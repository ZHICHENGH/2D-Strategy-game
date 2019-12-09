import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import java.util.ArrayList;
import java.io.BufferedReader;    
import java.io.FileReader; 
/**
 * This class should be used to contain all the different objects in your game world, and schedule their interactions.
 * 
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */
public class World {
	private TiledMap map;
	public TiledMap getMap() {
		return map;
	}
	private Camera camera = new Camera();
	private Input lastInput;
	private int lastDelta;
	private Sprite selectedSprite;
	private Text text;
	ArrayList<Sprite> allattributes= new ArrayList<Sprite>();
	private BufferedReader reader;
	public Camera getCamera() {
		return camera;
	}
	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	public Input getInput() {
		return lastInput;
	}
	public int getDelta() {
		return lastDelta;
	}
	public boolean isPositionFree(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, ConstantNumber.SOLID_PROPERTY, "false"));
	}
	public double getMapWidth() {
		return map.getWidth() * map.getTileWidth();
	}
	public double getMapHeight() {
		return map.getHeight() * map.getTileHeight();
	}
	/*judge whether sprite is selected*/
	public void findselected(Input input) {
		/*delete the former selection if left-click happen*/
		for(int i=0;i<allattributes.size();i++) {
			allattributes.get(i).setbeselected(false);
		}
		ArrayList<Sprite> canselected= new ArrayList<Sprite>();
		double x2=camera.screenXToGlobalX(input.getMouseX());
		double y2=camera.screenYToGlobalY(input.getMouseY());
		/*find all sprite that around the mouse location*/
		for(int i=0;i<allattributes.size();i++) {
			Sprite sprite=allattributes.get(i);
			if(sprite.getSelectable()==true) {
				double x1=sprite.getX();
				double y1=sprite.getY();	
				if(World.distance(x1,y1,x2,y2)<=ConstantNumber.SELECT_DISTANCE) {
					canselected.add(sprite);
				}
			}
		}
		/*the case that the mouse click location has sprite*/
		if(canselected.size()!=0) {
			/*if there are many sprite in the location, the unit has priority*/
			for(int j=0;j<canselected.size();j++) {
				if(canselected.get(j) instanceof Units) {
					canselected.get(j).setbeselected(true);
					this.selectedSprite=canselected.get(j);
					return;
				}
			}
			canselected.get(0).setbeselected(true);
			this.selectedSprite=canselected.get(0);

		}
		else {
			this.selectedSprite=null;
		}
	}
	public World() throws SlickException {
		map = new TiledMap(ConstantNumber.MAP_PATH);
		text=new Text();
        try {
        	/*read the CSV and create initial sprite into arraylist*/
            reader = new BufferedReader(new FileReader(ConstantNumber.OBJECT_PATH));
            String line = null;    
            while((line=reader.readLine())!=null){    
                String item[] = line.split(",");
                int positionX=Integer.parseInt(item[1]);
                int positionY=Integer.parseInt(item[2]);
                if (item[0].contentEquals("command_centre")){
                	CommandCentre tmpCC=new CommandCentre(camera,positionX,positionY);
                	allattributes.add(tmpCC);
                }
                else if(item[0].contentEquals("metal_mine")){
                	Metal tmpmetal=new Metal(camera,positionX,positionY);
                	allattributes.add(tmpmetal);
                }
                else if(item[0].contentEquals("unobtainium_mine")){
                	Unobtainium tmpum=new Unobtainium(camera,positionX,positionY);
                	allattributes.add(tmpum);
                }
                else if(item[0].contentEquals("pylon")){
                	Pylon tmpylon=new Pylon(camera,positionX,positionY);
                	allattributes.add(tmpylon);
                }
                else if(item[0].contentEquals("engineer")){
                	Engineering tmpee=new Engineering(camera,positionX,positionY);
                	allattributes.add(tmpee);
                }
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        	}
	}
	public void update(Input input, int delta) throws SlickException {
		lastInput = input;
		lastDelta = delta;
		/*if left click, find the selected sprite and  change the camera follow*/
		if(lastInput.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			findselected(lastInput);
			camera.followSprite(selectedSprite);
		}
		camera.update(this);
		/*if some sprite is no longer exist, remove it from arrayList*/
		for(int i=0;i<allattributes.size();i++) {
			Sprite tmpattribute=allattributes.get(i);
			if(tmpattribute.getExist()==false) {
					allattributes.remove(i);
					i--;
			}
		}
		/*update every sprite*/
		for(int i=0;i<allattributes.size();i++) {
			allattributes.get(i).update(this);
		}
	}
	public void render(Graphics g) {
		/*draw the map and every sprite*/
		map.render((int)camera.globalXToScreenX(0),
				   (int)camera.globalYToScreenY(0));
		for(int i=0;i<allattributes.size();i++) {
			allattributes.get(i).render();
		}
		text.render(this,g);
	}
	// This should probably be in a separate static utilities class, but it's a bit excessive for one method.
	public static double distance(double x1, double y1, double x2, double y2) {
		return (double)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	public int worldXToTileX(double x) {
		return (int)(x / this.getMap().getTileWidth());
	}
	public int worldYToTileY(double y) {
		return (int)(y / this.getMap().getTileHeight());
	}
}
