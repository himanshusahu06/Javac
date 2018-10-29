import java.util.concurrent.*;

/**
 * When we submit submit a task to the ThreadPoolExecutor then following
 * sequence of event happens:
 * <p>
 * 1. If there is any worker thread free which is free and can run this task.
 * 2. Else it will try to move this task to the workerqueue from where a worker
 * thread will pick up the task if it is free.
 * 3. If the workerQueue is also full then it will try to create a new thread if
 * possible(no of worker threads are less than maxPoolSize).
 * 4. If all the above fails then the task is sent to the handler.
 */
public class ThreadPoolExecutorExample {

  private final ThreadPoolExecutor executor;

  private ThreadPoolExecutorExample() {
    /**
     * corePoolSize - the number of threads to keep in the pool, even if they
     * are idle
     * maximumPoolSize - the maximum number of threads to allow in the pool
     * keepAliveTime - when the number of threads is greater than the core,
     * this is the maximum time that excess idle threads will wait for new
     * tasks before terminating.
     * unit - the time unit for the keepAliveTime argument
     * workQueue - the queue to use for holding tasks before they are
     * executed. This queue will hold only the Runnable tasks submitted by
     * the execute method.
     * handler - the handler to use when execution is blocked because the
     * thread bounds and queue capacities are reached.
     *
     *
     * Suppose you have a core size of 5, and a maximum size of 15. For some
     * reason your pool gets busy, and uses all 15 available threads.
     * Eventually you run out of work to do - so some of your threads become
     * idle as they finish their final task. So 10 of those threads are
     * allowed to die.
     *
     * However, to avoid them being killed off too quickly, you can specify
     * the keep-alive time. So if you specified 1 as the keepAliveTime value
     * and TimeUnit.MINUTE as the unit value, each thread would wait one
     * minute after it had finished executing a task to see if there was more
     * work to do. If it still hadn't been given any more work, it would let
     * itself complete, until there were only 5 threads in the pool - the
     * "core" of the pool.
     */
    this.executor = new ThreadPoolExecutor(
        4, 4, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5),
        new RejectHandler());
  }

  public static void main(String[] args) {

    ThreadPoolExecutorExample executorExample = new ThreadPoolExecutorExample();

    for (int i = 0; i < 20; i++) {
      executorExample.executor.execute(new WorkerClass("Thread-" + i));
    }
    executorExample.executor.shutdown();
  }
}

/**
 * New tasks submitted in method execute(java.lang.Runnable) will be rejected
 * when the Executor has been shut down or when no more threads or queue
 * slots are available because their bounds (limit of its local “memory”)
 * would be exceeded.
 */
class RejectHandler implements RejectedExecutionHandler {

  public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    System.out.println("Task " + r.toString() + " rejected from " + executor.toString());
    // you can also throw any exception here
  }
}

class WorkerClass implements Runnable {

  private final String name;

  WorkerClass(final String name) {
    this.name = name;
  }

  public void run() {
    try {
      Thread.sleep(2000);
      System.out.println(name + " - Task Finished.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "WorkerClass{name='" + name + '\'' + '}';
  }
}

/**
 * RESPONSE
 * ------------------------------------------------------------------------
 * Task WorkerClass{name='Thread-9'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-10'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-11'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-12'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-13'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-14'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-15'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-16'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-17'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-18'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Task WorkerClass{name='Thread-19'} rejected from java.util.concurrent
 * .ThreadPoolExecutor@66d3c617[Running, pool size = 4, active threads = 4,
 * queued tasks = 5, completed tasks = 0]
 * Thread-0 - Task Finished.
 * Thread-2 - Task Finished.
 * Thread-1 - Task Finished.
 * Thread-3 - Task Finished.
 * Thread-4 - Task Finished.
 * Thread-7 - Task Finished.
 * Thread-6 - Task Finished.
 * Thread-5 - Task Finished.
 * Thread-8 - Task Finished.
 **/