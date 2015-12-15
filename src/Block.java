

import static org.lwjgl.opengl.GL11.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;



public class Block {
	private BlockType type = BlockType.AIR;
	private float x;
	private float y;
		
	
	private Texture texture = null;
	
	public Block(BlockType type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
		
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", type.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bind() {
		texture.bind();
	}
	
	public void draw() {
		texture.bind();
		
		glLoadIdentity();
		
		glTranslated(x, y, 0);
		
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1, 0);
			glVertex2f(World.BLOCK_SIZE, 0);
			glTexCoord2f(1, 1);
			glVertex2f(World.BLOCK_SIZE, World.BLOCK_SIZE);
			glTexCoord2f(0, 1);
			glVertex2f(0, World.BLOCK_SIZE);
		glEnd();
		
		glLoadIdentity();
	}
	
	public BlockType getType() {
		return type;
	}
	public void setType(BlockType type) {
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
	
}
