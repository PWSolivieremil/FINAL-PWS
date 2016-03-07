package om.level.tile.pws;

public class BasicSolidTile extends BasicTile
{
	//Deze class is bedoeld om ervoor te zorgen dat je niet buiten het level en door stenen kan lopen
	public BasicSolidTile(int id, int x, int y, int tileColour) {
		super(id, x, y, tileColour);
		this.solid = true;
	}

}
