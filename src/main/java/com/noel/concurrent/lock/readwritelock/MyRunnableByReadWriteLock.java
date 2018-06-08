package com.noel.concurrent.lock.readwritelock;

public class MyRunnableByReadWriteLock implements Runnable {

  public void run() {
    for (int i = 0; i < 10; i++) {
      ExecuteByReadWriteLock.incrementByLock.incremente();
      String info = Thread.currentThread().getName() + " / indice de boucle : " + i + " / valeur incrementée : "+ (ExecuteByReadWriteLock.incrementByLock.get());

      System.out.println(info);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
