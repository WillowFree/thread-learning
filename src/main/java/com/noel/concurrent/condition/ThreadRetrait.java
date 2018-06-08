package com.noel.concurrent.condition;

import java.util.Random;

public class ThreadRetrait extends Thread {

  private CompteEnBanque compteEnBanque;
  private Random random = new Random();
  private static int numeroThread = 1;

  public ThreadRetrait(CompteEnBanque compteEnBanque1){
    compteEnBanque = compteEnBanque1;
    this.setName("Retrait" + numeroThread++);
  }

  public void run() {
    while(true){
      int nb = random.nextInt(300);
      long montant = Integer.valueOf(nb).longValue();
      compteEnBanque.retrait(montant);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
