import org.newdawn.slick.SlickException;

public class Scout extends Units{
	public Scout(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.SCOUT_PATH);
		setspeed(ConstantNumber.SCOUT_SPEED);
	}
}