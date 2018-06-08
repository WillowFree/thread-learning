package com.noel.concurrent.synchronizers.exchanger;

import java.util.concurrent.Exchanger;

/**
 * EXCHANGER : permet à deux threads de s'échanger un objet entre eux.
 *
 * <li>Par exemple, nous pourrions avoir un thread qui initialise une liste de documents à déplacer
 * et un autre thread qui se charge de les déplacer.
 * <li>Une fois tous les déplacement faits, la liste des documents à traiter est vide
 * et demande une nouvelle liste au thread qui détermine quel sont les documents à traiter :
 * <li>ils s'échangent donc leurs listes respectives et ainsi de suite…
 * <li>
 * <li>Tant que les deux threads n'invoquent pas ensemble la méthode exchange(), l'un attend l'autre.
 */
public class ExchangerExecutor {

  public static void main(String[] args) {

    Exchanger exchanger = new Exchanger();

    Thread fileFinderThread = new Thread(new FileFinder(exchanger));
    Thread fileMoverThread = new Thread(new FileMover(exchanger));

    fileFinderThread.start();
    fileMoverThread.start();
  }

}
