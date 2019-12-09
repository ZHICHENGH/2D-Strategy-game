import org.newdawn.slick.SlickException;

public class Unobtainium extends Resources{
	public Unobtainium(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.UNOBTAINIUM_PATH);
		setleftNumber(ConstantNumber.UNOBTAINIUM_INITIALNUMBER);
	}
}