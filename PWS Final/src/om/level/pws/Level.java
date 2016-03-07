package om.level.pws;

import java.util.ArrayList;
import java.util.List;

import om.entities.pws.Entity;
//import om.game.pws.Game;
//import om.entities.pws.Mob;
import om.graphics.pws.Screen;
import om.level.tile.pws.Tile;

public class Level {				//Dit is de level class. Hierin wordt het level geladen en worden de entities erin gezet.

	private byte[] tiles;			//Hierin staat welke tile er bij welke plaats hoort.
	public int width;//non static
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();

	public Level(int width, int height) {	//Hier wordt de grootte van het level bepaald. Die komt uit de game class.
		tiles = new byte[width * height];	//Elke pixel wordt gevuld met een Tile.
		this.width = width;
		this.height = height;
		this.generateLevel();
	}

	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {					//Hier worden de tiles ingevuld door de levelgenerator. Dit maakt de ondergrond.
				if (x * y % 10 < 7) { 
					tiles[x + y * width] = Tile.GRASS.getId();
				} else {
					tiles[x + y * width] = Tile.GRASS2.getId();
				} 
			}
		}
	}

	public void tick() {				//De entities worden geladen, bijvoorbeeld monsters.
		for (Entity e : entities) {
			e.tick();
		}

	}

	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)												//Dit zorgt dat de camera niet verder meebeweegt 
			xOffset = 0;												//als een speler naar een rand of hoek toe loopt
		if (xOffset > ((width << 3) - screen.width))
			xOffset = ((width << 3) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << 3) - screen.height))
			yOffset = ((height << 3) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
			for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}
	
	public void renderEntities(Screen screen) {
		for (Entity e : entities) {
			//for(int i = 0; i < entities.size(); i++) {//Dit is nodig als we willen controleren hoeveel entities er zijn
	        //   System.out.println(entities.get(i));
	        //}
			e.render(screen);
		}
	}

    public Tile getTile(int x, int y) {					//Als er verder dan de rand wordt gekeken dan wordt het een void tile.
        if (0 > x || x >= width || 0 > y || y >= height)
            return Tile.VOID;
        return Tile.tiles[tiles[x + y * width]];
    }
    
    public void addEntity(Entity entity){
    	this.entities.add(entity);
    }
}