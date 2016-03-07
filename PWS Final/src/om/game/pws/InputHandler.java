package om.game.pws;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import om.game.pws.Game;

public class InputHandler implements KeyListener {	//Dit is de inputhandler, als je een toets indrukt wordt het hier afgelezen.
	
	public InputHandler(Game game) {	
		game.requestFocus();
		game.addKeyListener(this);
	}

	public class Key {						
		private int numTimesPressed = 0;		//Dit houdt bij hoevaak een toets is ingedrukt.
		private boolean pressed = false;

		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean isPressed) {	//Dit houdt bij welke toets is ingedrukt.
			pressed = isPressed;

			if (isPressed) {					//Als de toets vaker is ingedrukt, dan wordt dit ook bijgehouden.
				numTimesPressed++;
			}
		}
	}

	public List<Key> keys = new ArrayList<Key>(); //Dit is niet meer nodig
	
	public Key up = new Key();					  //de keys
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key hit = new Key();
	public Key restart = new Key();

	public void keyPressed(KeyEvent e) {		
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void toggleKey(int KeyCode, boolean isPressed) {			//Dit is de inputaflezer
		
		if (KeyCode == KeyEvent.VK_W || KeyCode == KeyEvent.VK_UP) { //als er op W of op "up" wordt dit afgelezen als: up. Up is dan ingedrukt(pressed)
			up.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_S|| KeyCode == KeyEvent.VK_DOWN) {//Hetzelfde maar dan met S en "down". Down is nu ingedrukt.
			down.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_A || KeyCode == KeyEvent.VK_LEFT) {//Hetzelfde maar dan met A en "left". Left is nu ingedrukt.
			left.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_D || KeyCode == KeyEvent.VK_RIGHT) {//Hetzelfde maar dan met D en "right". Right is nu ingedrukt.
			right.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_SPACE){							//Deze knop leest de spatiebalk af. Als deze wordt ingedrukt dan slaat het poppetje (hit).
			hit.toggle(isPressed);
		}
		if (KeyCode == KeyEvent.VK_R && Game.playerHealthpoints <= 0){
			restart.toggle(isPressed);
		}
	}
}