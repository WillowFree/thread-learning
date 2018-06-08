package com.noel.concurrent.synchronizers.cyclicbarrier.newversion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * CYCLICBARRIER : pose une barrière, définie à l'avance par un entier. Cette barrière stoppe les
 * threads qui y arrivent et les met en attente JUSQU'A ce que le NOMBRE DE THREADS ATTEIGNENTT
 * ladite barrière corresponde au nombre défini par nos soins. Une fois que le nombre de threads
 * ayant atteint la barrière correspond à notre quota, tous les threads reprennent leurs tâches.
 */
public class BarrierExecution2 {

  public static void main(String[] args) {

    CyclicBarrierExemple2 cbe1, cbe2, cbe3, cbe4;
    cbe1 = new CyclicBarrierExemple2(0, 100, "Thread-0-100");
    cbe2 = new CyclicBarrierExemple2(1_000, 5_000, "Thread-1000-5000");
    cbe3 = new CyclicBarrierExemple2(5_000, 15_000, "Thread-5000-15000");
    cbe4 = new CyclicBarrierExemple2(10_000, 50_000, "Thread-10000-50000");

    //Nous allons utiliser une liste pour lancer tous nos threads
    List<Callable<Integer>> tasks = new ArrayList<>();
    tasks.add(cbe1);
    tasks.add(cbe2);
    tasks.add(cbe3);
    tasks.add(cbe4);

    ExecutorService execute = Executors.newFixedThreadPool(4);

    //Cet objet accepte un deuxième argument qui est un Runnable
    //permettant de faire une action lorsque la barrière cède
    CyclicBarrier barrier = new CyclicBarrier(4, new AfterBarrier(tasks));

    //Nous mettons maintenant notre barrière dans nos objets Callable<Integer>
    cbe1.setBarrier(barrier);
    cbe2.setBarrier(barrier);
    cbe3.setBarrier(barrier);
    cbe4.setBarrier(barrier);

    try {
      //Cette méthode est nouvelle pour vous
      //Vous pouvez ainsi lancer une lister de threads
      //Et récupérer une liste d'objet Future<T> : un par objet Callable<T>
      List<Future<Integer>> listFuture = execute.invokeAll(tasks);

      int resultat = 0;

      //On parcourt les résultats
      for (Future<Integer> future : listFuture) {
        resultat += future.get();
      }

      System.out.println("Total : " + resultat);

    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }


    execute.shutdown();
  }
}