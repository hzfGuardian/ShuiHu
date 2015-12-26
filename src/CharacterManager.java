
import java.util.ArrayList;
import java.util.List;

public class CharacterManager {
	
	protected int width = World.SCREEN_WIDTH, height = World.SCREEN_HEIGHT;
	
	//主角
	protected MainCharacter main_game_character = new MainCharacter(CharacterType.LU, 0, 0, 300, 300);
	
	//另外三个主将
	public MainCharacter[] leader = {
		new MainCharacter(CharacterType.SONG, 1, 1, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2),
		new MainCharacter(CharacterType.HU, 2, 0, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2),
		new MainCharacter(CharacterType.LIN, 0, 1, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2),
	};
	
	//角色列表
	protected List<GameCharacter> character_list = new ArrayList<GameCharacter>();
	//protected List<GameCharacter> other_list = new ArrayList<GameCharacter>();
	
	
	public CharacterManager() {
		setLocations();	
	}
	
	public void init(int main_id) {
		CharacterType[] character_type = CharacterType.values();
		
		main_game_character = new MainCharacter(character_type[main_id], 0, 0, 300, 300);
		
		int j = 0;
		for (int i = 0; i < 4; i++) {
			if (main_id != i) {
				leader[j] = new MainCharacter(character_type[i], 1, 1, World.SCREEN_WIDTH / 2, World.SCREEN_HEIGHT / 2);
				++j;
			}
		}
	}
	
	//加入列表
	public void addCharacterList(GameCharacter gc) {
		character_list.add(gc);
	}
	
	private void setLocations() {
		int n = 0;
		CharacterType[] character_type = CharacterType.values();
		for (int k = 0; k < n; k++) {
			int i = World.RAND.nextInt(10), j = World.RAND.nextInt(10);
			int x = World.SCREEN_WIDTH / 2, y = World.SCREEN_HEIGHT / 2;
			character_list.add(new GameCharacter(character_type[World.RAND.nextInt(character_type.length)], i, j, x, y));
		}
		/*
		for (int k = 0; k < leader.length; k++) {
			character_list.add(leader[k]);
		}
		*/
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
