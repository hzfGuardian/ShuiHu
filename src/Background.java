

import static org.lwjgl.opengl.GL11.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Background {
	
	private Texture texture = null;
	
	public Background(FileInputStream fis) {
		
		//load texture
		try {
			this.texture = TextureLoader.getTexture("PNG", fis);
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
		
		glTranslated(0, 0, 0);
		
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			glTexCoord2f(1.0f, 0);
			glVertex2f(World.SCREEN_WIDTH, 0);
			glTexCoord2f(1.0f, 1.0f);
			glVertex2f(World.SCREEN_WIDTH, World.SCREEN_WIDTH);
			glTexCoord2f(0, 1);
			glVertex2f(0, World.SCREEN_WIDTH);
		glEnd();
		
		glLoadIdentity();
	}
	
	
}
