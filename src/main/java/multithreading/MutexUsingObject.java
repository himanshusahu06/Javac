package multithreading;

public class MutexUsingObject {

	private static int A = 0;
	private static int B = 0;

	/**
	 * Mutex to lock only variables instead of entire class 1 mutex per variable
	 */
	private static Object mutexA = new Object();
	private static Object mutexB = new Object();

	private static void addA() {
		synchronized (mutexA) {
			A++;
		}
	}

	private static void addB() {
		synchronized (mutexB) {
			B++;
		}
	}

	private static void compute() {
		for (int i = 0; i < 1000; i++) {
			addA();
			addB();
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

		System.out.println("Counter A : " + A);
		System.out.println("Counter B : " + B);
	}
}
