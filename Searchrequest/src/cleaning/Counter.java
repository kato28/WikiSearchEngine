package cleaning;

public class Counter {

	private static Integer id =0;
	private boolean stop;
	
	public void increment() {
		id++;
	}
	public Counter(boolean b) {
		stop = b;
	}
	public Counter(boolean b, int n) {
		stop = b;
		id = n;
	}
	public void decrement() {
		id--;
	}
	public boolean isStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public Integer getID() {return id;}
}
