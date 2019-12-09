import org.newdawn.slick.SlickException;

public class Units extends Sprite{
	public Units(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setSelectedImage(ConstantNumber.UNITSSELECTED_PATH);
	}
}