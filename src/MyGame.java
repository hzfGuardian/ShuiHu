
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;


public class MyGame extends BasicGame {
	
	private StartUp startup;
	private Battle battle = new Battle(0);
	private GameContainer container;
	private Music music, battle_music;
	
	//游戏状态：0,正常行走状态。1，战斗状态。2,开启游戏状态
	private byte game_state = 2;
	
	private int RESOLUTION_WIDTH = World.SCREEN_WIDTH, RESOLUTION_HEIGHT = World.SCREEN_HEIGHT;
	
	private BlockType selection = BlockType.STONE;
	
	//游戏角色
	private SceneManager manager;
	
	//private int pos_x = 2, pos_y = 2;
	
	//角色移动的速度
	private int step_len = 10;
	
	private int selector_x = 0, selector_y = 0;
	
    public MyGame(String title) {
        super(title);
        
    }
    
    public void init(GameContainer container) throws SlickException {
    	this.container = container;
    	//初始化场景管理器
		manager = new SceneManager();
		
		//Initialization code OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
		
		//add in tutorial 13
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		//配音效
		music = new Music("res/music.ogg");
		battle_music = new Music("res/battle.ogg");
		music.play();
		
		//启动界面
		startup = new StartUp();
		
		World.TIMER.start();
    }
    
    @Override
    public void keyReleased(int key, char c) {
    	
    	//键盘松开时，主角必须停下来
    	manager.main_game_character.setMoveState(GameCharacter.MoveState.STOP);
    	
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	
    	container.setTargetFrameRate(20);
    	input();
    	
    	
    	if (game_state == 0) {
    		manager.randomMoveCharacter();
    		
		}
    	
    	//战斗状态
    	if (game_state == 1) {
    		
    		battle.update();
    		game_state = 0;
    		//System.out.println(battle.attackRest());
    		//System.out.println(battle.offenseRest());
    		
    		switch (battle.checkEnd()) {
			case 0://还在打
				game_state = 1;
				System.out.println("both alive");
				break;
			case 1://防守方
				System.out.println("offense died");
				//城池被攻占
				manager.setAreaOwner(manager.getCX(), manager.getCY(), battle.attacker);
				game_state = 0;
				manager.main_game_character.battle_mod = 0;
				break;
			case 2://进攻方
				System.out.println("attack died");
				game_state = 0;
				manager.main_game_character.battle_mod = 0;
				break;
			case 3://平手
				System.out.println("both died");
				game_state = 0;
				manager.main_game_character.battle_mod = 0;
				break;
			default:
				break;
			}
    		if (game_state == 0) {
    			battle_music.stop();
        		music.play();
			}
		}
    	
    	//主角移动
		manager.moveMainCharacter(step_len);
		
		//检查城池归属
		manager.check(manager.main_game_character);
		for (MainCharacter mc : manager.leader) {
			manager.check(mc);
		}
		
  		//move(manager.main_game_character, step_len);
  		World.TIMER.end();
  		if (World.TIMER.getTime() > 1000) {
  			manager.main_game_character.addMoney(50);
  			World.TIMER.start();
		}
  		
  		if (manager.main_game_character.battle_mod != 0 && game_state == 0) {
  			music.stop();
    		battle_music.play();
  			game_state = 1;
			battle = new Battle(manager.main_game_character.battle_mod);
			battle.init(manager.main_game_character, manager.getCurrentArea().owner, 1, 500, manager.getCX(), manager.getCY());
		}
    }
    
    
    @Override
    public void render(GameContainer container, Graphics graphics) throws SlickException {
       	
       	glClear(GL_COLOR_BUFFER_BIT);
       	
       	//开启游戏状态
       	if (game_state == 2) {
       		startup.play();
		}
		else {
			//再画角色
			manager.draw();
			
			if (game_state == 1) {
				battle.draw();
			}
			//System.out.println("in main thread");
			/*
			int i, j, x, y;
			i = manager.getCX();j = manager.getCY();
			x = (int)manager.main_game_character.getX();y = (int)manager.main_game_character.getY();
			graphics.drawString("(" + i + ", " + j + ")" + "(" + x + ", " + y + ")", 10, 40);
			*/
			
			graphics.drawString("This is me", manager.main_game_character.getX(), manager.main_game_character.getY() - 15);
			
			//每人有多少兵力
			for (MainCharacter gc : manager.leader) {
				if (gc.getI() == manager.getCX() && gc.getJ() == manager.getCY()) {
					graphics.drawString("Money: " + gc.getMoney(), gc.getX(), gc.getY() - 15);
				}			
			}
			
			graphics.drawString("" + manager.main_game_character.getMoney(), 40, 45);
			graphics.drawString("" + manager.main_game_character.getNum_of_soldiers(), 620, 45);
		}
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new MyGame("小朋友齐齐来当梁山好汉"));
        app.setDisplayMode(1024, 576, false);
        app.start();
    }
    
    //捕捉用户的输入，鼠标或键盘，鼠标按键获取格子被点击的效果，而键盘获取要点击下去绘制的效果
  	private void input() {
  		
  		if (game_state == 2) {
  			int main_id;
			if ((main_id = startup.input()) != 0) {
				game_state = 0;
				//选择主角
				manager.init(main_id - 1);
			}
		}
  		
  		if (Mouse.isButtonDown(0)) {
  			//mouseEnabled = true;
  			int mousex = Mouse.getX();
  			int mousey = World.SCREEN_HEIGHT - Mouse.getY() - 1;
  			
  			System.out.println(mousex + " " + mousey);
  			
  			boolean mouseClicked = Mouse.isButtonDown(0);
  			
  			selector_x = Math.round(mousex / World.BLOCK_SIZE);
  			selector_y = Math.round(mousey / World.BLOCK_SIZE);			
  			if (mouseClicked) {
  				manager.setAt(selector_x, selector_y, selection);
  				//System.out.println(selector_x + "," + selector_y);
  			}
  		}
  				
  	}
  	
  	//角色移动的函数
  	public void move(GameCharacter gc, int step_len) {
  		//根据行走的状态
  		switch (gc.getMoveState()) {
  		case LEFT:
  			gc.moveLeft(step_len);
  			if (manager.intersects(gc)) {
  				gc.moveRight(step_len);
  			}
  			
  			break;
  		case RIGHT:
  			//manager.main_game_character.moveRight(step_len);
  			gc.moveRight(step_len);
  			if (manager.intersects(gc)) {
  				gc.moveLeft(step_len);
  			}
  			
  			break;
  		case UP:
  			gc.moveUp(step_len);
  			if (manager.intersects(gc)) {
  				gc.moveDown(step_len);
  			}
  			
  			break;
  		case DOWN:
  			gc.moveDown(step_len);
  			if (manager.intersects(gc)) {
  				gc.moveUp(step_len);
  			}
  			
  			break;
  		case STOP:case DIED:case DISAPPEAR:
  			//do nothing
  			break;
  		}
  	}
    
  	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			
			if (!(selector_y - 1 < 0)) {
				selector_y--;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.UP);
			break;
		case Input.KEY_LEFT:
			
			if (!(selector_x - 1 < 0)) {
				selector_x--;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.LEFT);
			break;
		case Input.KEY_DOWN:
			
			if (!(selector_y + 1 > World.BLOCKS_HEIGHT - 2)) {
				selector_y++;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.DOWN);
			break;
		case Input.KEY_RIGHT:
			
			//manager.main_game_character.resetState();
			if (!(selector_x + 1 > World.BLOCKS_WIDTH - 2)) {
				selector_x++;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.RIGHT);
			break;
		case Input.KEY_R:
			manager.load();
			break;
		case Input.KEY_S:
			manager.save();
			break;
		case Input.KEY_B:
			game_state = 1;
			battle = new Battle(0);
			battle.init(manager.main_game_character, manager.leader[0], 500, 500, manager.getCX(), manager.getCY());
			
			break;
		case Input.KEY_P:
			music.stop();
			break;
		case Input.KEY_0:
			music.loop();
			break;
		}
		
	}
}


