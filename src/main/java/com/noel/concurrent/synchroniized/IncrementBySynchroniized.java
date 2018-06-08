package com.noel.concurrent.synchroniized;

public class IncrementBySynchroniized {

  private volatile Integer entier = 0;

  public void incremente() {
    synchronized (this) {
      entier = entier + 1;
    }
  }

  public Integer get() {
      return entier;
  }
}
