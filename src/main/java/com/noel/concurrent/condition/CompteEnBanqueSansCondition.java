package com.noel.concurrent.condition;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CompteEnBanqueSansCondition implements CompteEnBanque {

  private AtomicLong solde = new AtomicLong(1_000L);
  private final long decouvert = -130L;

  private Lock verrou = new ReentrantLock();
  private Condition condition = verrou.newCondition();

  /**
   * C'est sur cette méthode que nous allons devoir travailler dans nos threads et vérifier le solde
   * avant de retirer de l'argent
   */
  public void retrait(long montant) {
    verrou.lock();
    try {
      System.out.println("montant de retrait : " + Thread.currentThread().getName() + " : " + montant);

      long soldeAvantRetrait = solde.get();

      if(soldeAvantRetrait - montant > decouvert) {
        solde.set((soldeAvantRetrait - montant));
      } else {
        System.out.println("retrait REFUSE : " + Thread.currentThread().getName() + " : " + montant);
      }
      solde();


    } finally {
      verrou.unlock();
    }
  }

  // Puisqu’on utilise un objet AtomicLong
  // Inutile de synchroniser. ^^
  public void depot(long montant) {
    synchronized (this) {
      System.out.println("montant de depot : " + Thread.currentThread().getName() + " : " +  montant);
      long result = solde.addAndGet(montant);
      solde();
    }
  }

  public synchronized void solde() {
    System.out.println("Solde actuel, dans " + Thread.currentThread().getName() + " : " + solde.longValue());
  }

  public synchronized long getSolde() {
    return solde.longValue();
  }

  public long getDecouvertAutorise() {
    return decouvert;
  }
}