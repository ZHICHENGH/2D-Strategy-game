import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Truck extends Units{
	private boolean underBuilding=false;
	private double buildingTime=0;
	public boolean getunderBuilding() {
		return this.underBuilding;
	}
	public	void setunderBuilding(boolean underBuilding) {
		this.underBuilding=underBuilding;
	}
	
	public double getbuildingTime() {
		return this.buildingTime;
	}
	public	void setbuildingTime(double buildingTime) {
		this.buildingTime=buildingTime;
	}
	public Truck(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.TRUCK_PATH);
		setspeed(ConstantNumber.TRUCK_SPEED);
	}
	/*function to build CommandCentre*/
	public void buildCC(World world) throws SlickException {
		/*judge whether the build is valid*/
		if(super.getbeselected()==true) {
			if(this.underBuilding==false) {
				if(world.getInput().isKeyPressed(Input.KEY_1)) {
					int tileId = world.getMap().getTileId(world.worldXToTileX(this.getX()),world.worldYToTileY(this.getY()), 0);
					if(!Boolean.parseBoolean(world.getMap().getTileProperty(tileId,ConstantNumber.OCCUPIED_PROPERTY,"false"))){
						this.underBuilding=true;
					}
				}
			}
		}
		/*the case that under building*/
		if(this.underBuilding==true) {
			/*forbid movement during building*/
			setspeed(0);
			this.setTargetX(this.getX());
			this.setTargetY(this.getY());
			
			this.buildingTime+=world.getDelta();
			/*if build finished,create CC and make the truck state to not exist*/
			if(this.buildingTime>ConstantNumber.CC_TIMECOST) {
				CommandCentre sprite=new CommandCentre(world.getCamera(),this.getX(),this.getY());
				world.allattributes.add(sprite);
				this.setExist(false);
			}
		}
	}
	public void update(World world)throws SlickException {
		super.update(world);
		buildCC(world);
	}
}