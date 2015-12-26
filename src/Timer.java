

public class Timer {
	
	private long start, end;
    
    //
    public void start() {
    	start = System.currentTimeMillis();
	}
    
    public void end() {
		end = System.currentTimeMillis();
	}
    
    public int getTime() {
		return (int)(end - start);
	}
    
}
