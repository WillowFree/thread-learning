package com.noel.concurrent.condition;

public interface CompteEnBanque {

  void retrait(long montant);

  void depot(long montant);

  void solde();

  long getSolde();

  long getDecouvertAutorise();
}
