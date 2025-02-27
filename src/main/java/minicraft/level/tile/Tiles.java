package minicraft.level.tile;

import minicraft.core.CrashHandler;
import minicraft.level.tile.farming.FarmTile;
import minicraft.level.tile.farming.PotatoTile;
import minicraft.level.tile.farming.WheatTile;
import minicraft.util.Logging;

import java.util.ArrayList;
import java.util.HashMap;

public final class Tiles {
	/// Idea: to save tile names while saving space, I could encode the names in base 64 in the save file...^M
	/// Then, maybe, I would just replace the id numbers with id names, make them all private, and then make a get(String) method, parameter is tile name.

	public static ArrayList<String> oldids = new ArrayList<>();

	private static HashMap<Short, Tile> tiles = new HashMap<>();

	public static void initTileList() {
		Logging.TILES.debug("Initializing tile list...");

		tiles.put((short)0, new GrassTile("Grass"));
		tiles.put((short)1, new DirtTile("Dirt"));
		tiles.put((short)2, new FlowerTile("Flower"));
		tiles.put((short)3, new HoleTile("Hole"));
		tiles.put((short)4, new StairsTile("Stairs Up", true));
		tiles.put((short)5, new StairsTile("Stairs Down", false));
		tiles.put((short)6, new WaterTile("Water"));
		// This is out of order because of lava buckets
		tiles.put((short)17, new LavaTile("Lava"));

		tiles.put((short)7, new RockTile("Rock"));
		tiles.put((short)8, new TreeTile("Tree"));
		tiles.put((short)9, new SaplingTile("Tree Sapling", Tiles.get("Grass"), Tiles.get("Tree")));
		tiles.put((short)10, new SandTile("Sand"));
		tiles.put((short)11, new CactusTile("Cactus"));
		tiles.put((short)12, new SaplingTile("Cactus Sapling", Tiles.get("Sand"), Tiles.get("Cactus")));
		tiles.put((short)13, new OreTile(OreTile.OreType.Iron));
		tiles.put((short)14, new OreTile(OreTile.OreType.Gold));
		tiles.put((short)15, new OreTile(OreTile.OreType.Gem));
		tiles.put((short)16, new OreTile(OreTile.OreType.Lapis));
		tiles.put((short)18, new LavaBrickTile("Lava Brick"));
		tiles.put((short)19, new ExplodedTile("Explode"));
		tiles.put((short)20, new FarmTile("Farmland"));
		tiles.put((short)21, new WheatTile("Wheat"));
		tiles.put((short)22, new HardRockTile("Hard Rock"));
		tiles.put((short)23, new InfiniteFallTile("Infinite Fall"));
		tiles.put((short)24, new CloudTile("Cloud"));
		tiles.put((short)25, new OreTile(OreTile.OreType.Cloud));
		tiles.put((short)26, new DoorTile(Tile.Material.Wood));
		tiles.put((short)27, new DoorTile(Tile.Material.Stone));
		tiles.put((short)28, new DoorTile(Tile.Material.Obsidian));
		tiles.put((short)29, new FloorTile(Tile.Material.Wood));
		tiles.put((short)30, new FloorTile(Tile.Material.Stone));
		tiles.put((short)31, new FloorTile(Tile.Material.Obsidian));
		tiles.put((short)32, new WallTile(Tile.Material.Wood));
		tiles.put((short)33, new WallTile(Tile.Material.Stone));
		tiles.put((short)34, new WallTile(Tile.Material.Obsidian));
		tiles.put((short)35, new WoolTile(WoolTile.WoolType.NORMAL));
		tiles.put((short)36, new PathTile("Path"));
		tiles.put((short)37, new WoolTile(WoolTile.WoolType.RED));
		tiles.put((short)38, new WoolTile(WoolTile.WoolType.BLUE));
		tiles.put((short)39, new WoolTile(WoolTile.WoolType.GREEN));
		tiles.put((short)40, new WoolTile(WoolTile.WoolType.YELLOW));
		tiles.put((short)41, new WoolTile(WoolTile.WoolType.BLACK));
		tiles.put((short)42, new PotatoTile("Potato"));
		tiles.put((short)43, new MaterialTile(Tile.Material.Stone));
		tiles.put((short)44, new MaterialTile(Tile.Material.Obsidian));
		tiles.put((short)45, new DecorTile(Tile.Material.Stone));
		tiles.put((short)46, new DecorTile(Tile.Material.Obsidian));
		tiles.put((short)47, new BossWallTile());
		tiles.put((short)48, new BossFloorTile());
		tiles.put((short)49, new BossDoorTile());

		// WARNING: don't use this tile for anything!
		tiles.put((short)255, new ConnectTile());

		for(short i = 0; i < 256; i++) {
			if(tiles.get(i) == null) continue;
			tiles.get(i).id = (short)i;
		}
	}


	protected static void add(int id, Tile tile) {
		tiles.put((short)id, tile);
		Logging.TILES.debug("Adding " + tile.name + " to tile list with id " + id);
		tile.id = (short) id;
	}

	static {
		for(int i = 0; i < 32768; i++)
			oldids.add(null);

		oldids.set(0, "grass");
		oldids.set(1, "rock");
		oldids.set(2, "water");
		oldids.set(3, "flower");
		oldids.set(4, "tree");
		oldids.set(5, "dirt");
		oldids.set(41, "wool");
		oldids.set(42, "red wool");
		oldids.set(43, "blue wool");
		oldids.set(45, "green wool");
		oldids.set(127, "yellow wool");
		oldids.set(56, "black wool");
		oldids.set(6, "sand");
		oldids.set(7, "cactus");
		oldids.set(8, "hole");
		oldids.set(9, "tree Sapling");
		oldids.set(10, "cactus Sapling");
		oldids.set(11, "farmland");
		oldids.set(12, "wheat");
		oldids.set(13, "lava");
		oldids.set(14, "stairs Down");
		oldids.set(15, "stairs Up");
		oldids.set(17, "cloud");
		oldids.set(30, "explode");
		oldids.set(31, "Wood Planks");
		oldids.set(33, "plank wall");
		oldids.set(34, "stone wall");
		oldids.set(35, "wood door");
		oldids.set(36, "wood door");
		oldids.set(37, "stone door");
		oldids.set(38, "stone door");
		oldids.set(39, "lava brick");
		oldids.set(32, "Stone Bricks");
		oldids.set(120, "Obsidian");
		oldids.set(121, "Obsidian wall");
		oldids.set(122, "Obsidian door");
		oldids.set(123, "Obsidian door");
		oldids.set(18, "hard Rock");
		oldids.set(19, "iron Ore");
		oldids.set(24, "Lapis");
		oldids.set(20, "gold Ore");
		oldids.set(21, "gem Ore");
		oldids.set(22, "cloud Cactus");
		oldids.set(16, "infinite Fall");

		// Light/torch versions, for compatibility with before 1.9.4-dev3. (were removed in making dev3)
		oldids.set(100, "grass");
		oldids.set(101, "sand");
		oldids.set(102, "tree");
		oldids.set(103, "cactus");
		oldids.set(104, "water");
		oldids.set(105, "dirt");
		oldids.set(107, "flower");
		oldids.set(108, "stairs Up");
		oldids.set(109, "stairs Down");
		oldids.set(110, "Wood Planks");
		oldids.set(111, "Stone Bricks");
		oldids.set(112, "wood door");
		oldids.set(113, "wood door");
		oldids.set(114, "stone door");
		oldids.set(115, "stone door");
		oldids.set(116, "Obsidian door");
		oldids.set(117, "Obsidian door");
		oldids.set(119, "hole");
		oldids.set(57, "wool");
		oldids.set(58, "red wool");
		oldids.set(59, "blue wool");
		oldids.set(60, "green wool");
		oldids.set(61, "yellow wool");
		oldids.set(62, "black wool");
		oldids.set(63, "Obsidian");
		oldids.set(64, "tree Sapling");
		oldids.set(65, "cactus Sapling");

		oldids.set(44, "torch grass");
		oldids.set(40, "torch sand");
		oldids.set(46, "torch dirt");
		oldids.set(47, "torch wood planks");
		oldids.set(48, "torch stone bricks");
		oldids.set(49, "torch Obsidian");
		oldids.set(50, "torch wool");
		oldids.set(51, "torch red wool");
		oldids.set(52, "torch blue wool");
		oldids.set(53, "torch green wool");
		oldids.set(54, "torch yellow wool");
		oldids.set(55, "torch black wool");
	}

	private static int overflowCheck = 0;
	public static Tile get(String name) {
		//System.out.println("Getting from tile list: " + name);

		name = name.toUpperCase();

		overflowCheck++;

		if(overflowCheck > 50) {
			CrashHandler.crashHandle(new StackOverflowError("Tiles#get: " + name), new CrashHandler.ErrorInfo("Tile fetching Stacking",
				CrashHandler.ErrorInfo.ErrorType.SERIOUS, "STACKOVERFLOW prevented in Tiles.get(), on: " + name));
		}

		//System.out.println("Fetching tile " + name);

		Tile getting = null;

		boolean isTorch = false;
		if(name.startsWith("TORCH")) {
			isTorch = true;
			name = name.substring(6); // Cuts off torch prefix.
		}

		if(name.contains("_")) {
			name = name.substring(0, name.indexOf("_"));
		}

		for(Tile t: tiles.values()) {
			if(t == null) continue;
			if(t.name.equals(name)) {
				getting = t;
				break;
			}
		}

		if(getting == null) {
			Logging.TILES.info("Invalid tile requested: " + name);
			getting = tiles.get((short)0);
		}

		if(isTorch) {
			getting = TorchTile.getTorchTile(getting);
		}

		overflowCheck = 0;
		return getting;
	}

	public static Tile get(int id) {
		//System.out.println("Requesting tile by id: " + id);
		if(id < 0) id += 32768;

		if(tiles.get((short)id) != null) {
			return tiles.get((short)id);
		}
		else if(id >= 32767) {
			return TorchTile.getTorchTile(get(id - 32767));
		}
		else {
			Logging.TILES.info("Unknown tile id requested: " + id);
			return tiles.get((short)0);
		}
	}

	public static boolean containsTile(int id) {
		return tiles.get((short)id) != null;
	}

	public static String getName(String descriptName) {
		if(!descriptName.contains("_")) return descriptName;
		int data;
		String[] parts = descriptName.split("_");
		descriptName = parts[0];
		data = Integer.parseInt(parts[1]);
		return get(descriptName).getName(data);
	}

	public static HashMap<Short, Tile> getAll() {
		return new HashMap<>(tiles);
	}
}
