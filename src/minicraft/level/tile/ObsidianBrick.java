package minicraft.level.tile;

import minicraft.Game;
import minicraft.entity.Entity;
import minicraft.entity.Player;
import minicraft.gfx.Color;
import minicraft.gfx.Screen;
import minicraft.item.Item;
import minicraft.item.ToolItem;
import minicraft.item.ToolType;
import minicraft.level.Level;
import minicraft.sound.Sound;

public class ObsidianBrick extends Tile {
	public ObsidianBrick(int id) {
		super(id);
		maySpawn = true;
	}

	public void render(Screen screen, Level level, int x, int y) {
		int col = Color.get(102, 102, 203, 203);
		screen.render(x * 16 + 0, y * 16 + 0, 19 + 2 * 32, col, 0);
		screen.render(x * 16 + 8, y * 16 + 0, 19 + 2 * 32, col, 0);
		screen.render(x * 16 + 0, y * 16 + 8, 19 + 2 * 32, col, 0);
		screen.render(x * 16 + 8, y * 16 + 8, 19 + 2 * 32, col, 0);
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.pickaxe) {
				if (player.payStamina(4 - tool.level)) {
					level.setTile(xt, yt, Tile.hole, 0);
					Sound.monsterHurt.play();
					return true;
				}
			}
			if (tool.type == ToolType.pick) {
				if (player.payStamina(4 - tool.level)) {
					level.setTile(xt, yt, Tile.hole, 0);
					Sound.monsterHurt.play();
					return true;
				}
			}
		}
		return false;
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		return true;
	}
}
