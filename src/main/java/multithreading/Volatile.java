package multithreading;

/**
 * Declaring a volatile Java variable means: The value of this variable will
 * never be cached thread-locally: all reads and writes will go straight to
 * "main memory"; Access to the variable acts as as synchronized variable.
 * 
 * @author hsahu
 *
 */
class Worker implements Runnable {

	private volatile boolean isTerminated = false;

	@Override
	public void run() {
		while (!isTerminated) {
			System.out.println("Worker is running...");
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isTerminated() {
		return isTerminated;
	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}
}

public class Volatile {
	public static void main(String[] args) {

		Worker worker = new Worker();
		Thread t1 = new Thread(worker);
		t1.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		worker.setTerminated(true);
		System.out.println("finished....");
	}
}
