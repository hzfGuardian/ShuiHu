import java.util.ArrayList;
import java.util.List;

//主将:需要维护的内容有金钱，兵力，武器，阵法

public class MainCharacter extends GameCharacter {
	
	//初始化金钱为1000
	private int money = 1000;
	
	//初始有500兵力
	private int num_of_soldiers = 500;
	
	//武器列表
	private List<Weapon> weapon = new ArrayList<Weapon>();

	//拥有城池列表,只有主将能拥有城池
	private List<Areas> city_list = new ArrayList<Areas>();
	
	//主将当前拥有的士兵列表(进攻时刻的士兵列表,记录士兵的位置便于碰撞检测)
	private List<SoldierCharacter> soldier_list = new ArrayList<SoldierCharacter>();
	
	//constructor for main character
	public MainCharacter(CharacterType type, int i, int j, float x, float y) {
		super(type, i, j, x, y);
		
	}
	
	
	////////////////////////////////////////////////////////get and set
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getNum_of_soldiers() {
		return num_of_soldiers;
	}
	
	public void setNum_of_soldiers(int num_of_soldiers) {
		this.num_of_soldiers = num_of_soldiers;
	}
	
	public void addSoldier(SoldierCharacter sc) {
		soldier_list.add(sc);
	}
	
	public void clearSoldier() {
		soldier_list.clear();
	}
	//////////////////////////////////////////////////////////////////////////////
	
	
}
