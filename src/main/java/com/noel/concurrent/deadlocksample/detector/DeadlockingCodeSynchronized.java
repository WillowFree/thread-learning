package com.noel.concurrent.deadlocksample.detector;

public class DeadlockingCodeSynchronized implements DeadlockingCode {

  private final Object lock = new Object();

  public synchronized void f() {
    synchronized (lock) {
      // do something
    }
  }

  public void g() {
    synchronized (lock) {
      f();
    }
  }
}