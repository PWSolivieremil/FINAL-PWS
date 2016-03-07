package om.game.pws;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

import om.entities.pws.Bots;
import om.entities.pws.Entity;
import om.entities.pws.Player;
import om.graphics.pws.Screen;
import om.graphics.pws.SpriteSheet;
import om.level.pws.Level;

public class Game extends Canvas implements Runnable { //Dit is onze mainclass.

	private static final long serialVersionUID = 1L;//Dit moet gedaan worden bij het maken van een frame.

	public static final int WIDTH = 160;			//Dit zijn eind waardes die niet veranderen. 
	public static final int HEIGHT = WIDTH / 12 * 9;//Het is op deze manier gedaan,
	public static final int SCALE = 3;				//zodat wij het in de code snel kunnen aanpassen.
	public static final String NAME = "Game";

	private JFrame frame;							//Dit is de frame, hierin zal de game runnen.

	public boolean running = false;
	public int tickCount = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //Dit is wat er in het canvas komt te staan.
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();		
	private int[] colours = new int[6 * 6 * 6];
	
	private Screen screen;
	public InputHandler input;
	public Level level;
	public Entity Entity;
	public static final int levelWidth = 64;
	public static final int levelHeight = 64;
	public Player player;
	public Bots bots;
	public static String name;
	public static boolean secretMode;

	public static long starttime;
	public static long lastSpawn;
	public static long playtime;
	
	public static double playerX;
	public static double playerY;
	public static int weaponDamage;
	public static int botDamage;
	public static boolean damage;
	public static long lastHit;
	public static int playerHealthpoints;
	public static int totalBots;
	
	public Game() {
		GUI_start.startframe.setVisible(false);
		GUI_start.startframe.dispose(); 
		
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));	//Dit zijn de dimensies van het canvas
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));	//Doordat ze allemaal hetzelfde zijn, 
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));	//start het spel altijd met dezelfde grootte

		frame = new JFrame(NAME);										//hier maken we het frame

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//Het canvas sluit als jij hem sluit
		frame.setLayout(new BorderLayout());							 
		frame.add(this, BorderLayout.CENTER);
		frame.pack();													//De grootte van het canvas is nu  volgens die dimensies

		frame.setResizable(false);										//Grootte is niet aanpasbaar
		frame.setLocationRelativeTo(null);								//Waar het spel is nu in het midden van het scherm
		frame.setVisible(true);											//Vanaf nu kan het het spel zien.
	}

	public void init() {												//Dit start allerlei gegevens bij het opstarten van het spel.
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colours[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		
		starttime = System.nanoTime();
		lastSpawn = System.currentTimeMillis();
		weaponDamage = 30;
		botDamage = 3;
		damage = false;
		lastHit = System.currentTimeMillis();
		playerHealthpoints = 100;
		totalBots = 0;

		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);	
		level = new Level(levelWidth, levelHeight);
		player = new Player(level, 0, 0, input);
		level.addEntity(player);
		
		
	}

	public synchronized void start() {			//Synchronized is handig als je het als applet maakt, zo kan je het runnen in browser.
		running = true;	
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();				//Bij een game is het erg belangrijk
		long lastTimer = System.currentTimeMillis();	//dat het speelbaar is.
		double nsPerTick = 1000000000D / 60D;			//Door een limiet te zetten op de snelheid
		double delta = 0;								//van while loop(ticks) loopt de game op een 
		int ticks = 0;									//controleerbare snelheid, die op elk apparaat gelijk is.
		int frames = 0;
		init();

		while (running) {								//Dit is de  functie die blijft draaien tot je de game sluit
			long now = System.nanoTime();		
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
						
			while (delta >= 1) {	
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {	//Elke seconde komt een nieuwe waarden van de frames en ticks.
				lastTimer += 1000;									//Nu kan je beter zien dat er een limiet is.
				System.out.println(ticks + " ticks , " + frames + " frames per second");
				System.out.println("Hp " + playerHealthpoints);
				System.out.println("total bots " + totalBots);
				frames = 0;
				ticks = 0;
			}
			
		}
			
	}

	public void tick() {
		tickCount++;
		level.tick();	
		
		if (Game.playerHealthpoints <= 0 && input.restart.isPressed()){
			init();
		}
		
		int spawnRate = 1000;
		if (secretMode){
			spawnRate = 1;
		}
		
		if (System.currentTimeMillis() - Game.lastSpawn >= spawnRate && playerHealthpoints > 0){
			Game.lastSpawn += spawnRate;//10000
			totalBots ++;
			
			int botX = randInt(0, (levelWidth*8));
			int botY = randInt(0, (levelHeight*8));
		
			bots = new Bots(level, botX, botY, (0.6));
			level.addEntity(bots);
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);				//Dit is de snelheid waarop het plaatje wordt gemaakt
			return;
		}
		
		int xOffset = (int) (player.x - (screen.width / 2));
		int yOffset = (int) (player.y - (screen.height / 2));
		
		level.renderTiles(screen, xOffset, yOffset);

		//for (int x = 0; x < level.width; x++) {
			//int colour = Colours.get(-1, -1, -1, 000);
			
			//if (x % 10 == 0 && x != 0) {
				//colour = Colours.get(-1, -1, -1, 500);
			//}
			//Font.render((x%10) + "", screen, 0 + (x*8), 0, colour); //Dit is als je wil controleren hoe groot het level is
		//}

		level.renderEntities(screen);

		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int ColourCode = screen.pixels[x + y * screen.width];
				if (ColourCode < 255) {
					pixels[x + y * WIDTH] = colours[ColourCode];

				}
			}
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();						//De oude graphics wegdoen
		bs.show();
	}
	
	public static int randInt(int min, int max) {
	    int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
	    return randomNum;
	}

}