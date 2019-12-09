import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Factory extends Buildings{
	private boolean underTraining=false;
	private double trainingTime=0;
	
	public double gettrainingTime() {
		return this.trainingTime;
	}
	public	void settrainingTime(double trainingTime) {
		this.trainingTime=trainingTime;
	}
	public boolean getunderTraining() {
		return this.underTraining;
	}
	public	void setunderTraining(boolean underTraining) {
		this.underTraining=underTraining;
	}
	public Factory(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.FACTORY_PATH);
	}
	/*function to train truck*/
	public void trainingTruck(World world) throws SlickException {
		/*judge whether training is valid*/
		if(super.getbeselected()==true) {
			/*ensure the factory only train one truck every time*/
			if(this.underTraining==false) {
				if(world.getInput().isKeyPressed(Input.KEY_1)&&world.getText().getMetalNumber()>=ConstantNumber.TRUCK_METALCOST) {
					/*change state to under training and minus resource*/
					this.underTraining=true;
					world.getText().setMetalNumber(world.getText().getMetalNumber()-ConstantNumber.TRUCK_METALCOST);
				}
			}
		}
		/*the case that factory is under training*/
		if(this.underTraining==true) {
			this.trainingTime+=world.getDelta();
			/*if training finished, create new truck and free factory*/
			if(this.trainingTime>=ConstantNumber.TRUCK_TIMECOST) {
				Truck sprite=new Truck(world.getCamera(),this.getX(),this.getY());
				world.allattributes.add(sprite);
				this.trainingTime=0;
				this.underTraining=false;
			}
		}
	}
	public void update(World world)throws SlickException {
		super.update(world);
		trainingTruck(world);
	}
}