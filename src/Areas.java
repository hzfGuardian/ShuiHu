

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//区域，所有场景的base class

//包括普通城池，商店，图书馆，金银岛，乱葬岗

public class Areas extends BlockGrid {
	
	//场景类型
	protected SceneryType scenery_type;
	
	//场景名称
	protected String name;
	
	protected Background background = null;
	
	protected MainCharacter owner = null;
	
	public Areas(SceneryType s, int key) {
		super();
		scenery_type = s;
		try {
			background = new Background(new FileInputStream(new File("res/bg_" + key + ".png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw() {
		//画背景
		//super.draw();
		background.draw();
	}
	
	public static enum SceneryType {
		SIMPLE, CITY, SHOP, SOLDIER, ISLAND
	}

	public GameCharacter getOwner() {
		return owner;
	}

	public void setOwner(MainCharacter owner) {
		this.owner = owner;
	}
	
	//未归属
	public boolean isEmpty() {
		return owner == null;
	}
	
}
