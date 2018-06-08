package com.noel.concurrent.deadlocksample.detector;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockingCodeNewLocksBasic implements DeadlockingCode {

  private final Lock lock1 = new ReentrantLock();
  private final Lock lock2 = new ReentrantLock();

  public void f() {
    lock1.lock();
    try {
      lock2.lock();
      try {
        // do something
      } finally {
        lock2.unlock();
      }
    } finally {
      lock1.unlock();
    }
  }

  public void g() {
    lock2.lock();
    try {
      f();
    } finally {
      lock2.unlock();
    }
  }
}