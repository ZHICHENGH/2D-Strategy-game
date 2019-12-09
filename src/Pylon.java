import org.newdawn.slick.SlickException;

public class Pylon extends Buildings{
	private boolean active=false;
	public boolean getactive() {
		return this.active;
	}
	public	void setactive(boolean active) {
		this.active=active;
	}
	public Pylon(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.PYLON_PATH);
	}
	public void update(World world)throws SlickException {
		super.update(world);
		/*if unit near the Pylon,change the state to active and add 1 to carryNumber*/
		if(active==false) {
			for(int i=0;i<world.allattributes.size();i++) {
				if(world.allattributes.get(i) instanceof Units) {
					if((World.distance(this.getX(), this.getY(),world.allattributes.get(i).getX(),world.allattributes.get(i).getY()) <=ConstantNumber.ACTIVE_DISTANCE)) {
						this.active=true;
						world.getText().setCarryNumber(world.getText().getCarryNumber()+1);
						setImage(ConstantNumber.ACTIVEPYLON_PATH);
					}
				}
			}
		}
	}
}