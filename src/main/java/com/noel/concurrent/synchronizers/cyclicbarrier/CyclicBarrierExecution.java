package com.noel.concurrent.synchronizers.cyclicbarrier;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CYCLICBARRIER : pose une barrière, définie à l'avance par un entier. Cette barrière stoppe les
 * threads qui y arrivent et les met en attente jusqu'à ce que le nombre de threads atteignant
 * ladite barrière corresponde au nombre défini par nos soins. Une fois que le nombre de threads
 * ayant atteint la barrière correspond à notre quota, tous les threads reprennent leurs tâches.
 */
public class CyclicBarrierExecution {

  public static void main(String[] args) {

    ExecutorService executeService = Executors.newFixedThreadPool(4);
    CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

    CyclicBarrierExemple cbe1, cbe2, cbe3, cbe4;
    cbe1 = new CyclicBarrierExemple(0, 100, cyclicBarrier, "Thread-0-100");
    cbe2 = new CyclicBarrierExemple(1_000, 5_000, cyclicBarrier, "Thread-1000-5000");
    cbe3 = new CyclicBarrierExemple(5_000, 15_000, cyclicBarrier, "Thread-5000-15000");
    cbe4 = new CyclicBarrierExemple(10_000, 50_000, cyclicBarrier, "Thread-10000-50000");

    Future<Integer> ft1 = executeService.submit(cbe1);
    Future<Integer> ft2 = executeService.submit(cbe2);
    Future<Integer> ft3 = executeService.submit(cbe3);
    Future<Integer> ft4 = executeService.submit(cbe4);

    try {
      System.out.println("Total = " + (ft1.get() + ft2.get() + ft3.get() + ft4.get()));
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    executeService.shutdown();
  }
}
