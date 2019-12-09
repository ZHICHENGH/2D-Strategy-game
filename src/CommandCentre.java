import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CommandCentre extends Buildings{
	private boolean underTraining=false;
	private double trainingTime=0;
	private int trainingNumber;
	public CommandCentre(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.COMMANDCENTRE_PATH);
	}
	public boolean getunderTraining() {
		return this.underTraining;
	}
	public	void setunderTraining(boolean underTraining) {
		this.underTraining=underTraining;
	}
	public double gettrainingTime() {
		return trainingTime;
	}
	public void settrainingTime(double trainingTime) {
		this.trainingTime=trainingTime;
	}
	/*function for training*/
	public void training(World world) throws SlickException {
		/*judge whether training is valid, which unit is going to train, and minus the cost*/
		if(super.getbeselected()==true) {
			if(this.underTraining==false) {
				if(world.getInput().isKeyPressed(Input.KEY_1)&&world.getText().getMetalNumber()>=ConstantNumber.SCOUT_METALCOST) {
					this.trainingNumber=ConstantNumber.TRAINSCOUT_NUMBER;
					this.underTraining=true;
					world.getText().setMetalNumber(world.getText().getMetalNumber()-ConstantNumber.SCOUT_METALCOST);
				}
				else if(world.getInput().isKeyPressed(Input.KEY_2)&&world.getText().getMetalNumber()>=ConstantNumber.BUILDER_METALCOST) {
					this.trainingNumber=ConstantNumber.TRAINBUILDER_NUMBER;
					this.underTraining=true;
					world.getText().setMetalNumber(world.getText().getMetalNumber()-ConstantNumber.BUILDER_METALCOST);
				}
				else if(world.getInput().isKeyPressed(Input.KEY_3)&&world.getText().getMetalNumber()>=ConstantNumber.ENGINEER_METALCOST) {
					this.trainingNumber=ConstantNumber.TRAINENGINEER_NUMBER;
					this.underTraining=true;
					world.getText().setMetalNumber(world.getText().getMetalNumber()-ConstantNumber.ENGINEER_METALCOST);
				}
			}
		}
		if(this.underTraining==true) {
			/*if the training is valid, call the function underTrain to finish the training*/
			underTrain(world);
		}
	}
	/*function to finish training process*/
	public void underTrain(World world) throws SlickException{
			if(this.underTraining==true) {
				this.trainingTime+=world.getDelta();
		}
			if(this.trainingTime>=ConstantNumber.UNITS_TIMECOST) {
				/*create unit according to key-input*/
				if(this.trainingNumber==ConstantNumber.TRAINSCOUT_NUMBER) {
					Scout sprite=new Scout(world.getCamera(),this.getX(),this.getY());
					world.allattributes.add(sprite);
						}
				else if(this.trainingNumber==ConstantNumber.TRAINBUILDER_NUMBER) {
						Builder sprite=new Builder(world.getCamera(),this.getX(),this.getY());
						world.allattributes.add(sprite);
						}
				else if(this.trainingNumber==ConstantNumber.TRAINENGINEER_NUMBER) {
						Engineering sprite=new Engineering(world.getCamera(),this.getX(),this.getY());
						world.allattributes.add(sprite);
						}
				/*free the CC*/
				this.underTraining=false;
				this.trainingTime=0;
				}
	}
	public void update(World world)throws SlickException {
		super.update(world);
		training(world);
	}
}