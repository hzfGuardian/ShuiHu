
import java.io.File;


//场景管理器，继承角色管理器，含有游戏角色

//场景管理器判断角色移动的越界问题

public class SceneManager extends CharacterManager {
	
	//一共10*10个场景
	private final int SCENE_WIDTH = World.SCENE_WIDTH, SCENE_HEIGHT = World.SCENE_HEIGHT;
	//100个场景
	private Areas areas[][] = new Areas[SCENE_WIDTH][SCENE_HEIGHT];
	
	//当前显示场景，屏幕左上角开始
	private int display_x = 0, display_y = 0;
	
	public SceneManager() {
		for (int i = 0; i < SCENE_WIDTH; i++) 
		{
			for (int j = 0; j < SCENE_HEIGHT; j++) 
			{
				switch (World.scenery[i][j]) {
				case "city":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, "city");
					break;
				case "simple":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, "simple");
					break;
				case "shop":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, "simple");
					break;
				case "soldier":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, "simple");
					break;
				case "island":
					areas[i][j] = new Areas(Areas.SceneryType.CITY, "simple");
					break;
				}
			}
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
	
	
	
	//场景内角色随机移动
	public void randomMoveCharacter() {	
		int step_len = 2;
		for (GameCharacter gameCharacter : character_list) {
			//System.out.println("in scene thread");
			//
			gameCharacter.subStep();
			//根据状态行走
			switch (gameCharacter.getMoveState()) {
			case RIGHT:
				gameCharacter.moveDoubleRight(step_len);
				if (intersects(gameCharacter)) {
					gameCharacter.moveDoubleLeft(step_len);
					gameCharacter.setMoveState(GameCharacter.MoveState.LEFT);
					gameCharacter.subStep();
				}
				break;
			case LEFT:
				gameCharacter.moveDoubleLeft(step_len);
				if (intersects(gameCharacter)) {
					gameCharacter.moveDoubleRight(step_len);
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
		for (GameCharacter gameCharacter : other_list) {
			if (gameCharacter.getI() == display_x && gameCharacter.getJ() == display_y) {
				gameCharacter.draw();
			}
		}
		
		//绘制主角
		main_game_character.draw();
		
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//以下为所有公开到Boot主循环的函数,全部用于与用户交互，故只跟当前场景位置有关
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

}
