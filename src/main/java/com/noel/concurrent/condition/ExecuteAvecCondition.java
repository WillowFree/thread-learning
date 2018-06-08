package com.noel.concurrent.condition;

public class ExecuteAvecCondition {

  public static void main(String[] args) {

    CompteEnBanqueAvecCondition compteEnBanque = new CompteEnBanqueAvecCondition();

    //On crée deux threads de retrait
    Thread t1 = new ThreadRetrait(compteEnBanque);
    t1.start();

    Thread t2 = new ThreadRetrait(compteEnBanque);
    t2.start();

    //et un thread de dépôt
    Thread t3 = new ThreadDepot(compteEnBanque);
    t3.start();
  }

}