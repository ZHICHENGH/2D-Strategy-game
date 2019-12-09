import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Sprite {
	private double SPEED=0;
	private double x;
	private double y;
	// Initially, we don't need to move at all
	private double targetX = x;
	private double targetY = y;
	private Image image;
	private boolean beSelected=false;
	private boolean selectable=true;
	private Camera camera;
	private Image selectedImage;
	private boolean exist=true;
	public boolean getExist() {
		return this.exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public double getX() {
		return x;
	}
	public double getTargetX() {
		return targetX;
	}
	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}
	public double getTargetY() {
		return targetY;
	}
	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}
	public double getY() {
		return y;
	}
	public void setImage(String IMAGE_PATH) throws SlickException{
		this.image=new Image(IMAGE_PATH);
	}
	public void setSelectedImage(String SELECTEDIMAGE_PATH) throws SlickException{
		this.selectedImage = new Image(SELECTEDIMAGE_PATH);
	}
	public void setspeed(double speed){
		this.SPEED=speed;
	}
	public boolean getbeselected(){
		return this.beSelected;
	}
	public void setbeselected(boolean beselected){
		this.beSelected=beselected;
	}
	public boolean getSelectable() {
		return selectable;
	}
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public Sprite(Camera camera,double x,double y) throws SlickException {
		this.camera = camera;
		this.x=x;
		this.y=y;
		this.targetY=y;
		this.targetX=x;
	}
	public void update(World world) throws SlickException {
			/*if units been selected, moving by user's order*/
			if(this.beSelected==true) {
				if(this instanceof Units) {
					Input input = world.getInput();
					if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
						targetX = camera.screenXToGlobalX(input.getMouseX());
						targetY = camera.screenYToGlobalY(input.getMouseY());
					}
				}
			}
		// If we're close to our target, reset to our current position
		if (World.distance(x, y, targetX, targetY)<=ConstantNumber.WITHIN_DISTANCE) {
			resetTarget();
		} else {
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - y, targetX - x);
			double dx = (double)Math.cos(theta) * world.getDelta() * SPEED;
			double dy = (double)Math.sin(theta) * world.getDelta() * SPEED;
			// Check the tile is free before moving; otherwise, we stop moving
			if (world.isPositionFree(x + dx, y + dy)) {
				x += dx;
				y += dy;
			} else {
				resetTarget();
			}
		}
	}
	private void resetTarget() {
		targetX = x;
		targetY = y;		
	}
	public void render() {
		/*if the sprite be selected, draw highlight image*/
		if(this.beSelected==true) {
			selectedImage.drawCentered((int)camera.globalXToScreenX(x),
					   (int)camera.globalYToScreenY(y));
		}
		image.drawCentered((int)camera.globalXToScreenX(x),
						   (int)camera.globalYToScreenY(y));

		
	}
}
