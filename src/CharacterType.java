
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import net.java.games.input.Component.Identifier.Axis;

public enum CharacterType {
	SONG("res/song.png", "res/song.png"),
	LIN("res/l_lin.png", "res/r_lin.png"), 
	HU("res/l_niang.png", "res/r_niang.png"),
	LU("res/l_lu.png", "res/r_lu.png"), 
	SOLDIER_LU("res/l_lu_sold.png", "res/r_lu_sold.png"),
	SOLDIER_SONG("res/l_song_sold.png", "res/r_song_sold.png"),
	SOLDER_LIN("res/l_lin_sold.png", "res/l_lin_sold.png"),
	SOLDER_HU("res/l_hu_sold.png", "res/r_hu_sold.png"),
	PAO("res/pao_1.png", "res/pao_1.png"),
	EIGHT_GUA("res/five_1.png", "res/five_1.png"),
	GOLD_BULL("res/gold_bull.png", "res/gold_bull.png");
	
	//角色的动画frame images
	public final Image[] left_images = new Image[2];
	
	public final Image[] right_images = new Image[2];
	
	public final Image[] left_dead = new Image[1];
	public final Image[] right_dead = new Image[1];
	//
	public final Animation left_animation, right_animation;
	
	//make the dead animation for dead char
	
	CharacterType(String left, String right) {
		
		try {
			left_images[0] = new Image(left);
			left_images[1] = new Image(left);
			left_images[1].rotate(10);//逆时针为正，顺时针为负
			
			left_dead[0] = new Image(left);			
			left_dead[0].rotate(90);
			
			right_images[0] = new Image(right);
			right_images[1] = new Image(right);
			right_images[1].rotate(-10);
			right_dead[0] = new Image(right);
			right_dead[0].rotate(-90);
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		left_animation = new Animation(left_images, 10);
		right_animation = new Animation(right_images, 10);
	}

}
