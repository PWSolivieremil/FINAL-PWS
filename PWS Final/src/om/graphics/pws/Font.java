package om.graphics.pws;

// deze class is bedoeld om de tekste op de spritesheet te lezen

public class Font {

	public static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,:;'\"!?$%()-=+/      "; 	//Dit is de volgorde van de letters en cijfers
																													// zoals ze ook in de spritesheet staan.
	public static void render(String msg, Screen screen, int x, int y,
			int colour) {
		msg = msg.toUpperCase();

		// Het telt waar de letter in het rijtje staat en wijst het coördinaten op de spreadsheet toe om te laden.
		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0) {
				screen.render(x + i * 8, y, charIndex + 30 * 32, colour);
			}
		}
	}
}
