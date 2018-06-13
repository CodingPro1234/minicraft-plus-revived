package minicraft.screen;

import javax.swing.Timer;

import minicraft.core.Game;
import minicraft.core.Updater;
import minicraft.core.World;
import minicraft.core.io.Localization;
import minicraft.gfx.Color;
import minicraft.gfx.Font;
import minicraft.gfx.FontStyle;
import minicraft.gfx.Screen;
import minicraft.saveload.Save;

public class LoadingDisplay extends Display {
	
	private static float percentage = 0;
	private static String progressType = "";
	
	private Timer t;
	private String msg = "";
	
	public LoadingDisplay() {
		super(true,false);
		t = new Timer(500, e -> {
			World.initWorld();
			Game.setMenu(null);
		});
		t.setRepeats(false);
	}
	
	@Override
	public void init(Display parent) {
		super.init(parent);
		percentage = 0;
		progressType = "World";
		if(WorldSelectDisplay.loadedWorld())
			msg = "Loading";
		else
			msg = "Generating";
		t.start();
	}
	
	@Override
	public void onExit() {
		percentage = 0;
		if(!WorldSelectDisplay.loadedWorld()) {
			msg = "Saving";
			progressType = "World";
			new Save(WorldSelectDisplay.getWorldName());
			Game.notifications.clear();
		}
	}
	
	public static void setPercentage(float percent) {
		percentage = percent;
	}
	public static float getPercentage() { return percentage; }
	public static void setMessage(String progressType) { LoadingDisplay.progressType = progressType; }
	
	public static void progress(float amt) {
		percentage = Math.min(100, percentage+amt);
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		int percent = Math.round(percentage);
		Font.drawParagraph(screen, new FontStyle(Color.RED), 6,
			Localization.getLocalized(msg)+(progressType.length()>0?" "+Localization.getLocalized(progressType):"")+ getEllipses(),
			percent+"%"
		);
	}
	
	private int ePos = 0;
	private int eposTick = 0;
	private String getEllipses() {
		StringBuilder dots = new StringBuilder();
		for(int i = 0; i < 3; i++) {
			if (ePos == i)
				dots.append(".");
			else
				dots.append(" ");
		}
		
		eposTick++;
		if(eposTick >= Updater.normSpeed) {
			eposTick = 0;
			ePos++;
		}
		if(ePos >= 3) ePos = 0;
		
		return dots.toString();
	}
}
