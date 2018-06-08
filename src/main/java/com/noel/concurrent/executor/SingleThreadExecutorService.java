package com.noel.concurrent.executor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SingleThreadExecutorService {

  public static void main(String[] args) {

    //Notre executor mono-thread
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    //Nous créons maintenant nos objets
    Path chemin1 = Paths.get("D:\\Java\\tools\\intellij");
    Path chemin2 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\conf");
    Path chemin3 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\boot");

    //La méthode submit permet de récupérer un objet Future
    //qui contiendra le résultat obtenu
    Future<Long> ft1 = executorService.submit(new FolderScannerCallable(chemin1));
    Future<Long> ft2 = executorService.submit(new FolderScannerCallable(chemin2));
    Future<Long> ft3 = executorService.submit(new FolderScannerCallable(chemin3));

    Long nbAggregesDeFichiersTrouves;
    try {
      //Nous ajoutons tous les résultats
      nbAggregesDeFichiersTrouves = ft1.get() + ft2.get() + ft3.get();
      System.out.println("\nnombre aggrégés de fichiers trouvés : " + nbAggregesDeFichiersTrouves);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    //Dès que nos tâches sont terminées, nous fermons le pool
    //Sans cette ligne, ce programme restera en cours d'exécution
    executorService.shutdown();
  }
}
