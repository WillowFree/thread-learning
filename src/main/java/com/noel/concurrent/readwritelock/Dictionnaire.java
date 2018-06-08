package com.noel.concurrent.readwritelock;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * comment bien locker lire() et ecrire() ?
 */
public class Dictionnaire {

  private Map<Integer, String> dico = new TreeMap<>();
  private String[] listDeMots = {"abc", "bcd", "cde", "def", "efg"};
  private Random random = new Random();
  private static AtomicInteger indiceCollection = new AtomicInteger(0);

  ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  Lock writeLock = readWriteLock.writeLock();
  Lock readLock = readWriteLock.readLock();

  public void ecrire() {

    // On pose le VERROU EN ECRITURE : exclusif
    // ce qui va BLOQUER TOUS les autres THREADS en ECRITURE ET en LECTURE
    writeLock.lock();

    try {
      //On fait notre traitement
      String motAuHasard = listDeMots[random.nextInt(5)];
      int incrementeIndice = indiceCollection.getAndIncrement();
      String motAuHasardAvecIndice = motAuHasard + incrementeIndice;

      dico.put(incrementeIndice, motAuHasardAvecIndice);

      System.err.println(Instant.now() + " - " + Thread.currentThread().getName() + " : indice = "
          + incrementeIndice + " ; mot = " + motAuHasardAvecIndice);


    } finally {
      //On n’oublie surtout pas de libérer le verrou !
      writeLock.unlock();
    }
  }

  public void lire() {

    // On pose le VERROU EN LECTURE : peut etre partagé par plusieurs thread en lecture
    // - PAS D'EFFET pour les threads qui ne font que LIRE
    // - STOP ICI la lecture, si un autre Thread est en train d'ECRIRE
    readLock.lock();

    try {
      // On fait notre traitement
      if (dico.keySet().size() > 0) {
        int tailleDuDictionnaire = dico.keySet().size();
        int indiceDuDictionnaireAuHasard = random.nextInt(tailleDuDictionnaire);


        // Affiche un mot au hasard du dictionnaire
        System.out.println(Instant.now() + " - " + Thread.currentThread().getName() + " : indice = "
            + indiceDuDictionnaireAuHasard + " ; mot = " + dico.get(indiceDuDictionnaireAuHasard));

      }
    } finally {
      // On n’oublie surtout pas de libérer le verrou !
      readLock.unlock();
    }
  }
}
