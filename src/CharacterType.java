import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public enum CharacterType {
	LU("res/l_lu.png", "res/r_lu.png"), 
	SONG("res/song.png", "res/song.png"), 
	LIN("res/l_lin.png", "res/r_lin.png"), 
	HU("res/l_niang.png", "res/r_niang.png"),
	SOLDIER_LU("res/l_lu_sold.png", "res/r_lu_sold.png"),
	SOLDIER_SONG("res/l_song_sold.png", "res/r_song_sold.png"),
	SOLDER_LIN("res/l_lin_sold.png", "res/l_lin_sold.png"),
	SOLDER_HU("res/l_hu_sold.png", "res/r_hu_sold.png");
	
	
	public final String l_location, r_location;
	
	public final FileInputStream left_file, right_file;
	
	CharacterType(String l_location, String r_location) {
		
		this.l_location = l_location;
		this.r_location = r_location;
		left_file = getLeftFile();
		right_file = getRightFile();
	}
	
	public FileInputStream getLeftFile() {
		//file
		try {
			return new FileInputStream(new File(l_location));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public FileInputStream getRightFile() {
		//file
		try {
			return new FileInputStream(new File(r_location));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
