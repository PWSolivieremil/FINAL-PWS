package om.entities.pws;

//import om.game.pws.Game;
import om.graphics.pws.Screen;
import om.level.pws.Level;

public class Entity {

	public double x;	// Dit geeft de x-coördinaat	
	public double y;	//Dit geeft de y-coördinaat
	protected Level level;
	
	
	
	public Entity(Level level) {
		init(level);
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	
	//public abstract void tick() {
	//	System.out.print("Marco" + " ");
		//if (System.currentTimeMillis() - Game.lastSpawn >= 1000){
			//Game.lastSpawn += 1000;
		//	System.out.println("Polo");
	//		Game.Ai = new AI(this);
		//	Game.bots = new Bots(level, 0, 0, Ai);
		//	level.addEntity(bots);
			
	//	}
	//}		
	//Als er mobs zijn dan worden ze hier in gezet.
	
	public void tick() {
		
	}
	
	public void render(Screen screen) {	
		
	}
}