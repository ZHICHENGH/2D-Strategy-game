import org.newdawn.slick.Graphics;
/*store text information*/
public class Text {
	private int metalNumber=0;
	private int unobtainiumNumber=0;
	private int carryNumber=ConstantNumber.CARRY_INITIALNUMBER;
	public int getMetalNumber() {
		return metalNumber;
	}
	public void setMetalNumber(int metalNumber) {
		this.metalNumber = metalNumber;
	}
	public int getUnobtainiumNumber() {
		return unobtainiumNumber;
	}
	public void setUnobtainiumNumber(int unobtainiumNumber) {
		this.unobtainiumNumber = unobtainiumNumber;
	}
	public int getCarryNumber() {
		return carryNumber;
	}
	public void setCarryNumber(int carryNumber) {
		this.carryNumber = carryNumber;
	}
	public void render(World world,Graphics g)
	{
		g.drawString("Metal: "+this.metalNumber+"\nUnobtainium: "+this.unobtainiumNumber, 32, 32);
		/*judge which sprite is selected and show corresponding text*/
		for(int i=0;i<world.allattributes.size();i++) {
			if(world.allattributes.get(i).getbeselected()==true) {
				if(world.allattributes.get(i) instanceof CommandCentre) {
					g.drawString("1- Create Scout\n2- Create Builder\n3- Create Engineer\n",32,100);
				}
				else if(world.allattributes.get(i) instanceof Builder) {
					g.drawString("1- factory\n",32,100);
				}
				else if(world.allattributes.get(i) instanceof Truck) {
					g.drawString("1-  command centres\n",32,100);
				}
				else if(world.allattributes.get(i) instanceof  Factory) {
					g.drawString("1-   truck\n",32,100);
				}
			}
		}
	}
}
