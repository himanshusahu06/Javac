package multithreading;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Multi item Consumer Producer Factory
 *
 * 1. producer has acquired the lock, producer is producing the items until buffer is full
 * 2. once buffer is full, producer is going to signal consumer thread, will go to waiting state and release the lock
 * 3. since producer has released the lock and consumer is trying to acquire the lock, it will get the lock ownership
 * 4. now producer is sleeping and consumer is consuming the product
 * 5. when consumer has finished consuming, it will signal the producer thread, will go to waiting state and release the lock
 * 6. REPEAT STEP #1
 * 7. this is bounded buffer where consumer and producer will take turn to produce and
 *    consume the product which is not efficient because once consumer has consumed the product,
 *    producer has capability to produce it but it is not producing until consumer is asking it to produce.
 */
class ProductFactory {

  private List<Integer> productList;
  private int capacity = 5;
  private int item = 0;
  private Lock lock;
  private Condition condition;

  ProductFactory() {
    productList = new ArrayList<Integer>();
    lock = new ReentrantLock();
    condition = lock.newCondition();
  }

  ProductFactory(int capacity) {
    this();
    this.capacity = capacity;
  }

  void produce() throws InterruptedException {
    while (true) {
      System.out.println("Producer signaled by other thread.");
      lock.lock();
      while (productList.size() != capacity) {
        System.out.println("Produced: " + item);
        productList.add(item);
        ++item;
        Thread.sleep(800);  // wait for some time so that output can be little bit nicer
      }
      System.out.println("Waiting for consumer to consume the item.");
      condition.signal();   // awake the consumer thread
      System.out.println("Producer switched to waiting mode.");
      condition.await();    // go to waiting state
      lock.unlock();        // release the lock so that consumer can start consuming
    }
  }

  void consume() throws InterruptedException {
    while (true) {
      System.out.println("Consumer signaled by other thread.");
      lock.lock();
      while (productList.size() != 0) {
        System.out.println("Consumed: " + item);
        productList.remove(--item);
        Thread.sleep(800);  // wait for some time so that output can be little bit nicer
      }
      System.out.println("Waiting for producer to produce the item.");
      condition.signal(); // signal the producer to produce the item
      System.out.println("Consumer switched to waiting mode.");
      condition.await();  // go to wait mode and can be awakened by producer thread
      lock.unlock();      // release the lock so that producer can acquire the lock
    }
  }
}

/**
 * Producer consumer problem with ReentrantLock
 */
public class ProducerConsumerReentrantLock {
  public static void main(String[] args) {
    final ProductFactory productFactory = new ProductFactory();
    Thread prodThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          productFactory.produce();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    Thread consThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(10000);  // start consumer after producer
          productFactory.consume();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    prodThread.start();
    consThread.start();

    try {
      prodThread.join();
      consThread.join();
    } catch (final InterruptedException ie) {
      ie.printStackTrace();
    }
  }
}
