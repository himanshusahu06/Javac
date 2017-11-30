package multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker2 {

	public Lock lock = new ReentrantLock();
	public Condition ourCondition = lock.newCondition();

	public void producer() throws InterruptedException {
		// Acquires the lock
		lock.lock();
		System.out.println("Producer Producing..");
		/**
		 * Causes the current thread to wait until it is signalled The lock associated
		 * with condition is atomically released and the current thread becomes
		 * disabled.
		 */
		ourCondition.await();
		System.out.println("Producer Produced..");
		// Releases the lock
		lock.unlock();
	}

	public void consumer() throws InterruptedException {
		// Acquires the lock
		lock.lock();
		Thread.sleep(1000);
		System.out.println("Consumer Consumed..");
		/**
		 * Wakes up one waiting thread. If any threads are waiting on this condition
		 * then they are all woken up. Each thread must re-acquire the lock.
		 */
		ourCondition.signal();
		// Releases the lock
		lock.unlock();
	}
}

public class ReentrantLocking {
	public static void main(String[] args) {
		Worker2 worker2 = new Worker2();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					worker2.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					worker2.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}
}
