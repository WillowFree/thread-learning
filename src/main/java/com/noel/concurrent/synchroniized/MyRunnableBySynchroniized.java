package com.noel.concurrent.synchroniized;

public class MyRunnableBySynchroniized implements Runnable {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      ExecuteBySynchro.incrementBySynchroniized.incremente();
      String info = Thread.currentThread().getName() + " - "
          + (ExecuteBySynchro.incrementBySynchroniized.get());
      System.out.println(info);
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
