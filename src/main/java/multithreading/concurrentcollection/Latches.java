package multithreading.concurrentcollection;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Latches {
	/**
	 * A synchronization object that allows one or more threads to wait until a set
	 * of operations being performed in other threads completes.
	 * 
	 * This is used to synchronize one or more tasks by forcing them to wait for the
	 * completion of a set of operations being performed by other tasks
	 * 
	 * You given an initial count to CountDownLatch object and any task that calls
	 * wait() on that object will block until the count reaches zero
	 * 
	 * CountDownLatch can not be reset !!!
	 * 
	 * Example: You want to trigger something after 100 users download an image.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		ExecutorService executorService = Executors.newFixedThreadPool(2);
		CountDownLatch countDownLatch = new CountDownLatch(5);

		for (int i = 0; i < 19; i++) {
			executorService.execute(new Worker(i + 1, countDownLatch));
		}
		try {
			// Causes the current thread to wait until the latch has counted down to zero
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("All prerequisites are done...");
		System.out.println("Current countdown for latch: " + countDownLatch.getCount());
		executorService.shutdown();
	}
}

class Worker implements Runnable {

	private int id;
	private CountDownLatch countDownLatch;

	public Worker(int id, CountDownLatch countDownLatch) {
		this.id = id;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		doWork();
		countDownLatch.countDown();
	}

	public void doWork() {
		System.out.println("Thread with id: " + this.id + " starts working...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}