package com.noel.concurrent.executor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheludedThreadPoolExecutoreService {

  public static void main(String[] args) {

    //Cette instruction permet de lister le nombre de processeurs disponibles
    //sur la machine exécutant le programme
    int corePoolSize = Runtime.getRuntime().availableProcessors();
    System.out.println("Nombre de processeurs disponibles : " + corePoolSize);

    //Notre executor avec un nombre de processeurs fixés dynamiquement
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);

    //Nous créons une liste stockant les objets Future<Long>
    List<Future<Long>> listFuture = new ArrayList<>();

    //Nous créons maintenant nos objets
    Path chemin1 = Paths.get("D:\\Java\\tools\\intellij");
    Path chemin2 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\conf");
    Path chemin3 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\boot");

    Long total = 0L;

    //Ici, nous lançons la tâche N° 1 dans 10 secondes
    Future<Long> scheduledFuture = scheduledExecutorService.schedule(new FolderScannerCallable(chemin1), 10, TimeUnit.SECONDS);
    listFuture.add(scheduledFuture);

    //Ici, nous lançons la tâche N° 2 dans 1 secondes
    Future<Long> scheduledFuture2 = scheduledExecutorService
        .schedule(new FolderScannerCallable(chemin2), 1000, TimeUnit.MILLISECONDS);
    listFuture.add(scheduledFuture2);

    //Ici, nous lançons la tâche N° 3 dans 5 secondes
    Future<Long> scheduledFuture3 = scheduledExecutorService.schedule(new FolderScannerCallable(chemin3), 5, TimeUnit.SECONDS);
    listFuture.add(scheduledFuture3);

    //Afin d'avoir un traitement en parallèle
    //nous parcourons maintenant la liste de nos objets Future<T>
    for (Future<Long> future : listFuture) {
      try {
        total += future.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    System.out.println("nombre total de fichiers agreges trouvés : " + total);

    //Dès que nos tâches sont terminées, nous fermons le pool
    //Sans cette ligne, ce programme restera en cours d'exécution
    scheduledExecutorService.shutdown();
  }

}
