package om.graphics.pws;

public class Colours {

	public static int get(int colour1, int colour2, int colour3, int colour4) { // de invoer zijn de verschillende kleuern die je gaat gebruiken voor elke sprite,
		return ((get(colour4) << 24) + (get(colour3) << 16)						// dit kunnen er niet meer dan 4 zijn op dit moment
				+ (get(colour2) << 8) + (get(colour1)));	
		// dit is zodat het 1 getal geeft ipv meerdere verschillende waardes voor rood, groen en blauw
	}
		// Het nadeel van deze manier is dat er meerdere kleuren aan elk getal zitten, het is dan lastig rekenen
	private static int get(int colour) {// wat dit doet is de gecombineerde waarde van de RGB kleuren omzetten tot een kleur
		if (colour < 0) {
			return 255;
		}
		int r = colour / 100 % 10;
		int g = colour / 10 % 10;
		int b = colour % 10;
		return r * 36 + g * 6 + b;
	}
}