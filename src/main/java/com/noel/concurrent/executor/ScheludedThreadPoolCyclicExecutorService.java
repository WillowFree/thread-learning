package com.noel.concurrent.executor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ScheludedThreadPoolCyclicExecutorService {


  public static void main(String[] args) {

    //Cette instruction permet de lister le nombre de processeurs disponibles
    //sur la machine exécutant le programme
    int corePoolSize = Runtime.getRuntime().availableProcessors();
    System.out.println("Nombre de processeurs disponibles : " + corePoolSize);

    //Notre executor avec un nombre de processeurs fixés dynamiquement
    ScheduledExecutorService execute = Executors.newScheduledThreadPool(corePoolSize);

    //Nous créons maintenant nos objets
    Path chemin1 = Paths.get("D:\\Java\\tools\\intellij");
    Path chemin2 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\conf");
    Path chemin3 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\boot");

    //Ici, nous lançons la tâche N° 3 dans 40 secondes
    //elle se répétera toutes les 20 secondes
    execute.scheduleAtFixedRate(new FolderScannerRunnable(chemin3), 40_000, 20_000, TimeUnit.MILLISECONDS);

    //Ici, nous lançons la tâche N° 2 dans 30 secondes
    //elle se répétera toutes les 30 secondes
    execute.scheduleAtFixedRate(new FolderScannerRunnable(chemin2), 2, 4, TimeUnit.SECONDS);

    //Ici, nous lançons la tâche N° 1 dans 1 minute
    //elle se répétera toutes les minutes
    execute.scheduleWithFixedDelay(new FolderScannerRunnable(chemin1), 5, 1, TimeUnit.SECONDS);

    //Si nous fermons le pool, les tâches seront abandonnées
    //j'ai donc commenté la ligne
    //execute.shutdown();
  }
}
