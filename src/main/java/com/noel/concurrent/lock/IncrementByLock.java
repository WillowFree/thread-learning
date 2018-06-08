package com.noel.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncrementByLock {

  private Lock lock = new ReentrantLock();

  private int entier = 0;

  public void incremente() {
    lock.lock();
    try {
      entier++;
      System.out.println(Thread.currentThread().getName() + " / entier incrémenté : " + entier);

    } finally {
      lock.unlock();
    }
  }

  public Integer get() {
    lock.lock();
    try {
      System.out.println(Thread.currentThread().getName() + " / entier lu : " + entier);
      return entier;
    } finally {
      lock.unlock();
    }
  }
}
