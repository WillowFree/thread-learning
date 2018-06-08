package com.noel.concurrent.readwritelock;

/**
 * Dans cet exemple on veut que :
 * <li> Plusieurs thread en lecture puisse lire en parralèe</li>
 * <li> DEs qu'un thread lit les données : ca empeche toute écriture</li>
 * <li> Des qu'un thread ecrit des doonnées : ça empeche toute lecture</li>
 */
public class ExecuterReadAndWriteLock {

  public static void main(String[] args) {

    Dictionnaire dico = new Dictionnaire();

    ThreadWriter tw1 = new ThreadWriter("Writer 1", dico);
    tw1.start();

    ThreadWriter tw2 = new ThreadWriter("Writer 2", dico);
    tw2.start();

    for (int i = 0; i < 6; i++) {
      ThreadReader tr = new ThreadReader("Reader " + i, dico);
      tr.start();
    }
  }

}