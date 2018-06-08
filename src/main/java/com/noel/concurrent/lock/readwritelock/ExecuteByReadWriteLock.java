package com.noel.concurrent.lock.readwritelock;

import java.time.Instant;


/**
 *
 */
public class ExecuteByReadWriteLock {

  public static IncrementByReadWriteLock incrementByLock = new IncrementByReadWriteLock();

  public static void main(String[] args) {

    System.out.println("Start : " + Instant.now().toEpochMilli());
    try {
      Thread t1 = new Thread(new MyRunnableByReadWriteLock());
      Thread t2 = new Thread(new MyRunnableByReadWriteLock());
      Thread t3 = new Thread(new MyRunnableByReadWriteLock());
      Thread t4 = new Thread(new MyRunnableByReadWriteLock());

      t1.start();
      t2.start();
      t3.start();
      t4.start();
    } finally {
      System.out.println("End : " + Instant.now().toEpochMilli());
    }
  }

}
