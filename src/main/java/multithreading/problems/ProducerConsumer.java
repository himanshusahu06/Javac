package multithreading;

import java.util.ArrayList;
import java.util.List;

class Factory {
  private List<Integer> items = new ArrayList<Integer>();
  private final int LIMIT = 5;
  private int value = 0;
  private final Object lock = new Object();

  void produce() throws InterruptedException {
    synchronized (lock) {
      while (true) {
        if (items.size() == LIMIT) {
          System.out.println("Waiting for consumer to consume the item.");
          lock.wait();
         } else {
          System.out.println("Produced: " + value);
          items.add(value);
          value++;
          lock.notify();
        }
        Thread.sleep(500);
      }
    }
  }

  void consume() throws InterruptedException {
    synchronized (lock) {
      while (true) {
        if (items.isEmpty()) {
          System.out.println("Waiting for producer to produce the item.");
          lock.wait();
        } else {
          System.out.println("Consumed: " + value);
          items.remove(--value);
          lock.notify();
        }
        Thread.sleep(800);
      }
    }
  }
}

public class ProducerConsumer {
  public static void main(String[] args) {
    final Factory factory = new Factory();
    Thread t1 = new Thread(new Runnable() {
      public void run() {
        try {
          factory.produce();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    }, "producer");
    Thread t2 = new Thread(new Runnable() {
      public void run() {
        try {
          factory.consume();
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
    }, "consumer");

    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }
}
