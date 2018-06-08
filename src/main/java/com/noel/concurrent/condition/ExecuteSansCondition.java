package com.noel.concurrent.condition;

public class ExecuteSansCondition {

  public static void main(String[] args) {

    CompteEnBanqueSansCondition compteEnBanqueSansCondition = new CompteEnBanqueSansCondition();

    //On crée deux threads de retrait
    Thread t1 = new ThreadRetrait(compteEnBanqueSansCondition);
    t1.start();

    Thread t2 = new ThreadRetrait(compteEnBanqueSansCondition);
    t2.start();

    //et un thread de dépôt
    Thread t3 = new ThreadDepot(compteEnBanqueSansCondition);
    t3.start();
  }

}