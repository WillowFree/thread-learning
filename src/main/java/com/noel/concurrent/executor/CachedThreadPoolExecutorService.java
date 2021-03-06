package com.noel.concurrent.executor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * <li>
 * <li>Crée un pool de thread où, lorsqu'un thread prend fin, ce dernier reste en cache pour être
 * réutilisé si besoin.
 * <li>Cela permet d'éviter de re-créer un thread et donc de gagner en performance de temps
 * d'exécution.
 * <li>La taille du pool grandit en fonction des besoins. Les threads mis en cache restent en attente
 * pendant 60 secondes. Après ce délai, ils sont retirés du cache.
 */
public class CachedThreadPoolExecutorService {

  public static void main(String[] args) {

    //Notre executor
    ExecutorService execute = Executors.newCachedThreadPool();

    //Nous créons une liste stockant les objets Future<Long>
    List<Future<Long>> listFuture = new ArrayList<>();

    //Nous créons maintenant nos objets
    Path chemin1 = Paths.get("D:\\Java\\tools\\intellij");
    Path chemin2 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\conf");
    Path chemin3 = Paths.get("D:\\Java\\tools\\apache-maven-3.5.3\\boot");

    //On change un peu le code en utilisant une boucle
    Path[] chemins = new Path[]{chemin1, chemin2, chemin3};

    Long nbAgregesDeFichiersTrouves = 0L;

    for (Path path : chemins) {
      //Nous laçons le traitement
      Future<Long> future = execute.submit(new FolderScannerCallable(path));
      //Nous stockons l'objet Future<Long>
      //si nous avions utilisé la méthode get() directement
      //Les tâches se seraient lancées de façon séquentielle
      //car la méthode get() ATTEND LA FIN DU TRAITEMENT
      listFuture.add(future);
    }

    //Afin d'avoir un traitement en parallèle
    //nous parcourons maintenant la liste de nos objets Future<T>
    Iterator<Future<Long>> it = listFuture.iterator();
    while (it.hasNext()) {
      try {
        nbAgregesDeFichiersTrouves += it.next().get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    System.out.println("nombre total de fichiers trouvés : " + nbAgregesDeFichiersTrouves);

    //Dès que nos tâches sont terminées, nous fermons le pool
    //Sans cette ligne, ce programme restera en cours d'exécution
    execute.shutdown();
  }
}