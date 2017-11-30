package multithreading;

class WorkerA implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner A : " + i);
		}
	}
}

class WorkerB implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner B : " + i);
		}
	}
}

public class SimpleThreading {
	public static void main(String[] args) {
		Thread threadA = new Thread(new WorkerA());
		Thread threadB = new Thread(new WorkerB());
		threadA.start();
		threadB.start();

		try {
			/**
			 * Waits for this thread to die.
			 */
			threadA.join();
			threadB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Race complete..");
	}
}
