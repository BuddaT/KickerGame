package net.buddat.ludumdare.kickergame.client.gfx;

import net.buddat.ludumdare.kickergame.Constants;
import net.buddat.ludumdare.kickergame.client.KickerGame;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.util.FontUtils;

public class MenuScreen implements Screen {
	
	private KickerGame parent;
	private UnicodeFont mainFont, optionsFont;
	
	private MenuItem playItem, practiceItem, leaderboardItem, optionsItem;

	public MenuScreen(KickerGame kickerGame) {
		this.parent = kickerGame;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		
		Utilities.renderText(mainFont, Constants.NAME, 775, 50, Utilities.ALIGN_RIGHT, Color.green);
		playItem.render(gc);
		practiceItem.render(gc);
		leaderboardItem.render(gc);
		optionsItem.render(gc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc) {
		try {
			mainFont = new UnicodeFont("/data/SF Sports Night.ttf", 52, false, false);
			mainFont.addAsciiGlyphs();
			mainFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			mainFont.getEffects().add(new OutlineEffect(1, java.awt.Color.BLACK));
			mainFont.loadGlyphs();
			
			optionsFont = new UnicodeFont("/data/SF Sports Night.ttf", 42, false, false);
			optionsFont.addAsciiGlyphs();
			optionsFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			optionsFont.getEffects().add(new OutlineEffect(1, java.awt.Color.BLACK));
			optionsFont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		playItem = new MenuItem(optionsFont, "Play", 775, 300, Utilities.ALIGN_RIGHT, Color.white, Color.red);
		practiceItem = new MenuItem(optionsFont, "Practice", 775, 350, Utilities.ALIGN_RIGHT, Color.white, Color.red);
		leaderboardItem = new MenuItem(optionsFont, "Leaderboard", 775, 400, Utilities.ALIGN_RIGHT, Color.white, Color.red);
		optionsItem = new MenuItem(optionsFont, "Options", 775, 450, Utilities.ALIGN_RIGHT, Color.white, Color.red);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (playItem.isSelected(gc)) {
				parent.changeScreen(parent.getPlayScreen());
			}
		}
	}
	
	public void dispose() {
		
	}
	
	private class MenuItem {
		
		Font textFont;
		String itemText;
		int x, y, width, height;
		Color normalColor, selectedColor;
		
		MenuItem(Font f, String s, int _x, int _y, int align, Color normal, Color selected) {
			this.textFont = f;
			this.itemText = s;
			this.x = _x;
			this.y = _y;
			this.normalColor = normal;
			this.selectedColor = selected;
			
			width = f.getWidth(itemText);
			height = f.getLineHeight();
			
			switch (align) {
				case Utilities.ALIGN_CENTER:
					x -= width / 2;
					break;
				case Utilities.ALIGN_RIGHT:
					x -= width;
					break;
			}
		}
		
		private void render(GameContainer gc) {
			textFont.drawString(x, y, itemText, isSelected(gc) ? selectedColor : normalColor);
		}
		
		private boolean isSelected(GameContainer gc) {
			int mX = gc.getInput().getMouseX();
			int mY = gc.getInput().getMouseY();
			
			if (mX > x && mX < x + width)
				if (mY > y && mY < y + height)
					return true;
			return false;
		}
	}

}
