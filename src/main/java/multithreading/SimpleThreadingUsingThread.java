package multithreading;

class RunnerA extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner A : " + i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class RunnerB extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner B : " + i);
			try {
				Thread.sleep(23);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class SimpleThreadingUsingThread {
	public static void main(String[] args) {
		new RunnerA().start();
		new RunnerB().start();
	}
}
