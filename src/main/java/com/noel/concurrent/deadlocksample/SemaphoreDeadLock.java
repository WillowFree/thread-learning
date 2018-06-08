package com.noel.concurrent.deadlocksample;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDeadLock implements Runnable {

  private String clientName;
  private Semaphore semaphore;

  public SemaphoreDeadLock(String pName, Semaphore pSem) {
    clientName = pName;
    semaphore = pSem;
  }

  @Override
  public void run() {
    try {

      // Nous prenons une réservation de ressource
      semaphore.acquire();
      // lorsque le compteur atteint 0, la ressource n'est plus disponible,
      // il faut que le compteur soit SUPERIEUR à 0 pour pouvoir y accéder.

      Random random = new Random();
      //Pour avoir une pause conséquente et bien voir le mécanisme du sémaphore.
      long pause = 0;
      while (pause < 8000) {
        pause = random.nextInt(15000);
      }

      System.out
          .println(clientName + " : Je mange au restaurant pendant " + pause / 1000 + " secondes");

      Thread.sleep(pause);

      System.err.println(clientName + " : Au revoir. Je quitte le restaurant. ");

      // Pour libérer la ressource
//      semaphore.release();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) {
    //Bon, c'est un restaurant à 5 places...
    //C'est petit, mais en Bretagne, il y en a. ;)
    Semaphore semaphore = new Semaphore(5);

    ExecutorService execute = Executors.newCachedThreadPool();

    int i = 0;
    while (true) {
      SemaphoreDeadLock semaphoreDeadLock = new SemaphoreDeadLock("Client N°" + (++i), semaphore);
      execute.execute(semaphoreDeadLock);

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
