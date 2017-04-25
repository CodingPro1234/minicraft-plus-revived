package com.mojang.ld22.entity;

import com.mojang.ld22.Game;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.gfx.MobSprite;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.screen.ModeMenu;
import com.mojang.ld22.screen.OptionsMenu;

public class Pig extends PassiveMob {
	private static MobSprite[][] sprites = MobSprite.compileMobSpriteAnimations(16, 14);
	/*private static int[][] colors = {
		{Color.get(-1, 000, 444, 411), Color.get(-1, 000, 555, 522), Color.get(-1, 000, 333, 311), Color.get(-1, 000, 222, 211)},
		{Color.get(-1, 000, 555, 522), Color.get(-1, 000, 555, 522), Color.get(-1, 000, 555, 522), Color.get(-1, 000, 555, 522)},
		{Color.get(-1, 000, 444, 522)}
	};*/
	
	public Pig() {
		super(sprites/*, colors*/);
		
		col0 = Color.get(-1, 000, 444, 411);
		col1 = Color.get(-1, 000, 555, 522);
		col2 = Color.get(-1, 000, 333, 311);
		col3 = Color.get(-1, 000, 222, 211);
		col4 = Color.get(-1, 000, 444, 522);
	}
	/*
	public void tick() {
		super.tick();
		
		if (health < maxHealth) {
			if (level.player != null && randomWalkTime == 0) {
				int xd = level.player.x - x;
				int yd = level.player.y - y;
				if (xd * xd + yd * yd < 200 * 200) {
					xa = 0;
					ya = 0;
					if (xd < 0) xa = +1;
					xe = xa;
					if (xd > 0) xa = -1;
					xe = xa;
					if (yd < 0) ya = +1;
					xe = xa;
					if (yd > 0) ya = -1;
					xe = xa;
				}
			}
		}

		int speed = tickTime & 1;
		if (!move(xa * speed, ya * speed) || random.nextInt(40) == 0) {
			randomWalkTime = 45;
			xa = (random.nextInt(3) - 1) * random.nextInt(2);
			ya = (random.nextInt(3) - 1) * random.nextInt(2);
		}
		if (randomWalkTime > 0) randomWalkTime--;
	}*/

	public void render(Screen screen) {
		/*int xt = 16;
		int yt = 14;

		int flip1 = (walkDist >> 3) & 1;
		int flip2 = (walkDist >> 3) & 1;

		if (dir == 1) {
			xt += 2;
		}
		if (dir > 1) {

			flip1 = 0;
			flip2 = ((walkDist >> 4) & 1 / 2);
			if (dir == 2) {
				flip1 = 1;
				flip2 = 1;
			}
			xt += 4 + ((walkDist >> 3) & 1) * 2;
		}

		int xo = x - 8;
		int yo = y - 11;
		*/
		col0 = Color.get(-1, 000, 444, 411);
		col1 = Color.get(-1, 000, 555, 522);
		col2 = Color.get(-1, 000, 333, 311);
		col3 = Color.get(-1, 000, 222, 211);
		col4 = Color.get(-1, 000, 444, 522);
		
		if (isLight()) {
			col0 = col1 = col2 = col3 = col4 = Color.get(-1, 000, 555, 522);
		}
		
		if (level.dirtColor == 322) {
			if (Game.time == 0) col = col0;
			if (Game.time == 1) col = col1;
			if (Game.time == 2) col = col2;
			if (Game.time == 3) col = col3;
		} else col = col4;
		
		super.render(screen);
	}

	public boolean canWool() {
		return true;
	}

	protected void die() {
		int min = 0, max = 0;
		if (OptionsMenu.diff == OptionsMenu.easy) {min = 1; max = 3;}
		if (OptionsMenu.diff == OptionsMenu.norm) {min = 1; max = 2;}
		if (OptionsMenu.diff == OptionsMenu.hard) {min = 0; max = 2;}
		
		dropResource(min, max, Resource.rawpork);
		
		super.die();
	}
}
