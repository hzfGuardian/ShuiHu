import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.lwjgl.input.Mouse;

public class StartUp {
	
	private Animation animation;
	
	private Image[] images = new Image[7];
	
	private Image background;
	
	private ChooseButton[] chooseBtn = new ChooseButton[4];
	
	public StartUp() throws SlickException {
		for (int i = 0; i < 7; i++) {
			images[i] = new Image("res/head_" + (i+1) + ".png");
		}
		
		animation = new Animation(images, 100);
		animation.setLooping(false);
		
		background = new Image("res/select.png");
		for (int i = 0; i < 4; i++) {
			chooseBtn[i] = new ChooseButton(i << 8, 150, "ch"+(i+1));
		}
	}
	
	public void play() {
		animation.draw();
		if (animation.isStopped()) {
			background.draw();
			//每个人(576-150)*(1024/4)=426*256
			for (int i = 0; i < 4; i++) {
				chooseBtn[i].draw();
			}
		}
	}
	
	public int input() {
		int id = 0;
		for (int i = 0; i < 4; i++) {
			id += chooseBtn[i].onClicked();
		}
		return id;
	}
	
	private static class ChooseButton extends MyButton {
		
		private int cur = 0;
		
		public ChooseButton(int posX, int posY, String key) {
			super(posX, posY, key);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public int onClicked() {
			
			//松开=没被按下+上一时刻刚好被按下
			if (Mouse.isButtonDown(0)) {
	  			int mousex = Mouse.getX();
	  			int mousey = World.SCREEN_HEIGHT - Mouse.getY() - 1;
	  			
	  			if (mousex >= posX && mousex <= posX + width && mousey >= posY && mousey <= posY + height) {
					current[0] = current[2];
					
					if (key.equals("ch1")) {
						cur = 1;
					} else if (key.equals("ch2")) {
						cur = 2;
					} else if (key.equals("ch3")) {
						cur = 3;
					} else if (key.equals("ch4")) {
						cur = 4;
					}
					
				}
	  			else {
					current[0] = current[1];
				}
	  		}
			else {
				current[0] = current[1];
				return cur;
			}
			
			return 0;
		}
		
	}
}
