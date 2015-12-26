
import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


//场景管理器，继承角色管理器，含有游戏角色

//场景管理器判断角色移动的越界问题

public class SceneManager extends CharacterManager {
	
	//一共10*10个场景
	private final int SCENE_WIDTH = World.SCENE_WIDTH, SCENE_HEIGHT = World.SCENE_HEIGHT;
	//100个场景
	private Areas areas[][] = new Areas[SCENE_WIDTH][SCENE_HEIGHT];
	
	//当前显示场景，屏幕左上角开始
	private int display_x = 0, display_y = 0;
	
	//主角的金钱条和士兵条
	private Image money_bar = null, soldier_bar = null;
	
	private Image money_num = null, soldier_num = null;
	
	//当前领地归属者
	private Image[] owner_map = new Image[4];
	
	private ChooseBattle choose_battle = new ChooseBattle();
	
	
	
	public SceneManager() {
		for (int i = 0; i < SCENE_WIDTH; i++) 
		{
			for (int j = 0; j < SCENE_HEIGHT; j++) 
			{
				switch (World.scenery[i][j]) {
				case "city":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, 5);
					break;
				case "simple":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, 7);
					break;
				case "shop":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, 2);
					break;
				case "soldier":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, 3);
					break;
				case "island":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, 4);
					break;
				}
			}
		}
		
		try {
			money_bar = new Image("res/money_bar.png");
			soldier_bar = new Image("res/soldier_bar.png");
			money_num = new Image("res/money_num.png");
			soldier_num = new Image("res/soldier_num.png");
			
			owner_map[0] = new Image("res/song_flag.png");
			owner_map[1] = new Image("res/lin_flag.png");
			owner_map[2] = new Image("res/hu_flag.png");
			owner_map[3] = new Image("res/lu_flag.png");
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCX() {
		return display_x;
	}
	
	public int getCY() {
		return display_y;
	}
	
	//碰撞检测
	public boolean intersects(GameCharacter gc) {
		
		int num = 4;
		
		//检查每个点是否位于非AIR方块中
		for (int i = 0; i <= num; i++) {
			
			int x = Math.round((gc.getX() + i * World.CHARACTER_SIZE / num - World.BLOCK_SIZE) / World.BLOCK_SIZE);
			
			for (int j = 0; j <= num; j++) {
	
				int y = Math.round((gc.getY() + j * World.CHARACTER_SIZE / num - World.BLOCK_SIZE) / World.BLOCK_SIZE);
				
				if (x - 1 < 0) {
					x = 1;
				}
				if (y - 1 < 0) {
					y = 1;
				}
				//System.out.println("(" + main_game_character.getI() + ","  + main_game_character.getJ() + ")" + "("+x+","+y+")");
				if (areas[gc.getI()][gc.getJ()].getAt(x, y).getType() == BlockType.DIRT) {
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	//主角移动
	public void moveMainCharacter(int step_len) {
		main_game_character.move(step_len);
	}
	
	//场景内角色随机移动
	public void randomMoveCharacter() {	
		int step_len = 2;
		for (GameCharacter gameCharacter : leader) {
			//System.out.println("in scene thread");
			//
			gameCharacter.subStep();
			//根据状态行走
			switch (gameCharacter.getMoveState()) {
			case RIGHT:
				gameCharacter.moveRight(step_len);
				if (intersects(gameCharacter)) {
					gameCharacter.moveLeft(step_len);
					gameCharacter.setMoveState(GameCharacter.MoveState.LEFT);
					gameCharacter.subStep();
				}
				break;
			case LEFT:
				gameCharacter.moveLeft(step_len);
				if (intersects(gameCharacter)) {
					gameCharacter.moveRight(step_len);
					gameCharacter.setMoveState(GameCharacter.MoveState.RIGHT);
					gameCharacter.subStep();
				}
				break;
			case UP:
				gameCharacter.moveUp(step_len);
				if (intersects(gameCharacter)) {
					gameCharacter.moveDown(step_len);
					gameCharacter.setMoveState(GameCharacter.MoveState.DOWN);
					gameCharacter.subStep();
				}
				break;
			case DOWN:
				gameCharacter.moveDown(step_len);
				if (intersects(gameCharacter)) {
					gameCharacter.moveUp(step_len);
					gameCharacter.setMoveState(GameCharacter.MoveState.UP);
					gameCharacter.subStep();
				}
				break;
			default:
				
				break;
			}
		}
	}
	
	//绘制当前场景
	public void draw() {
		
		//先获取当前场景位置
		display_x = main_game_character.getI();
		display_y = main_game_character.getJ();
		
		//绘制背景
		areas[display_x][display_y].draw();
		
		//绘制角色
		for (GameCharacter gameCharacter : character_list) {
			if (gameCharacter.getI() == display_x && gameCharacter.getJ() == display_y) {
				gameCharacter.draw();
			}
		}
		
		//绘制角色
		for (GameCharacter gameCharacter : leader) {
			if (gameCharacter.getI() == display_x && gameCharacter.getJ() == display_y) {
				gameCharacter.draw();
			}
		}
		
		//绘制主角
		main_game_character.draw();
		
		money_bar.draw();
		soldier_bar.draw(money_bar.getWidth(), 0);
		
		int grid_1 = main_game_character.getMoney() / 100;
		int grid_2 = main_game_character.getNum_of_soldiers() / 500;
		
		float startX = 113, startY = 75;
		for (int i = 0; i < grid_1; i++) {
			//startX += 1.7;
			money_num.draw(startX, startY);
			startX += 0.07;
		}
		
		startX = 561;
		for (int i = 0; i < grid_2; i++) {
			soldier_num.draw(startX, startY);
			startX -= 18;
		}
		
		//绘制当前领地归属者
		switch (areas[display_x][display_y].owner.getType()) {
		case SONG:
			owner_map[0].draw(World.SCREEN_WIDTH - owner_map[0].getWidth(), 0);
			break;
		case LIN:
			owner_map[1].draw(World.SCREEN_WIDTH - owner_map[1].getWidth(), 0);
			break;
		case HU:
			owner_map[2].draw(World.SCREEN_WIDTH - owner_map[2].getWidth(), 0);
			break;
		case LU:
			owner_map[3].draw(World.SCREEN_WIDTH - owner_map[3].getWidth(), 0);
			break;
		default:
			break;
		}
		
		if (main_game_character.choose_state) {
			choose_battle.draw();
		}
	}
	
	//检查自己是否处在别人的城池内
	public void check(MainCharacter mc) {
		
		int i = mc.getI(), j = mc.getJ();
		if (areas[i][j].owner == null) {
			areas[i][j].setOwner(mc);
			mc.choose_state = false;
		}
		else if (areas[i][j].owner.getType() != mc.getType()) { //如果是别人的城池
			mc.choose_state = true;
			//如果是玩家发起的操作
			if (mc.getType() == main_game_character.getType()) {
				mc.battle_mod = choose_battle.input();
			}
			//mc.addMoney(-1000);
		}
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//以下为所有公开到Boot主循环的函数,全部用于与用户交互，故只跟当前场景位置有关
	
	public void setAreaOwner(int i, int j, MainCharacter gc) {
		areas[i][j].setOwner(gc);
	}
	//鼠标点击
	public void setAt(int x, int y, BlockType b) {
		areas[display_x][display_y].setAt(x, y, b);
	}
	
	public void save() {
		String filename = "scene.xml";
		areas[display_x][display_y].save(new File(filename));
	}
	
	public void load() {
		String filename = "scene.xml";
		areas[display_x][display_y].load(new File(filename));
	}
	
	public void clear() {
		areas[display_x][display_y].clear();
	}
	
	//获取当前场景
	public Areas getCurrentArea() {
		return areas[display_x][display_y];
	}
	
	

}
