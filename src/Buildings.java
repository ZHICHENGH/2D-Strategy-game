import org.newdawn.slick.SlickException;


public class Buildings extends Sprite{
	public Buildings(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setSelectedImage(ConstantNumber.BUILDINGSELECTED_PATH);
	}
}