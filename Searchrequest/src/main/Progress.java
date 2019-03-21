package main;

public class Progress extends Thread {
	private static final int MINUTTOWAIT = 2;

	private Counter num;

	public Progress(Counter num) {
		this.num=num;
	}
	
	public void run() {
		while(!num.isStop()) {
			System.out.println(" progress : "+num.getID());
			try {
				Thread.sleep(1000*60*MINUTTOWAIT);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

		}
	}

}
