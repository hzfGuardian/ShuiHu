

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public enum BlockType {
	STONE("res/stone.png"), AIR("res/air.png"), GRASS("res/grass.png"), DIRT("res/road.png");
	
	public final String location;
	
	public final FileInputStream file;
	
	//constructor
	BlockType(String location) {
		this.location = location;
		file = getFile();
	}
	
	public FileInputStream getFile() {
		//file
		try {
			return new FileInputStream(new File(location));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}


