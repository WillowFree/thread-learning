package com.noel.concurrent.executor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FolderScannerCallable implements Callable<Long> {

  private Path path;
  private long nbDeFichiersTrouves = 0;

  public FolderScannerCallable() {
  }

  public FolderScannerCallable(Path path) {
    this.path = path;
  }

  /**
   * Méthode qui se charge de scanner les dossiers de façon récursive
   */
  public Long call() {

    System.out.println("Exécution dans " + Thread.currentThread().getName());
    System.out.println("Scan du dossier : " + path + " à la recherche des fichiers ");

    //On liste maintenant le contenu du répertoire pour traiter les sous-dossiers
    try (DirectoryStream<Path> listing = Files.newDirectoryStream(path)) {
      for (Path nom : listing) {
        //S'il s'agit d'un dossier, on le scanne grâce à notre objet
        if (Files.isDirectory(nom.toAbsolutePath())) {

          //si nous sommes dans un répertoire, nous lançons un nouveau thread d'exécution
          //sur ce répertoire pour compter le nombre de fichiers
          FolderScannerCallable folderScannerCallable = new FolderScannerCallable(
              nom.toAbsolutePath());

          //On retrouve notre objet FutureTask qui va nous permettre de récupérer
          //le résultat du comptage de fichiers
          FutureTask<Long> futureTask = new FutureTask<>(folderScannerCallable);
          Thread thread = new Thread(futureTask);
          thread.setName("MyHomeMade Thread");
          thread.start();

          try {
            nbDeFichiersTrouves += futureTask.get();
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Maintenant, on filtre le contenu de ce même dossier sur le filtre défini
    try (DirectoryStream<Path> listing = Files.newDirectoryStream(path)) {
      for (Path nom : listing) {
        System.out.println(" trouve : " + nom);
        if (Files.isRegularFile(nom)) {
          //Pour chaque fichier correspondant, on incrémente notre compteur
          nbDeFichiersTrouves++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("nb De Fichiers Trouves : " + nbDeFichiersTrouves);
    return nbDeFichiersTrouves;
  }


}