package om.graphics.pws;

public class Screen {
	// Dit zijn allemaal eigenschappen die worden gebruikt bij het laden van het level.
    public static final int MAP_WIDTH = 64;												//De breedte van het level
    public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;								//De breedte van het leven -1 om het deel te krijgen wat je kan zien.

    public static final byte BIT_MIRROR_X = 0x01;
    public static final byte BIT_MIRROR_Y = 0x02;

    public int[] pixels;

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;

    public SpriteSheet sheet;

    // dit deel neemt de hoogte en de breedte en berekend hoeveel pixels er op het scherm zijn.
    public Screen(int width, int height, SpriteSheet sheet) {
            this.width = width;
            this.height = height;
            this.sheet = sheet;

            pixels = new int[width * height];

    }
    // Dit bepaald de afstand van de tegels.
    public void setOffset(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
    }
    
    public void render(int xPos, int yPos, int tile, int colour) {
            render(xPos, yPos, tile, colour, 0x00);
    }
    // Deze methode bepaald wat er waar moet worden geladen in het spel. Het heeft een x-coördinaat, y-coördinaat, de tegel op de spritesheet, de kleur en de
    public void render(int xPos, int yPos, int tile, int colour, int mirrorDir) {
            xPos -= xOffset;
            yPos -= yOffset;

            boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
            boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
            
            // dit zorgt ervoor dat het tellen van de pixels altijd op het begin van een tegel uitkomt en je dus het goede laadt. Dit is nodig voor het bepalen welke 
            // tegel er moet worden geladen
            
            int xTile = tile % 32;
            int yTile = tile / 32;
            int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;

            
            for (int y = 0; y < 8; y++) {
                    if (y + yPos < 0 || y + yPos >= height) {
                            continue;
                    }
                    int ySheet = y;
                    if (mirrorY) {
                            ySheet = 7 - y;
                    }
                    for (int x = 0; x < 8; x++) {
                            if (x + xPos < 0 || x + xPos >= width) {
                                    continue;
                            }
                            int xSheet = x;
                            if (mirrorX) {
                                    xSheet = 7 - x;
                            }
                            int col = (colour >> (sheet.pixels[xSheet + ySheet
                                                * sheet.width + tileOffset] * 8)) & 255;
                            if (col < 255) {
                                    pixels[(x + xPos) + (y + yPos) * width] = col;
                            }
                    }
            }
    }
}