package om.level.tile.pws;

import om.graphics.pws.Colours;
import om.graphics.pws.Screen;
import om.level.pws.Level;

public abstract class Tile {

	// dit maakt drie soorten tegels. leeg, steen en gras.
	//Elk gebruikt de BasicTile methode samen met een ID, x en y-coödinaat en kleur.
	//BasicSolidTile houdt in dat je er niet doorheen kan lopen.
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1,-1, -1));
	public static final Tile GRASS2 = new BasicTile(1, 1, 0, Colours.get(-1,542, 121, -1));
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1,542, 141, -1));

	protected byte id;
	protected boolean solid;
	protected boolean emitter;

	public Tile(int id, boolean isSolid, boolean isEmitter) {
		this.id = (byte) id;
		if (tiles[id] != null) {
			throw new RuntimeException("Duplicate tile id on" + id);
		}
		this.solid = isSolid;
		this.emitter = isEmitter;
		tiles[id] = this;
	}

	public byte getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isEmitter() {
		return emitter;
	}

	public abstract void render(Screen screen, Level level, int x, int y);

}
