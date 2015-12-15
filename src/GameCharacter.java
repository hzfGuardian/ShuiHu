
import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class GameCharacter {
	
	//碰撞检测用的hitbox
	private Rectangle hitbox = new Rectangle();
	
	//角色类型
	private CharacterType type;
	
	//运动状态
	private MoveState isMoving = MoveState.STOP;
	
	//处于第几个场景中
	private int i, j;
	
	//处于第x，y个位置处
	private float x, y;
	
	//当前这个角色的目的地
	private int rand_steps = 0;
	
	private Texture texture = null;
	
	
	private byte state = 2;
	
	public GameCharacter(CharacterType type, int i, int j, float x, float y) {
		this.type = type;
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;
		
		//随机设定移动步数
		rand_steps = World.RAND.nextInt(10);
		
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", type.right_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLeft() {
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", type.left_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRight() {
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", type.right_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	//设置人物的运动状态
	public void setMoveState(MoveState ms) {
		this.isMoving = ms;
	}
	
	public void bind() {
		texture.bind();
	}
	
	public void draw() {
		
		if (isMoving == MoveState.LEFT) {
			setLeft();
		}
		if (isMoving == MoveState.RIGHT) {
			setRight();
		}
		
		texture.bind();
		
		glLoadIdentity();
		
		glTranslated(x, y, 0);
		
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(World.CHARACTER_SIZE, 0);
			glTexCoord2f(1, 1);
			glVertex2f(World.CHARACTER_SIZE, World.CHARACTER_SIZE);
			glTexCoord2f(0, 1);
			glVertex2f(0, World.CHARACTER_SIZE);
		glEnd();
		
		glLoadIdentity();
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
				x = World.SCREEN_WIDTH - World.CHARACTER_SIZE - 1;
				i--;
			}
		}
		else {
			x -= step_len;
		}
	}
	
	public void moveRight(int step_len) {
		if (x + step_len + World.CHARACTER_SIZE > World.SCREEN_WIDTH - 1) {
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
		if (y - step_len < 0) {
			if (j > 0) {
				y = World.SCREEN_HEIGHT - World.CHARACTER_SIZE - 1;
				j--;
			}
			
		}
		else {
			y -= step_len;
		}
			
	}
	
	public void moveDown(int step_len) {
		if (y + step_len + World.CHARACTER_SIZE > World.SCREEN_HEIGHT - 1) {
			if (j + 1 < World.SCENE_HEIGHT) {
				y = 0;
				j++;
			}
		}
		else {
			y += step_len;
		}
	}
	
	public void resetState() {
		state = 0;
	}
	
	
	public void moveDoubleLeft(int step_len) {
		if (state == 0) {
			moveLeft(step_len / 2);
			moveUp(step_len / 2);
			state = 1;
		}
		else if (state == 1) {
			moveLeft(step_len / 2);
			moveUp(step_len / 2);
			state = 2;
		}
		else if (state == 2) {
			moveLeft(step_len / 2);
			moveDown(step_len / 2);
			state = 3;
		}
		else if (state == 3) {
			moveLeft(step_len / 2);
			moveDown(step_len / 2);
			state = 0;
		}
	}
	
	public void moveDoubleRight(int step_len) {

		if (state == 0) {
			moveRight(step_len / 2);
			moveUp(step_len / 2);
			//System.out.println("state0" + x + " " + y); 
			state = 1;
		}
		else if (state == 1) {
			moveRight(step_len / 2);
			moveUp(step_len / 2);
			
			//System.out.println("state1" + x + " " + y); 
			state = 2;
		}
		else if (state == 2) {
			moveRight(step_len / 2);
			moveDown(step_len / 2);
			state = 3;
		}
		else if (state == 3) {
			moveRight(step_len / 2);
			moveDown(step_len / 2);
			state = 0;
		}
	}
	
	public static enum MoveState {
		LEFT, RIGHT, UP, DOWN, STOP;
	}
	
	//检测人与人的碰撞
	public boolean intersect(GameCharacter that) {
		//不在同一场景内肯定不会碰撞
		if (this.i != that.i || this.j != that.j) {
			return false;
		}
		
		//继承Entity类的碰撞检测器
		hitbox.setBounds((int)this.x, (int)this.y, World.CHARACTER_SIZE, World.CHARACTER_SIZE);
		
		return hitbox.intersects((int)that.x, (int)that.y, World.CHARACTER_SIZE, World.CHARACTER_SIZE);
	}
	
}
