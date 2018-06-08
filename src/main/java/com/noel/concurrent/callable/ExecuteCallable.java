package com.noel.concurrent.callable;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Callable : permet de retourner un resultat contrairement à Runnable.
 * Le resultat du callable peut etre récupérer en utilsant l'objet Future
 */
public class ExecuteCallable {

  public static void main(String[] args){

    //Nous créons un objet Callable basique
    Callable<Integer> callable = () -> {
        Random rand = new Random();
        int result = rand.nextInt(2_000);
        System.out.println("Dans l'objet Callable : " + result);
        try {
          Thread.sleep(3_000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return result;
      };

    //nous l'associons à un objet FutureTask
    //du même type générique
    FutureTask<Integer> futureTask = new FutureTask<>(callable);

    System.out.println(" - Lancement de notre premier test.");
    //Pour que cette tâche soit lancée dans un thread
    //nous devons tout de même utiliser la classe Thread
    //qui autorise un objet de type FutureTask dans son constructeur
    Thread futureTaskThread = new Thread(futureTask);

    //Nous lançons maintenant le thread
    futureTaskThread.start();
    System.out.println("Traitement…");
    try {
      //Ici, notre objet Future attend la fin de la tâche pour
      //retourner le résultat, en attendant
      //le thread courant est bloqué
      System.out.println("Résultat : " + futureTask.get());
    } catch (Exception e) {
      e.printStackTrace();
    }

    showStatus(futureTask);

    System.out.println("\n - Lancement de notre second test.");
    futureTask = new FutureTask<>(callable);
    futureTaskThread = new Thread(futureTask);
    futureTaskThread.start();
    System.out.println("Traitement…");

    //Ici, nous mettons un délai, il y aura donc une exception de levée
    //car le délai est inférieur à la pause dans l'objet Callable
    try {
      System.out.println("Résultat : " + futureTask.get(500, TimeUnit.MILLISECONDS));
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      System.err.println("La tâche à mis trop de temps à répondre.");
    }
    //Cette instruction n'affichera rien car le statut
    //de la tâche n'est ni OK ni annulée...
    showStatus(futureTask);

  }

  private static void showStatus(FutureTask<Integer> futureTask){
    if(futureTask.isDone()) {
      System.out.println("La tâche c'est déroulée correctement");
    }

    if(futureTask.isCancelled()) {
      System.out.println("La tâche a été annulée");
    }
  }
}