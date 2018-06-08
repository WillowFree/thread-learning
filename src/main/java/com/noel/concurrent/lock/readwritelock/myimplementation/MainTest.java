package com.noel.concurrent.lock.readwritelock.myimplementation;

import com.noel.concurrent.lock.readwritelock.MyRunnableByReadWriteLock;
import java.time.Instant;

public class MainTest {

  public static void main(String[] args) {

    System.out.println("Start : " + Instant.now().toEpochMilli());


    MyReadWriteLock myReadWriteLock = new MyReadWriteLock();
    myReadWriteLock.read();
    myReadWriteLock.write();

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
