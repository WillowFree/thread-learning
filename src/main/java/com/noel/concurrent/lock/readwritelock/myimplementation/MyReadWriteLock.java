package com.noel.concurrent.lock.readwritelock.myimplementation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * But : implémenter un mécanisme qui empeche les ecritures quand au moins un thread lit la
 * ressources empeche la lecture quand au moins un thread écrit dans la ressource
 */
public class MyReadWriteLock {

  private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private Lock readLock = readWriteLock.readLock();
  private Lock writeLock = readWriteLock.writeLock();
  private int readCounter;
  private int sharedReources;

  void read() {

    readCounter++;
    if (readCounter >= 1) {
      writeLock.lock();
    }

    System.out.println(Thread.currentThread().getName() + " is reading : " + sharedReources);

    writeLock.unlock();
    readCounter--;
  }


  void write() {
    readLock.lock();
    writeLock.lock();

    System.out.println(Thread.currentThread().getName() + " is writing resource :" + ++sharedReources);

    writeLock.unlock();
    readLock.unlock();
  }


}
