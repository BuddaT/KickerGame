package net.buddat.ludumdare.kickergame.client.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Utilities {

	public static final int ALIGN_LEFT = 0, ALIGN_CENTER = 1, ALIGN_RIGHT = 2;
	
	public static void renderText(Graphics g, String str, int x, int y, int align) {
		Font currentFont = g.getFont();
		int textWidth = currentFont.getWidth(str);
		
		switch (align) {
			case ALIGN_CENTER:
				x -= textWidth / 2;
				break;
			case ALIGN_RIGHT:
				x -= textWidth;
				break;
		}
		
		g.drawString(str, x, y);
	}
	
	public static void renderText(Font f, String str, int x, int y, int align, Color c) {
		Font currentFont = f;
		int textWidth = currentFont.getWidth(str);
		
		switch (align) {
			case ALIGN_CENTER:
				x -= textWidth / 2;
				break;
			case ALIGN_RIGHT:
				x -= textWidth;
				break;
		}
		
		f.drawString(x, y, str, c);
	}
}
