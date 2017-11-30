package multithreading;

public class Synchronized {
	private static int counter = 0;

	/**
	 * mutual exclusion on method using synchronized keyword
	 */
	private static synchronized void increment() {
		++counter;
	}

	private static void compute() {
		for (int i = 1; i <= 1000; i++) {
			increment();
		}
	}

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				compute();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				compute();
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(counter);
	}
}
