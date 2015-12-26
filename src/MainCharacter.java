import java.util.ArrayList;
import java.util.List;

//主将:需要维护的内容有金钱，兵力，武器，阵法

public class MainCharacter extends GameCharacter {
	
	//初始化金钱为1000
	private int money = 0;
	
	//初始有500兵力
	private int num_of_soldiers = 5000;
	
	//武器列表
	List<Weapon> weapon = new ArrayList<Weapon>();

	//拥有城池列表,只有主将能拥有城池
	List<Areas> city_list = new ArrayList<Areas>();
	
	public boolean choose_state = false;
	
	public int battle_mod = 0;
	
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
	
	public void addMoney(int dm) {
		money += dm;
	}
	
	public void addSoldiers(int ds) {
		num_of_soldiers += ds;
	}
	
	//////////////////////////////////////////////////////////////////////////////
	
	//用士兵攻城
	public void attack(int num) {
		
	}
	
	//用武器攻城
	public void attack(Weapon weapon) {
		
	}
	
	
	
}
