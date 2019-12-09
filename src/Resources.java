import org.newdawn.slick.SlickException;


public class Resources extends Sprite{
	private int leftNumber;
	public int getleftNumber() {
		return this.leftNumber;
	}
	public	void setleftNumber(int leftNumber) {
		this.leftNumber=leftNumber;
	}
	public Resources(Camera camera,double x,double y) throws SlickException {
		super(camera,x,y);
		setSelectable(false);
	}
	public void update(World world)throws SlickException {
		super.update(world);
		/*if the resource has been mined out, make Exist to be false to remove it*/
		if(this.getleftNumber()==0) {
			super.setExist(false);
		}
	}
}