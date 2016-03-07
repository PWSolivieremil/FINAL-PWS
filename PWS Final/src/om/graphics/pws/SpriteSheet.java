package om.graphics.pws;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {				//Deze class is oorspronkelijk gemaakt door Notch
										//Hij heeft een zeer handige manier gevonden om dit te doen
	public String path;					
	public int width;
	public int height;

	public int[] pixels;

	public SpriteSheet(String path) {			//Hier zoekt Java naar het bestand
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));	//We hebben de spritesheet in het buildpath gezet
		} catch (IOException e) {												//Hierdoor hoef je niet een specifieke locatie op je computer te schrijven
			e.printStackTrace();
		}
		if (image == null) {
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);//Dit stuk leest de kleuren af van het bestand
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff) / 64;	//Dit verwijdert een alphachannel Het wordt gedeeld door 64 want we hebben 4 kleuren 256/4 = 64
		} //We hebben is de spritesheet 4 kleuren kleur 1 is 255/3*0 R=0 G=0 B=0 dat is zwart. Kleur 2 is 255/3*1 R=85 G=85 etc...
								
		for (int i = 0; i < 8; i++){		//Dit is oud
			System.out.println(pixels[i]);
		}
	}
}