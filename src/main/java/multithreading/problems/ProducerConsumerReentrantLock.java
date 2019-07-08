package multithreading;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Consumer Producer Factory
 *
 * 1. one of producer has acquired the lock, producer is producing the items and as soon as item is available,
      it signals all the consumer threads waiting on the same lock.
 * 2. once buffer is full, producer will go to waiting state.
 * 3. lock will be released after producing the item, so that awakened consumer thread can consume the items.
 * 4. once consumer has acquired the lock, it will keep consuming the items and will keep awaking producer
      after each consumption (because once item is consumed, buffer is ready to be filled by producer).
 * 5. if consumer has finished the consumption, it will go to waiting state.
 * 6. STEPS WILL BE REPEATED BASED ON WHO HAS ACQUIRED THE LOCK (🔒 PRODUCER OR CONSUMER)
 
 * 7. https://en.wikipedia.org/wiki/Producer%E2%80%93consumer_problem
 */
class ProductFactory {
  private Lock lock;
  private Condition condition;
  private List<Integer> productList;
  private int capacity = 5;
  private int item = 0;

  ProductFactory() {
    this.productList = new ArrayList<Integer>();
    this.lock = new ReentrantLock();
    this.condition = lock.newCondition();
  }

  ProductFactory(int capacity) {
    this();
    this.capacity = capacity;
  }

  void produce() throws InterruptedException {

    while (true) {
      lock.lock();
      if (productList.size() == capacity) {
        System.out.println("Producer " + Thread.currentThread().getName() + " has switched to wait state.");
        condition.await();    // buffer is full, wait for consumption
      } else {
        System.out.println(Thread.currentThread().getName() +  " Produced : "  + item);
        productList.add(item);
        item++;
        condition.signalAll();  // as soon as produce any item, notify the consumers
      }
      lock.unlock();
      Thread.sleep(300);
    }
  }

  void consume() throws InterruptedException {
    while (true) {
      lock.lock();
      if (productList.isEmpty()) {
        System.out.println("Consumer " + Thread.currentThread().getName() + " has switched to wait state.");
        condition.await();    // buffer is full, wait for production
      } else {
        System.out.println(Thread.currentThread().getName() +  " Consumed : "  + item);
        productList.remove(--item);
        condition.signalAll(); // as soon as consume any item, invoke the producers
      }
      lock.unlock();
      Thread.sleep(200);
    }
  }
}

class ProducerConsumerAsync {
  public static void main(String[] args) {
    final ProductFactory productFactory = new ProductFactory(10);

    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          productFactory.produce();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          productFactory.consume();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    Thread t3 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(3000);
          productFactory.consume();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    Thread t4 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          productFactory.produce();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    Thread t5 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          productFactory.produce();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    });

    t1.start();
    t2.start();
    t3.start();
    t4.start();
    t5.start();

    try {
      t1.join();
      t2.join();
      t3.join();
      t4.join();
      t5.join();
    } catch (final InterruptedException ie) {
      ie.printStackTrace();
    }
  }

