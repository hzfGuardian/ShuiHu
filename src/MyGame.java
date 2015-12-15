
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;

import org.newdawn.slick.*;


public class MyGame extends BasicGame {
	
	private GameContainer container;
	private Music music;
	private Animation animation = new Animation();
	private Image[] images = new Image[4];
	
	//游戏状态：0,正常行走状态。1，战斗状态
	private byte game_state = 0;
	
	private int RESOLUTION_WIDTH = World.SCREEN_WIDTH, RESOLUTION_HEIGHT = World.SCREEN_HEIGHT;
	
	private BlockType selection = BlockType.STONE;
	
	//游戏角色
	private SceneManager manager;
	
	private int pos_x = 2, pos_y = 2;
	
	//角色移动的速度
	private int step_len = 10;
	
	private int selector_x = 0, selector_y = 0;
	
	private boolean mouseEnabled = true;
	
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
		//music = new Music("res/music.ogg");
		//music.loop();
		for (int i = 0; i < 4; i++) {
			images[i] = new Image("res/five_" + (i+1) + ".png");
		}
		
		animation = new Animation(images, 100);
		//animation.addFrame(frame, duration);
    }
    
    @Override
    public void keyReleased(int key, char c) {
    	
    	manager.main_game_character.resetState();
    	//键盘松开时，主角必须停下来
    	manager.main_game_character.setMoveState(GameCharacter.MoveState.STOP);
    	
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
    }
    
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	
    	container.setTargetFrameRate(30);
    	input();
    	
    	if (game_state == 0) {
    		manager.randomMoveCharacter();
		}
    	
    	//战斗状态
    	if (game_state == 1) {
    		   		
    		for (GameCharacter gc : manager.character_list) {
				//gc.setMoveState(GameCharacter.MoveState.RIGHT);
				move(gc, World.RAND.nextInt(8));
			}
    		
    		for (GameCharacter gc : manager.other_list) {
				//gc.setMoveState(GameCharacter.MoveState.LEFT);
				move(gc, World.RAND.nextInt(8));
			}
    		
    		for (GameCharacter gc : manager.character_list)
    			for (GameCharacter oc : manager.other_list)
    				if (gc.intersect(oc)) {
						gc.setMoveState(GameCharacter.MoveState.STOP);
						oc.setMoveState(GameCharacter.MoveState.STOP);
					}
		} 
    	
    	//主角移动
  		move(manager.main_game_character, step_len);
  		  		
    }
    
    
    @Override
    public void render(GameContainer container, Graphics graphics) throws SlickException {
       	
       	glClear(GL_COLOR_BUFFER_BIT);
		
		//再画角色
		manager.draw();
		//System.out.println("in main thread");
		int i, j, x, y;
		i = manager.getCX();j = manager.getCY();
		x = (int)manager.main_game_character.getX();y = (int)manager.main_game_character.getY();
		graphics.drawString("(" + i + ", " + j + ")" + "(" + x + ", " + y + ")", 10, 40);
		
		if (manager.intersect()) {
			graphics.drawString("Hit", manager.main_game_character.getX(), manager.main_game_character.getY() - 15);
		}
		else {
			graphics.drawString("Lu Zhishen", manager.main_game_character.getX(), manager.main_game_character.getY() - 15);
		}
		
		//animation.draw(pos_x, pos_y);
		images[0].draw(pos_x, pos_y);
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new MyGame("水浒英雄"));
        app.setDisplayMode(1024, 576, false);
        app.start();
    }
    
  //捕捉用户的输入，鼠标或键盘，鼠标按键获取格子被点击的效果，而键盘获取要点击下去绘制的效果
  	private void input() {
  		
  		if (mouseEnabled || Mouse.isButtonDown(0)) {
  			mouseEnabled = true;
  			int mousex = Mouse.getX();
  			int mousey = World.SCREEN_HEIGHT - Mouse.getY() - 1;
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
  			gc.moveDoubleLeft(step_len);
  			if (manager.intersects(gc)) {
  				gc.moveDoubleRight(step_len);
  			}
  			
  			break;
  		case RIGHT:
  			//manager.main_game_character.moveRight(step_len);
  			gc.moveDoubleRight(step_len);
  			if (manager.intersects(gc)) {
  				gc.moveDoubleLeft(step_len);
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
  		case STOP:
  			//do nothing
  			break;
  		}
  	}
    
  	
	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			pos_y-=5;
			
			mouseEnabled = false;
			if (!(selector_y - 1 < 0)) {
				selector_y--;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.UP);
			break;
		case Input.KEY_LEFT:
			pos_x-=5;
			
			mouseEnabled = false;
			if (!(selector_x - 1 < 0)) {
				selector_x--;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.LEFT);
			break;
		case Input.KEY_DOWN:
			pos_y+=5;
			
			mouseEnabled = false;
			if (!(selector_y + 1 > World.BLOCKS_HEIGHT - 2)) {
				selector_y++;
			}
			
			manager.main_game_character.setMoveState(GameCharacter.MoveState.DOWN);
			break;
		case Input.KEY_RIGHT:
			pos_x+=5;
			
			mouseEnabled = false;
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
			int i = manager.getCX();
    		int j = manager.getCY();
    		float x = 10;
    		float y = 200;
    		//(10,205)
    		for (int k = 0; k < 10; k++) {
    			manager.character_list.add(new GameCharacter(CharacterType.SOLDER_HU, i, j, x, y + k * 100));
    			manager.other_list.add(new GameCharacter(CharacterType.SOLDIER_SONG, i, j, World.SCREEN_WIDTH - 200,
    					y + k * 100));
			}
    		
    		for (GameCharacter gc : manager.character_list) {
				gc.setMoveState(GameCharacter.MoveState.RIGHT);
			}
    		
    		for (GameCharacter gc : manager.other_list) {
				gc.setMoveState(GameCharacter.MoveState.LEFT);
			}
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

