package om.entities.pws;

import om.game.pws.Game;
import om.game.pws.InputHandler;
import om.graphics.pws.Colours;
import om.graphics.pws.Font;
import om.graphics.pws.Screen;
import om.level.pws.Level;

public class Player extends Mob {

	private InputHandler input;
	private int colour = Colours.get(-1, 111, 500, 532);

	public Player(Level level, int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1);
		this.input = input;
	}

	public void tick() {					//Looprichting
		if (Game.playerHealthpoints > 0){
			
			int xa = 0;
			int ya = 0;

			if (input.up.isPressed()) {			//Pijl omhoog en omlaag zet de looprichting op de y-as.
				ya -= 1;
			}
			if (input.down.isPressed()) {
				ya += 1;
			}
			if (input.left.isPressed()) {		//Pijl naar links en naar rechts zet de looprichting op de x-as.
				xa -= 1;
			}
			if (input.right.isPressed()) {
				xa += 1;
			}
		
			if (xa != 0 || ya != 0) {			//Als de looprichting op de x-as en/of de y-as positief is dan wordt isMoving op true gezet
				move(xa, ya);					// om later te gebruiken
				isMoving = true;	
			} else {							//als de looprichting op de x-as en/of de y-as niet positief is dan wordt isMoving op false gezet
				isMoving = false;
			}
		} 
	}

	public void render(Screen screen) {
		boolean playerweapon = true;
		int xTile = 0;
		int yTile = 28;
		int walkingSpeed = 4;		// dit is de snelheid waarop de player animatie beweegt
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;

		
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
		
		
		//Dit is 4 keer gedaan zodat aanpassingen bijvoorbeeld een zwaard niet een nieuwe sprite nodig hebben,
		//maar delen van de oude sprite kunnen kopieren.
		if (Game.playerHealthpoints > 0){
			
			if (playerweapon == false){	//Als er geen wapen is.
				
				screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop); // upper body part 1
				screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop); // upper body part 2
				screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom); // lower body part 1
				screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,colour , flipBottom); // lower body part 2
		
			} else {
			
			
				if (input.hit.isPressed()){ //als er wel een wapen is en wordt geslagen.
					Game.damage = true;
					xTile += 16;
					screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop); // upper body part 1
					screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop); // upper body part 2
					screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom); // lower body part 1
					screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour , flipBottom); // lower body part 2
				} else {					//Als er een wapen is maar niet wordt geslagen.
					Game.damage = false;
					xTile += 8;
					screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop); // upper body part 1
					screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop); // upper body part 2
					screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom); // lower body part 1
					screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,colour , flipBottom); // lower body part 2
		
				}
				Game.playerX = x;
				Game.playerY = y;
			} 
				
		} else {
			xTile += 24;
			screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, colour, flipTop); // upper body part 1
			screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, colour, flipTop); // upper body part 2
			screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, colour, flipBottom); // lower body part 1
			screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32,colour , flipBottom); // lower body part 2
			
		}
		clock(screen);
		
		
		
	}
	
	public void clock(Screen screen) {
		int clockx = (int) (x - 80);
		int clocky = (int) (y - 58);
		
		if (clockx < 0)												//Dit zorgt dat de klok niet verder meebeweegt 
			clockx = 0;												//als een speler naar een rand of hoek toe loopt
		if (clockx > ((Game.levelWidth << 3) - screen.width))
			clockx = ((Game.levelWidth << 3) - screen.width);
		if (clocky < 0)
			clocky = 0;
		if (clocky > ((Game.levelHeight << 3) - screen.height))
			clocky = ((Game.levelHeight << 3) - screen.height);
		
		if (Game.playerHealthpoints > 0){
			Game.playtime = System.nanoTime();
		}
		long p = Game.playtime - Game.starttime;			//Dit is de klok die je in de game zal zien.
		long gametime = p/1000000000;
		String gametime2 = Long.toString(gametime);
		int colour2 = Colours.get(-1, -1, -1, 000);
		int colour3 = Colours.get(-1, -1, -1, 125);
		if (Game.playerHealthpoints > 0){ 
			Font.render(gametime2, screen, clockx, clocky, colour2);
		} else {
			Font.render("Congratulations, " , screen, clockx, clocky, colour2);
			Font.render("you, " + Game.name +", have", screen, clockx, (clocky + 9), colour2);
			if (gametime <= 10){
				Font.render("reached a pathetic "  , screen, clockx, (clocky + 18), colour2);
				Font.render("score of: " + gametime2 , screen, clockx, (clocky + 18), colour2);
			} else {
				Font.render("reached a score of: "  , screen, clockx, (clocky + 18), colour2);
				Font.render(gametime2, screen, clockx, (clocky + 27), colour3);
			}
			Font.render("Press R to RESTART"  , screen, clockx, (clocky + 45), colour2);
		}
	}

		// Hier wordt bepaald wat de collisionbox (deel wat kan botsen met objecten) is. 
		// Er wordt ook voor gezorgd dat de x en y coordinaten niet in de vaste tegels kunnen zitten en dus bots je.
		public boolean hasCollided(int xa, int ya) {
			int xMin = 0;								//Dit zijn de afmetingen van de speler. Het is kleiner dan de sprite van de speler zodat je niet
			int xMax = 7;								// tegen tegels kan botsen met je hoofd. Hierdoor lijkt het een beetje 3D omdat je een diepteeffect krijgt.
			int yMin = 3;			
			int yMax = 7;
			for (int x = xMin; x < xMax;x++){			//Dit gebruikt de isSolidTile methode om te bepalen of de speler tegen een tegel aan botst.
				if (isSolidTile(xa,ya,x,yMin)){			//Als dit zo is dan geeft het true als antwoord
					return true;	
				}
			}
			for (int x = xMin; x < xMax;x++){				
				if (isSolidTile(xa,ya,x,yMax)){
					return true;
				}
			}
			for (int y = yMin; y < yMax;y++){				
				if (isSolidTile(xa,ya,xMin,y)){
					return true;
				}
			}
			for (int y = yMin; y < yMax;y++){				
				if (isSolidTile(xa,ya,xMax,y)){
					return true;
				}
			}
			
			return false;								//Als geen van deze voorwaarden kloppen dan is het antwoord false.
		}
		
		
}
