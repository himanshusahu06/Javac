package designpattern.singleton;

/**
 * Thread safe implementation of Lazy instantiated singleton class
 * 
 * @author hsahu
 *
 */
public class ThreadSafeLazySingleton {

	private static ThreadSafeLazySingleton threadSafeLazySingleton;

	private ThreadSafeLazySingleton() {
		// disable default constuctor
	}

	public static ThreadSafeLazySingleton getInstance() {
		if (threadSafeLazySingleton == null) {
			synchronized (ThreadSafeLazySingleton.class) {
				threadSafeLazySingleton = new ThreadSafeLazySingleton();
			}
		}
		return threadSafeLazySingleton;
	}
}
