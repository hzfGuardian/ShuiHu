
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MyButton {
	
	/**按钮的位置和宽高*/ 
	protected int posX, posY, width, height;
	protected Image[] current = new Image[3];
	protected String key;
	
	protected boolean isClicked = false;
	
	public MyButton(int posX, int posY, String key) {
		this.posX = posX;
		this.posY = posY;
		this.key = key;
		
		try {
			current[1] = new Image("res/" + key + ".png");
			current[2] = new Image("res/" + key + "_lux.png");
			current[0] = current[1];
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		this.width = current[0].getWidth();
		this.height = current[0].getHeight();
	}
	
	//draw the button
	public void draw() {
		current[0].draw(posX, posY);
	}
	
	public int onClicked() {
		return 0;
	}
	
}

