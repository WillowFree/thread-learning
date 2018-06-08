package com.noel.concurrent.lock;

import java.time.Instant;

public class ExecuteByLock {

  public static IncrementByLock incrementByLock = new IncrementByLock();

  public static void main(String[] args) {

    System.out.println("Start : " + Instant.now().toEpochMilli());

    Thread t1 = new Thread(new MyRunnableByLock());
    Thread t2 = new Thread(new MyRunnableByLock());
    Thread t3 = new Thread(new MyRunnableByLock());
    Thread t4 = new Thread(new MyRunnableByLock());

    t1.start();
    t2.start();
    t3.start();
    t4.start();

    System.out.println("End : " + Instant.now().toEpochMilli());

  }
}
