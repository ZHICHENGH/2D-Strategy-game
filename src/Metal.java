import org.newdawn.slick.SlickException;


public class Metal extends Resources{
	public Metal(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setImage(ConstantNumber.METAL_PATH);
		setleftNumber(ConstantNumber.METAL_INITIALNUMBER);
	}
}