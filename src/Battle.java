import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//one battle one class

public class Battle {
	
	//战斗模式
	private int battle_mod = 0;
	
	int i, j;
	//进攻方，防守方
	public MainCharacter attacker, offenser;
	
	//进攻队列，防守队列
	public List<GameCharacter> attack_list = new ArrayList<GameCharacter>(), offense_list = new ArrayList<GameCharacter>();
		
	private Timer timer = new Timer();
	
	private Animation animation = new Animation();
	Image[] images = new Image[4];
	
	//constructor
	public Battle(int battle_mod) {
		this.battle_mod = battle_mod;
	}
		
	//用兵攻城
	public void init(MainCharacter gc_att, MainCharacter gc_off, int num_a, int num_o, int i, int j) {
		
		//进攻方和防守方主将的初始化
		attacker = gc_att;
		offenser = gc_off;
		
		this.i = i;
		this.j = j;
		
		//战斗模式--0, 代表普通出兵
		if (battle_mod == 0) {
    		float x = 10;
    		float y = 110;
    		//(10,205)
    		for (int k = 0; k < num_a; k++) {
    			//y = World.RAND.nextInt(World.SCREEN_HEIGHT);
    			attack_list.add(new GameCharacter(CharacterType.SOLDIER_LU, i, j, x, y + World.RAND.nextInt(350)));
    		}
    		
    		for (int k = 0; k < num_o; k++) {
    			offense_list.add(new GameCharacter(CharacterType.SOLDIER_SONG, i, j, World.SCREEN_WIDTH, y + World.RAND.nextInt(350)));
			}
    		
    		for (GameCharacter gc : attack_list) {
				gc.setMoveState(GameCharacter.MoveState.RIGHT);
			}
    		
    		for (GameCharacter gc : offense_list) {
				gc.setMoveState(GameCharacter.MoveState.LEFT);
			}
		}
		else {
			
			for (int k = 0; k < 4; k++) {
				try {
					if (battle_mod == 1) {
						images[k] = new Image("res/five_" + (k+1) + ".png");
					}
					else if (battle_mod == 2) {
						images[k] = new Image("res/pao_" + (k+1) + ".png");
					} 
					else if (battle_mod == 3) {
						images[k] = new Image("res/gold_bull_" + (k+1) + ".png");
					}
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			animation = new Animation(images, 50);
			//System.out.println("animation");
			
    		float y = 110;
    		//(10,205)
    		for (int k = 0; k < num_a; k++) {
    			//y = World.RAND.nextInt(World.SCREEN_HEIGHT);
    			attack_list.add(new GameCharacter(CharacterType.EIGHT_GUA, i, j, 0, 150));
    		}
    		
    		for (int k = 0; k < num_o; k++) {
    			offense_list.add(new GameCharacter(CharacterType.SOLDIER_SONG, i, j, World.SCREEN_WIDTH, y + World.RAND.nextInt(350)));
			}
    		
    		for (GameCharacter gc : attack_list) {
				gc.setMoveState(GameCharacter.MoveState.RIGHT);
			}
    		
    		for (GameCharacter gc : offense_list) {
				gc.setMoveState(GameCharacter.MoveState.LEFT);
			}
		}
		
		timer.start();
		
	}
	
	//检查本次战斗是否结束
	public int checkEnd() {
		timer.end();
		if (timer.getTime() >= 10000) {
			if (battle_mod != 0) {
				return 1;
			}
			if (attackRest() > offenseRest()) {
				return 1;
			}
			else if (attackRest() < offenseRest()) {
				return 2;
			}
			else {
				return 3;
			}
		}
		//未结束
		return 0;
	}
	
	public int attackRest() {
		int num = 0;
		for (GameCharacter gc : attack_list) {
			if (gc.isAlive()) {
				num++;
			}
		}
		return num;
	}
	
	public int offenseRest() {
		int num = 0;
		for (GameCharacter gc : offense_list) {
			if (gc.isAlive()) {
				num++;
			}
		}
		return num;
	}
	
	//战斗场面的逻辑实现
	public void update() {
		
		for (GameCharacter gc : attack_list) {
			
			if (gc.isAlive()) {
				gc.move(World.RAND.nextInt(10));
				
    			if ((gc.getMoveState() == GameCharacter.MoveState.LEFT && gc.getX() - 30 <= 0) 
    					|| gc.getX() + gc.getWidth() + 3 > World.SCREEN_WIDTH) {
					//gc.inverseDir();
    				//gc.setMoveState(GameCharacter.MoveState.STOP);
				}
    			
    			if (gc.getX() > 0.75 * World.SCREEN_WIDTH && gc.getMoveState() == GameCharacter.MoveState.RIGHT) {
    				gc.setMoveState(GameCharacter.MoveState.STOP);
				}
			}
		}
		
		for (GameCharacter gc : offense_list) {
			//gc.setMoveState(GameCharacter.MoveState.LEFT);
			if (gc.isAlive()) {
				gc.move(World.RAND.nextInt(10));
    			if ((gc.getMoveState() == GameCharacter.MoveState.LEFT && gc.getX() - 100 <= 0) 
    					|| gc.getX() + gc.getWidth() + 3 > World.SCREEN_WIDTH) {
					//gc.inverseDir();
    				//gc.setMoveState(GameCharacter.MoveState.STOP);
				}
    			if (gc.getX() < 0.25 * World.SCREEN_WIDTH && gc.getMoveState() == GameCharacter.MoveState.LEFT) {
    				gc.setMoveState(GameCharacter.MoveState.STOP);
				}
			}
			//move(gc, World.RAND.nextInt(8));
		}
		
		for (GameCharacter gc : attack_list)
			for (GameCharacter oc : offense_list) {
				if (gc.intersect(oc) && gc.isAlive() && oc.isAlive()) {					
					gc.resetAngle(World.RAND.nextInt(100) + 80);
					oc.resetAngle(World.RAND.nextInt(100) + 80);
					if (battle_mod == 0) {
						gc.setMoveState(GameCharacter.MoveState.DIED);
						attacker.setNum_of_soldiers(attacker.getNum_of_soldiers() - 1);
					}					
					oc.setMoveState(GameCharacter.MoveState.DIED);
					offenser.setNum_of_soldiers(offenser.getNum_of_soldiers() - 1);
				}    				
			}
	}
	
	public void draw() {
		//绘制角色
		for (GameCharacter gameCharacter : attack_list) {
			if (i == gameCharacter.getI() && j == gameCharacter.getJ()) {
				if (battle_mod == 0) {
					gameCharacter.draw();
				}
				else {
					animation.draw(gameCharacter.getX(), gameCharacter.getY());
				}
			}	
		}
		
		//绘制角色
		for (GameCharacter gameCharacter : offense_list) {
			if (i == gameCharacter.getI() && j == gameCharacter.getJ()) {
				gameCharacter.draw();
			}
		}
	}
}
