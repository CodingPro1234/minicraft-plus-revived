package minicraft.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import minicraft.core.FileHandler;
import minicraft.core.Game;
import minicraft.core.io.InputHandler;
import minicraft.core.io.Sound;
import minicraft.gfx.*;
import minicraft.saveload.Save;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;

/* The skins are put in a folder generated by the game called "skins".
 * Many skins can be put according to the number of files.
 */
public class SkinDisplay extends Display {
	private static final List<String> skinNames = new ArrayList<>();
	private static final int defaultSkins;
	private static final List<SpriteSheet> customSkins = new ArrayList<>();
	private static int selectedSkinIndex = 0;

	private int step;

	static {
		// These are all the generic skins. To add one, just add an entry in this list.
		skinNames.add("Paul");
		skinNames.add("Paul with cape");
		skinNames.add("Familiar boy");
		skinNames.add("Familiar girl");

		// Never remove this
		defaultSkins = skinNames.size();

		// Get the folder containing the skins.
		File skinFolder = new File(FileHandler.getSystemGameDir() + "/" + FileHandler.getLocalGameDir() + "/skins");

		// Create folder, and see if it was successful.
		if (skinFolder.mkdirs()) {
			if (Game.debug) System.out.println("Skin folder created.");
		}

		// Read and add the .png file to the skins list.
		for (String fileName : Objects.requireNonNull(skinFolder.list())) {
			if (fileName.endsWith(".png")) { // Only .png skins files
				BufferedImage image = null;
				try {
					image = ImageIO.read(new FileInputStream(skinFolder + "/" + fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}

				// If we found an image.
				if (image != null) {
					SpriteSheet spriteSheet = new SpriteSheet(image);

					// Check if sheet is a multiple of 8.
					if (spriteSheet.width % 8 == 0 && spriteSheet.height % 8 == 0) {
						// Add the spritesheet to the custom skins list.
						customSkins.add(spriteSheet);

						// Remove the filetype (.png) and to the .
						skinNames.add(fileName.substring(0, fileName.length()-4));
					} else {
						// Go here if image has wrong dimensions.
						System.err.println("Error: Custom skin at " + fileName + "has incorrect width or height. Should be a multiple of 8.");
					}
				}
			}
		}
	}

	public SkinDisplay() {
		if (selectedSkinIndex >= skinNames.size()) selectedSkinIndex = 0;
	}

    @Override
    public void onExit() {
		// Save the selected skin.
		new Save();

		// Play confirm sound.
        Sound.confirm.play();
    }

	@Override
	public void tick(InputHandler input) {
		if (input.getKey("menu").clicked || input.getKey("attack").clicked || input.getKey("exit").clicked) {
			Game.exitMenu();
			return;
		}
		if (input.getKey("cursor-down").clicked && selectedSkinIndex < skinNames.size() - 1) {
			selectedSkinIndex++;
			Sound.select.play();
		}
		if (input.getKey("cursor-up").clicked && selectedSkinIndex > 0) {
			selectedSkinIndex--;
			Sound.select.play();
		}
	}

	@Override
	public void render(Screen screen) {
		screen.clear(0);
		step++;

		// Get skin above and below.
		String selectedUpUp = selectedSkinIndex + 2 > skinNames.size() - 2 ? "" : skinNames.get(selectedSkinIndex + 2);
		String selectedUp = selectedSkinIndex + 1 > skinNames.size() - 1 ? "" : skinNames.get(selectedSkinIndex + 1);
		String selectedDown = selectedSkinIndex - 1 < 0 ? "" : skinNames.get(selectedSkinIndex - 1);
		String selectedDownDown = selectedSkinIndex - 2 < 0 ? "" : skinNames.get(selectedSkinIndex - 2);

		// Title.
		Font.drawCentered("Skins", screen, Screen.h - 180, Color.YELLOW);

		// Render the menu.
		Font.drawCentered(SkinDisplay.shortNameIfLong(selectedUpUp), screen, Screen.h - 60, Color.GRAY); // First unselected space
		Font.drawCentered(SkinDisplay.shortNameIfLong(selectedUp), screen, Screen.h - 70, Color.GRAY); // Second unselected space
		Font.drawCentered(SkinDisplay.shortNameIfLong(skinNames.get(selectedSkinIndex)), screen, Screen.h - 80, Color.GREEN); // Selection
		Font.drawCentered(SkinDisplay.shortNameIfLong(selectedDown), screen, Screen.h - 90, Color.GRAY); // Third space
		Font.drawCentered(SkinDisplay.shortNameIfLong(selectedDownDown), screen, Screen.h - 100, Color.GRAY); // Fourth space

		// Help text.
		Font.drawCentered("Use "+ Game.input.getMapping("cursor-down") + " and " + Game.input.getMapping("cursor-up") + " to move.", screen, Screen.h - 19, Color.WHITE);
		Font.drawCentered(Game.input.getMapping("SELECT") + " to select.", screen, Screen.h - 11, Color.WHITE);

		int h = 2;
		int w = 2;
		int xoffset = Screen.w / 2 - w * 4; // Put this in the center of the screen
		int yoffset = 38;

		int spriteIndex = (step / 40) % 8; // 9 = 8 Frames for sprite

		// Render preview of skin.
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++)
				if (selectedSkinIndex < defaultSkins) {
					screen.render(xoffset + x * 8, yoffset + y * 8, spriteIndex * 2 + x + (y + selectedSkinIndex * 4) * 32, 0, 4);
				} else {
					SpriteSheet spriteSheet = customSkins.get(selectedSkinIndex - defaultSkins);
					screen.render(xoffset + x * 8, yoffset + y * 8, spriteIndex * 2 + x + y * 32, 0, spriteSheet, - 1, false);
				}
	}


	// In case the name is too big ...
	private static String shortNameIfLong(String name) {
		return name.length() > 22 ? name.substring(0, 16) + "..." : name;
	}

	public static int getSelectedSkinIndex() {
		return selectedSkinIndex;
	}

	public static void setSelectedSkinIndex(int selectedSkinIndex) {
		SkinDisplay.selectedSkinIndex = selectedSkinIndex;
	}

	// First array is one of the four animations.
	@NotNull
	public static MobSprite[][][] getSkinAsMobSprite() {
		MobSprite[][][] mobSprites = new MobSprite[4][][];

		if (selectedSkinIndex < defaultSkins) {
			mobSprites[0] = MobSprite.compilePlayerSpriteAnimations(0, SkinDisplay.getSelectedSkinIndex() * 4);
			mobSprites[1] = MobSprite.compilePlayerSpriteAnimations(0, SkinDisplay.getSelectedSkinIndex() * 4 + 2);
			mobSprites[2] = MobSprite.compilePlayerSpriteAnimations(8, SkinDisplay.getSelectedSkinIndex() * 4 + 2);
			mobSprites[3] = MobSprite.compilePlayerSpriteAnimations(8, SkinDisplay.getSelectedSkinIndex() * 4);
		} else {
			mobSprites[0] = MobSprite.compileCustomPlayerSpriteAnimations(0, 0, customSkins.get(selectedSkinIndex - defaultSkins));
			mobSprites[1] = MobSprite.compileCustomPlayerSpriteAnimations(0, 2, customSkins.get(selectedSkinIndex - defaultSkins));
			mobSprites[2] = MobSprite.compileCustomPlayerSpriteAnimations(8, 2, customSkins.get(selectedSkinIndex - defaultSkins));
			mobSprites[3] = MobSprite.compileCustomPlayerSpriteAnimations(8, 0, customSkins.get(selectedSkinIndex - defaultSkins));
		}

		return mobSprites;
	}
}
