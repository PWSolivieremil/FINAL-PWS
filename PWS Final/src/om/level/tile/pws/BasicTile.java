package om.level.tile.pws;

import om.graphics.pws.Screen;
import om.level.pws.Level;

public class BasicTile extends Tile {
	
	// Dit zal er voor zorgen dat elke tegel een eigen ID en een kleuren patroon krijgt.
	
	protected int tileId;
	protected int tileColour;

	// Het ID wordt bepaald door de locatie in het level en de kleur wordt in de coulours.java bepaald
	public BasicTile(int id, int x, int y, int tileColour) {
		super(id, false, false);
		this.tileId = x + y;
		System.out.println(tileId + x + y);
		this.tileColour = tileColour;
	}
	// Dit activeert de render methode in screen.java
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour);
	}

}