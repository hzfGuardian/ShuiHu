

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;


public class GameCharacter {
	
	//角色需要两个状态：运动状态和静止状态
	protected Animation animation;
	
	protected Image image;
	
	//碰撞检测用的hitbox
	protected Rectangle hitbox = new Rectangle();
	
	//角色类型
	protected CharacterType type;
	
	//运动状态
	protected MoveState isMoving = MoveState.STOP;
	
	//脸部朝向
	protected MoveState faceDir = MoveState.RIGHT;
	
	//飞出去的动画角度
	protected float angle = 0;
	protected float radius = 0;
	protected float round_x = 0, round_y = 0;
	
	//计时器计算死亡
	protected Timer timer = new Timer();
	
	//处于第几个场景中
	protected int i, j;
	
	//处于第x，y个位置处
	private float x, y;
	
	//当前这个角色的目的地
	protected int rand_steps = 0;
	
	
	public GameCharacter(CharacterType type, int i, int j, float x, float y) {
		this.type = type;
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;
		
		//随机设定移动步数
		rand_steps = World.RAND.nextInt(10);
		
		//load texture
		setRight();
	}
		
	public int getWidth() {
		
		return animation.getWidth();
	}
	
	public int getHeight() {
		return animation.getHeight();
	}
	
	
	public void setLeft() {
		//load texture
		faceDir = MoveState.LEFT;
		image = type.left_images[0];
		animation = type.left_animation;
	}
	
	
	public void setRight() {
		//load texture
		faceDir = MoveState.RIGHT;
		image = type.right_images[0];
		animation = type.right_animation;
	}
	
	//在仍在行走的过程中，将人物置于死地
	public void setDead() {
		if (faceDir == MoveState.LEFT) {
			//moveRight(20);
			image = type.left_dead[0];
		}
		else {
			//moveLeft(20);
			image = type.right_dead[0];
		}	
		
		if (angle <= 180) {
			if (faceDir == MoveState.LEFT)
				x = round_x + radius - radius * (float)Math.cos(angle);
			else 
				x = round_x - radius + radius * (float)Math.cos(angle);
			
			y = round_y - radius * (float)Math.sin(angle) - image.getHeight();
			angle += 15;
			//开启计时器
			timer.start();
		}
		else {
			timer.end();
			if (timer.getTime() >= 5000) {
				setMoveState(MoveState.DISAPPEAR);
			}
		}
	}
	
	//重置飞行角度为0,并设定飞行半径
	public void resetAngle(int radius) {
		angle = 0;
		round_x = x;
		round_y = y;
		this.radius = radius;
	}
	
	//判断当前步数是否走完
	public boolean judegeStepIsZero() {
		return rand_steps <= 0;
	}
	
	
	public void setSteps(int rs) {
		rand_steps = rs;
	}
	
	//目的步数减1
	public void subStep() {
		if (rand_steps <= 0) {
			//重新调整运动方向
			int dir = World.RAND.nextInt(4);
			switch (dir) {
			case 0:
				isMoving = MoveState.RIGHT;
				break;
			case 1:
				isMoving = MoveState.LEFT;
				break;
			case 2:
				isMoving = MoveState.UP;
				break;
			case 3:
				isMoving = MoveState.DOWN;
				break;
			default:
				break;
			}
			//重新设定移动步数
			rand_steps = World.RAND.nextInt(1000);
			
		}	
		else {
			rand_steps--;
		}
	}
	
	//获取人物的运动状态
	public MoveState getMoveState() {
		return isMoving;
	}
	
	//左右转向函数
	public void inverseDir() {
		if (isMoving == MoveState.LEFT) {
			isMoving = MoveState.RIGHT;
			setRight();
		}
		if (isMoving == MoveState.RIGHT) {
			isMoving = MoveState.LEFT;
			setLeft();
		}
	}
	
	//设置人物的运动状态
	public void setMoveState(MoveState ms) {
		this.isMoving = ms;
	}
	
	public void draw() {
		
		switch (isMoving) {
		case UP:case DOWN:
			animation.draw(x, y);//运动状态，则动画
			break;
		
		case LEFT:
			setLeft();
			animation.draw(x, y);//运动状态，则动画
			break;
		case RIGHT:
			setRight();
			animation.draw(x, y);//运动状态，则动画
			break;
		case STOP:
			image.draw(x, y);//停止状态，则画图
			break;
		case DIED:
			setDead();
			image.draw(x, y);
			break;
		case DISAPPEAR:
			break;
		}
		
	}
	
	public CharacterType getType() {
		return type;
	}
	public void setType(CharacterType type) {
		this.type = type;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public void moveLeft(int step_len) {
		if (x - step_len < 0) {
			if (i - 1 >= 0) {
				x = World.SCREEN_WIDTH - getWidth() - 1;
				i--;
			}
		}
		else {
			x -= step_len;
		}
	}
	
	public void moveRight(int step_len) {
		if (x + step_len + getWidth() > World.SCREEN_WIDTH - 1) {
			if (i + 1 < World.SCENE_WIDTH) {
				x = 0;//右边下一个场景的最左边
				i++;
			}
		}
		else {
			x += step_len;
		}
	}

	public void moveUp(int step_len) {
		if (y - step_len < World.SCREEN_HEIGHT / 2) {
			if (j > 0) {
				y = World.SCREEN_HEIGHT - getHeight() - 1;
				j--;
			}
			
		}
		else {
			y -= step_len;
		}
			
	}
	
	public void moveDown(int step_len) {
		if (y + step_len + getHeight() > World.SCREEN_HEIGHT - 1) {
			if (j + 1 < World.SCENE_HEIGHT) {
				y = World.SCREEN_HEIGHT / 2;
				j++;
			}
		}
		else {
			y += step_len;
		}
	}
	

	public static enum MoveState {
		LEFT, RIGHT, UP, DOWN, STOP, DIED, DISAPPEAR;
	}
	
	//检测人与人的碰撞
	public boolean intersect(GameCharacter that) {
		//不在同一场景内肯定不会碰撞
		if (this.i != that.i || this.j != that.j) {
			return false;
		}
		
		//继承Entity类的碰撞检测器
		hitbox.setBounds((int)this.x, (int)this.y, getWidth(), getHeight());
		
		return hitbox.intersects((int)that.x, (int)that.y, that.getWidth(), that.getHeight());
	}
	
	
	public void move(int len) {
		switch (isMoving) {
		case UP:
			moveUp(len);
			break;
		case DOWN:
			moveDown(len);
			break;
		case LEFT:
			moveLeft(len);
			break;
		case RIGHT:
			moveRight(len);
			break;
		case STOP:
			
			break;
		case DIED:
			
			break;
		case DISAPPEAR:
			break;
		}
	}
	
	public boolean isAlive() {
		return isMoving != MoveState.DIED && isMoving != MoveState.DISAPPEAR;
	}
}
