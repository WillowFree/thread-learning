package com.noel.concurrent.synchronizers.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Client implements Runnable {

  String clientName;
  Semaphore semaphore;

  public Client(String pName, Semaphore pSem) {
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
      semaphore.release();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
