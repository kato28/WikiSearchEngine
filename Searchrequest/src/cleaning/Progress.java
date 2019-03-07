package cleaning;

public class Progress extends Thread {
	
	private Counter num;

	public Progress(Counter num) {
		this.num=num;
	}
	
	public void run() {
		while(!num.isStop()) {
			System.out.println(" progress : "+num.getID());
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

		}
	}

}
