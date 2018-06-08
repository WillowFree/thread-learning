package com.noel.concurrent.condition;

import java.util.Random;

public class ThreadDepot extends Thread {

  private CompteEnBanque compteEnBanque;
  private Random random = new Random();

  public ThreadDepot(CompteEnBanque compteEnBanque1) {
    compteEnBanque = compteEnBanque1;
    this.setName("Dépôt");
  }

  public void run() {
    while (true) {
      int randomNumber = random.nextInt(100);
      long montant = Integer.valueOf(randomNumber).longValue();
      compteEnBanque.depot(montant);

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
