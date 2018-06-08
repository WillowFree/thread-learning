package com.noel.concurrent.synchronizers.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SEMAPHORES : permettent de mettre un verrou en fonction d'un entier, celui-ci représentant
 * un seuil d'acceptation d'utilisation d'une ressource (repensez aux places de libres sur le
 * manège). Cet  objet a un constructeur prenant un entier comme paramètre : celui-ci sera la limite
 * autorisée pour la ressource ; il possède aussi deux méthodes : acquire() / release().
 *
 *
 *
 */
public class Restaurant {

  //  Lors de l'exécution, vous avez pu voir que lorsque le nombre de clients à table atteint le nombre de 5,
  // les autres ne peuvent pas entrer et utiliser les places du restaurant.
  // Ce n'est que lorsqu'un client sort du restaurant et libère une place qu'un autre client peut y siéger :
  // voilà le fonctionnement des sémaphores.
  public static void main(String[] args) {
    //Bon, c'est un restaurant à 5 places...
    //C'est petit, mais en Bretagne, il y en a. ;)
    Semaphore semaphore = new Semaphore(5);

    ExecutorService executorService = Executors.newCachedThreadPool();

    int i = 0;
    while (true) {
      Client client = new Client("Client N°" + (++i), semaphore);
      executorService.execute(client);

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }
}