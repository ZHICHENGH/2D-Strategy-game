import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Builder extends Units{
	private boolean underBuilding=false;
	private double buildingTime=0;
	
	public boolean getunderBuilding() {
		return underBuilding;
	}
	public	void setunderBuilding(boolean underBuilding) {
		this.underBuilding=underBuilding;
	}
	public double getbulidingTime() {
		return buildingTime;
	}
	public void setbulidingTime(double buildingTime) {
		this.buildingTime=buildingTime;
	}
	
	public Builder(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.BUILDER_PATH);
		setspeed(ConstantNumber.BUILDER_SPEED);
	}
	/*function to build factory*/
	public void buildFactory(World world) throws SlickException {
		/*judge whether build is valid*/
		if(super.getbeselected()==true) {
			if(this.underBuilding==false) {
				if(world.getInput().isKeyPressed(Input.KEY_1)&&world.getText().getMetalNumber()>=ConstantNumber.FACTORY_METALCOST) {
					int tileId = world.getMap().getTileId(world.worldXToTileX(this.getX()),world.worldYToTileY(this.getY()), 0);
					if(!Boolean.parseBoolean(world.getMap().getTileProperty(tileId,ConstantNumber.OCCUPIED_PROPERTY,"false"))){
						/*start building and minus building cost*/
						this.underBuilding=true;
						world.getText().setMetalNumber(world.getText().getMetalNumber()-ConstantNumber.FACTORY_METALCOST);
					}
				}
			}
		}
		/*the situation that under building*/
		if(this.underBuilding==true) {
			/*forbid the builder to move*/
			setspeed(0);
			this.setTargetX(this.getX());
			this.setTargetY(this.getY());
			/*count time*/
			this.buildingTime+=world.getDelta();
			/*if build finished ,create the factory and free the builder*/
			if(this.buildingTime>=ConstantNumber.FACTORY_TIMECOST) {
				Factory sprite=new Factory(world.getCamera(),this.getX(),this.getY());
				world.allattributes.add(sprite);
				setspeed(ConstantNumber.BUILDER_SPEED);
				this.buildingTime=0;
				this.underBuilding=false;
			}
		}
	}
	public void update(World world)throws SlickException {
			super.update(world);
			buildFactory(world);
	}
}