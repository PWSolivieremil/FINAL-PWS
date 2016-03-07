package om.entities.pws;

import om.game.pws.Game;
import om.graphics.pws.Colours;
import om.graphics.pws.Font;
import om.graphics.pws.Screen;
import om.level.pws.Level;

public class Bots  extends Mob{

	private int colour = Colours.get(-1, 111, 300, 051);
	protected int healthpoints = 300;
	
	public Bots(Level level, int x, int y, double speed) {
		super(level, "zombie", x, y, speed);
	}
	
	public void tick() {			//Looprichting
		int xa = 0;
		int ya = 0;
			
		if (healthpoints > 0 ){ 
			if ((y - Game.playerY) > 0.5) {			//Al deze regels zijn er omdat de snelheid van de Bot lager is dan die van de player
				ya -= 1;							//De bot kan een halve tile lopen. Dit kan zorgen dat de bot niet precies op de 
			}										//zelfde x en y coordinaten van de player komt. In dit gedeelte wordt dat beter verwerkt.
			if ((y - Game.playerY) <= 0.5 && (y - Game.playerY) > 0){
				ya -= (y - Game.playerY);
			}
			if (Game.playerY == y) {
				ya += 0;
			}
			if ((y - Game.playerY) < -0.5) {			
				ya += 1;
			}
			if ((y - Game.playerY) >= -0.5 && (y - Game.playerY) < 0){
				ya += (y - Game.playerY);
			}
			if ((x - Game.playerX) > 0.5) {		
				xa -= 1;
			}
			if ((x - Game.playerX) <= 0.5 && (x - Game.playerX) > 0){
				xa -= (x - Game.playerX);
			}
			if (Game.playerX == x) {
				xa += 0;
			}
			if ((x - Game.playerX) < -0.5) {			
				xa += 1;
			}
			if ((x - Game.playerX) >= -0.5 && (x - Game.playerX) < 0){
				xa += (x - Game.playerX);
			}

			if (xa != 0 || ya != 0) {
				move(xa, ya);
				isMoving = true;
			} else {
				isMoving = false;
			}
		}
	}

	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 26;
		int walkingSpeed = 4;		// dit is de snelheid waarop de player animatie beweegt
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		int playerRange = 10;
		
		//Hier wordt de schade per hit berekend.
			double deltaX = x - Game.playerX;
			double deltaY = y - Game.playerY;
			if (healthpoints > 0 && Game.playerHealthpoints > 0){
			if ((deltaX <= playerRange && deltaX >= 0)){				//Schade door de speler
				if ((deltaY <= playerRange && deltaY >= 0)){
					if (Game.damage) {
						dmgCounter(screen);
						healthpoints = healthpoints - Game.weaponDamage;
					}
				}
				if ((deltaY >= -playerRange && deltaY <= 0)){
					if (Game.damage) {
						dmgCounter(screen);
						healthpoints = healthpoints - Game.weaponDamage;
					}
				}
			} else if (deltaX >= -playerRange && deltaX <= 0) {
				if ((deltaY <= playerRange && deltaY >= 0)){
					if (Game.damage) {
						dmgCounter(screen);
						healthpoints = healthpoints- Game.weaponDamage;
					}
				}
				if ((deltaY >= -playerRange && deltaY <= 0)){
					if (Game.damage) {
						dmgCounter(screen);
						healthpoints = healthpoints - Game.weaponDamage;
					}
				}
			}
				if ((int)Game.playerX == (int)x && (int)Game.playerY == (int)y){
				Game.playerHealthpoints = Game.playerHealthpoints - Game.botDamage;
			}	
		}

		
		if (movingDir == 1) {		//nieuwe sprite die de game moet gebruiken
			xTile += 2;
			
		}else if (movingDir > 1){
			xTile += 4  + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
			flipBottom = (movingDir - 1) % 2;
				
		}

		int modifier = 8 * scale;
		int xOffset = (int) (x - modifier / 2);
		int yOffset = (int) (y - modifier / 2 - 4);
	
		if (healthpoints > 0){
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop); // upper body part 1
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop); // upper body part 2
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom); // lower body part 1
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,colour , flipBottom); // lower body part 2
		
		} else { // Dode Bot
			xTile += 8;
			screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop); // upper body part 1
			screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop); // upper body part 2
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom); // lower body part 1
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,colour , flipBottom); // lower body part 2
		}
		
	}
	public void dmgCounter(Screen screen) {
		int dmgx = (int) (x - 4);
		int dmgy = (int) (y - 15);
		
		String dmg = Integer.toString(Game.weaponDamage);
		int colour2 = Colours.get(-1, -1, -1, 500);
		Font.render(dmg, screen, dmgx, dmgy, colour2);
	}
}