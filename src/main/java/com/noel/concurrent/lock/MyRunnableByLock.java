package com.noel.concurrent.lock;

public class MyRunnableByLock implements Runnable {

  public void run() {
    for (int i = 0; i < 10; i++) {
      ExecuteByLock.incrementByLock.incremente();
      String info = Thread.currentThread().getName() + " / indice de boucle : " + i + " / valeur incrementée : " + (ExecuteByLock.incrementByLock.get());

      System.out.println(info);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
