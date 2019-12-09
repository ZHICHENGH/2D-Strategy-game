import org.newdawn.slick.SlickException;


public class Engineering extends Units{
	private boolean underMining=false;
	private boolean withResource=false;
	private int carryNumber;
	private double miningTime=0;
	private Resources miningResource=null;
	private String resourceType;
	private Sprite nearestCC=null;
	public boolean getunderMining() {
		return this.underMining;
	}
	public	void setunderMining(boolean underMining) {
		this.underMining=underMining;
	}
	
	public boolean getwithResource() {
		return this.withResource;
	}
	public	void setwithResource(boolean withResource) {
		this.withResource=withResource;
	}
	
	public double getminingTime() {
		return this.miningTime;
	}
	public	void setminingTime(double miningTime) {
		this.miningTime=miningTime;
	}
	public Resources getminingResource() {
		return this.miningResource;
	}
	public Engineering(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.ENGINEERING_PATH);
		setspeed(ConstantNumber.ENGINEERING_SPEED);
	}
	/*judge whether the engineering is near a resource*/
	public void nearResource(World world) {
		for(int i=0;i<world.allattributes.size();i++) {
			Sprite sprite=world.allattributes.get(i);
			if(sprite instanceof Resources) {
				if(World.distance(this.getX(), this.getY(),sprite.getX(),sprite.getY())<=ConstantNumber.WITHIN_DISTANCE) {
					/*if it is, record the Resource and change the state to mining*/
					this.miningResource=(Resources)sprite;
					this.underMining=true;
					return;
				}
			}
		}
	}
	/*function to find the nearest CC and record*/
	public void findnearestCC(World world) {
		double mindistance=Double.MAX_VALUE;
		double dist;
		for(int i=0;i<world.allattributes.size();i++) {
			Sprite sprite=world.allattributes.get(i);
			if(sprite instanceof CommandCentre) {
				dist=World.distance(this.miningResource.getX(), this.miningResource.getY(),sprite.getX(),sprite.getY());
				if(dist<mindistance) {
					mindistance=new Double(dist);
					this.nearestCC=sprite;
					}
				}
			}
	}
	/*function to judge whether the engineer is near a CC*/
	public boolean nearCC(World world) {
		for(int i=0;i<world.allattributes.size();i++) {
			Sprite sprite=world.allattributes.get(i);
			if(sprite instanceof CommandCentre) {
				if(World.distance(this.getX(),this.getY(),sprite.getX(),sprite.getY())<=ConstantNumber.WITHIN_DISTANCE){
					return true;
				}
			}
		}
		return false;
	}
	/*function to mining*/
	public void Mining(World world) {
		/*keep judging whether the engineering is near resource if it is not under mining*/
		if(this.underMining==false) {
			nearResource(world);
		}
		/*if the engineering is near a CC with resource, drop the resource and add it the total number*/
		if(nearCC(world)==true) {
			if(this.withResource==true) {
				if(this.resourceType.equals(ConstantNumber.METAL_STRING)) {
					world.getText().setMetalNumber(world.getText().getMetalNumber()+this.carryNumber);
				}
				else if(this.resourceType.equals(ConstantNumber.UNOBTAINIUM_STRING)) {
					world.getText().setUnobtainiumNumber(world.getText().getUnobtainiumNumber()+this.carryNumber);
				}
			}
			this.withResource=false;
		}
		if(this.miningResource!=null) {
			findnearestCC(world);
			if(World.distance(this.getX(), this.getY(),miningResource.getX(),miningResource.getY())<=ConstantNumber.WITHIN_DISTANCE) {
				if(this.miningResource.getleftNumber()>0&&this.withResource==false) {
					this.miningTime+=world.getDelta();
				}
			}
			/*if the engineer leaves the resource, clean the time counting*/ 
			else{
				this.miningTime=0;
			}
			/*the case that finish mining*/
			if(this.miningTime>=ConstantNumber.MINING_TIMECOST) {
				/*get the carryNumber*/
				this.miningTime=0;
				this.withResource=true;
				this.carryNumber=world.getText().getCarryNumber();
				/*prevent carryNumber larger than left number*/
				if(this.carryNumber>this.miningResource.getleftNumber()) {
					this.carryNumber=this.miningResource.getleftNumber();
				}
				/*minus the mining number from resource*/
				this.miningResource.setleftNumber(this.miningResource.getleftNumber()-this.carryNumber);
				/*record the resource type for dropping resource*/
				if(this.miningResource instanceof Metal) {
					this.resourceType=ConstantNumber.METAL_STRING;
				}
				else if(this.miningResource instanceof Unobtainium) {
					this.resourceType=ConstantNumber.UNOBTAINIUM_STRING;
				}
				/*set the target to nearestCC to drop resource*/
				this.setTargetX(this.nearestCC.getX());
				this.setTargetY(this.nearestCC.getY());
			}
			/*deal with the case that engineering is under transfer*/
			if(this.underMining==true) {
				double x1=this.getX();
				double y1=this.getY();
				double x2=this.nearestCC.getX();
				double y2=this.nearestCC.getY();
				double x3=this.miningResource.getX();
				double y3=this.miningResource.getY();
				/*if the engineer is not going to planning target, stop the mining state*/
				if(World.distance(this.getTargetX(),this.getTargetY(),x3,y3)>ConstantNumber.WITHIN_DISTANCE) {
					if(World.distance(this.getTargetX(),this.getTargetY(),x2,y2)>ConstantNumber.WITHIN_DISTANCE) {
						this.underMining=false;
						this.miningResource=null;
					}
				}
				/*if the engineering is in nearestCC and under mining,send it back to resource*/
				if(World.distance(x1,y1,x2,y2)<=ConstantNumber.WITHIN_DISTANCE) {
					this.setTargetX(this.miningResource.getX());
					this.setTargetY(this.miningResource.getY());
				}
			}
		}
	}
	public void update(World world) throws SlickException {
		super.update(world);
		Mining(world);
	}
}