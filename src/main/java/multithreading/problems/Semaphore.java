package multithreading;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore:- only keep track only how many resources are free.
 *             It doesn't keep track of which resources are free.
 *             acquire() - take a resource/permit
 *             release() - release a resource/permit
 *             when count/permit becomes zero, new thread requesting for resource
 *             have to wait until one resource is relinquished.
 *
 * Mutex :- Binary Semaphore (semaphore with just one permit)
 *          Only the process that locked the mutex can unlock it.
 */
public class SemaphoreAbstractDataType {
  public static void main(String[] args) {
    final Downloader downloader = Downloader.getInstance();
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0 ; i < 12; i++) {
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          downloader.download();
        }
      });
    }
    executorService.shutdown();
  }
}


class Downloader {
  private static Downloader downloader = new Downloader();
  public static Downloader getInstance() {
    return downloader;
  }
  private Downloader() {
    super();
  }

  private Semaphore semaphore = new Semaphore(5, true);
  private Random random = new Random();
  private int max_waiting_time = 3000;
  private int min_waiting_time = 1000;

  public void download() {
    try {
      semaphore.acquire();
      downloadFile();
    } catch (final InterruptedException ie) {
      ie.printStackTrace();
    } finally {
      semaphore.release();
    }
  }
  private void downloadFile() throws InterruptedException {
    final int sleepTime = random.nextInt(max_waiting_time - min_waiting_time + 1) + min_waiting_time;
    System.out.println("Downloading file from internet. It will take " + sleepTime + "ms to download.");
    Thread.sleep(sleepTime);
  }
}
