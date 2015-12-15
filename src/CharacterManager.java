
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterManager {
	
	protected int width = World.SCREEN_WIDTH, height = World.SCREEN_HEIGHT;
	
	//主角
	protected GameCharacter main_game_character = new MainCharacter(CharacterType.LU, 0, 0, 300, 300);
	
	//另外三个主将
	protected GameCharacter[] leader = {
		new MainCharacter(CharacterType.SONG, 0, 0, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2),
		new MainCharacter(CharacterType.HU, 0, 0, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2),
		new MainCharacter(CharacterType.LIN, 0, 0, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2),
	};
	
	//角色列表
	protected List<GameCharacter> character_list = new ArrayList<GameCharacter>();
	protected List<GameCharacter> other_list = new ArrayList<GameCharacter>();
	
	//随机生成角色位置
	Random random = new Random();
	//protected GameCharacter[][] characters = new GameCharacter[width][height];
	
	public CharacterManager() {
		
		setLocations();
		
	}
	
	//加入列表
	public void addCharacterList(GameCharacter gc) {
		character_list.add(gc);
	}
	
	private void setLocations() {
		int n = 100;
		for (int k = 0; k < n; k++) {
			int i = random.nextInt(10), j = random.nextInt(10);
			int x = World.SCREEN_WIDTH / 2, y = World.SCREEN_HEIGHT / 2;
			character_list.add(new GameCharacter(CharacterType.SOLDIER_LU, i, j, x, y));
		}
		for (int k = 0; k < leader.length; k++) {
			character_list.add(leader[k]);
		}
	}
	
	//检测主角是否与其他角色碰撞
	public boolean intersect() {
		for (GameCharacter gameCharacter : character_list) {
			if (main_game_character.intersect(gameCharacter)) {
				return true;
			}
		}
		return false;
	}
	
}
