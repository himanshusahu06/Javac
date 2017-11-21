package designpattern.singleton;

/**
 * Thread safe implementation of Eager instantiated singleton class This class
 * is thread safe because class loader will load one instance per Class
 * 
 * @author hsahu
 *
 */
public class ThreadSafeEagerSingleton {

	private static ThreadSafeEagerSingleton threadSafeEagerSingleton = new ThreadSafeEagerSingleton();

	private ThreadSafeEagerSingleton() {
		// disable default constuctor
	}

	public static ThreadSafeEagerSingleton getInstance() {
		return threadSafeEagerSingleton;
	}
}
