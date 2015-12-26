import org.lwjgl.input.Mouse;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ChooseBattle {
	
	private Image weapon_bar = null;
	
	public ChooseBattle() {
		// TODO Auto-generated constructor stub
		try {
			weapon_bar = new Image("res/weapon_list.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private BattleButton[] weapon = { 
		new BattleButton(300, World.SCREEN_HEIGHT - 105, "weapon0"), 
		new BattleButton(400, World.SCREEN_HEIGHT - 105, "weapon1"), 
		new BattleButton(500, World.SCREEN_HEIGHT - 105, "weapon2")
	};
	
	public int input() {
		int id = 0;
		for (int i = 0; i < 3; i++) {
			id += weapon[i].onClicked();
		}
		return id;
	}
	
	private static class BattleButton extends MyButton {
		
		public int cur = 0;
		
		public BattleButton(int posX, int posY, String key) {
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
					
					if (key.equals("weapon0")) {
						cur = 1;
					} else if (key.equals("weapon1")) {
						cur = 2;
					} else if (key.equals("weapon2")) {
						cur = 3;
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
	
	public void draw() {
		
		weapon_bar.draw(0, World.SCREEN_HEIGHT-weapon_bar.getHeight());
		
		for (BattleButton battleButton : weapon) {
			battleButton.draw();
		}
	}
}
