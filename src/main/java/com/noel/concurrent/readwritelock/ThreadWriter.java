package com.noel.concurrent.readwritelock;

/**
 * Ce Thread ECRIT des données dans le dictionnaire
 */
public class ThreadWriter extends Thread {

  private Dictionnaire dico;

  public ThreadWriter(String nom, Dictionnaire pDico) {
    setName(nom);
    dico = pDico;
  }

  public void run() {
    while (true) {
      dico.ecrire();

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
