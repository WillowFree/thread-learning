package com.noel.concurrent.lock.readwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class IncrementByReadWriteLock {

  ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  Lock readLock = readWriteLock.readLock();
  Lock writeLock = readWriteLock.writeLock();

  private int entier = 0;

  public void incremente() {
    writeLock.lock();
    try {
      entier++;
      System.out.println(Thread.currentThread().getName() + " / entier incrémenté : " + entier);

    } finally {
      writeLock.unlock();
    }
  }

  public Integer get() {
    readLock.lock();
    try {
      System.out.println(Thread.currentThread().getName() + " / entier lu : " + entier);
      return entier;
    } finally {
      readLock.unlock();
    }
  }
}
