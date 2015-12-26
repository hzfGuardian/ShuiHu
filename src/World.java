

import java.util.Random;

public class World {
	public static final int BLOCK_SIZE = 32;
	public static final int BLOCKS_WIDTH = 48, BLOCKS_HEIGHT = 40;
	
	public static final int CHARACTER_SIZE = 100;
	
	//total size of the whole world
	public static final int WORLD_SIZE = 10000;
	public static final int SCENE_WIDTH = 10, SCENE_HEIGHT = 10;
	
	//屏幕大小
	public static final int SCREEN_WIDTH = 1024, SCREEN_HEIGHT = 576;
	
	//随机数生成器
	public static Random RAND = new Random();
	
	//世界计时器
	public static Timer TIMER = new Timer();
	
	//场景类型
	public static String scenery[][] = {
		{"simple", "city", "city", "city", "shop", "city", "city", "city", "simple", "city"},
		{"city", "city", "city", "city", "city", "city", "soldier", "city", "city", "city"},
		{"shop", "city", "city", "simple", "city", "city", "city", "shop", "city", "simple"},
		{"city", "city", "soldier", "city", "city", "island", "city", "city", "city", "city"},
		{"simple", "city", "city", "city", "simple", "city", "city", "city", "soldier", "simple"},
		{"city", "simple", "city", "shop", "city", "city", "city", "city", "city", "city"},
		{"city", "city", "city", "city", "city", "city", "city", "simple", "city", "shop"},
		{"city", "city", "city", "city", "simple", "city", "shop", "city", "city", "city"},
		{"city", "city", "city", "city", "city", "shop", "city", "simple", "city", "city"},
		{"city", "shop", "city", "city", "city", "city", "city", "city", "city", "soldier"}
	};
	
}
