package com.noel.concurrent.executor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FolderScannerRunnable implements Runnable {

  private Path path;
  private long nbDeFichiersTrouves = 0;

  public FolderScannerRunnable() {
  }

  public FolderScannerRunnable(Path path) {
    this.path = path;
  }

  /**
   * Méthode qui se charge de scanner les dossiers de façon récursive
   */
  private Long call() {

    System.out.println("Exécution dans " + Thread.currentThread().getName());
    System.out.println("Scan du dossier : " + path + " à la recherche des fichiers ");

    //On liste maintenant le contenu du répertoire pour traiter les sous-dossiers
    try (DirectoryStream<Path> listing = Files.newDirectoryStream(path)) {
      for (Path nom : listing) {
        //S'il s'agit d'un dossier, on le scanne grâce à notre objet
        if (Files.isDirectory(nom.toAbsolutePath())) {

          //si nous sommes dans un répertoire, nous lançons un nouveau thread d'exécution
          //sur ce répertoire pour compter le nombre de fichiers
          FolderScannerRunnable folderScannerRunnable = new FolderScannerRunnable(
              nom.toAbsolutePath());

          //On retrouve notre objet FutureTask qui va nous permettre de récupérer
          //le résultat du comptage de fichiers
          Thread thread = new Thread(folderScannerRunnable);
          thread.setName("MyHomeMade Thread");
          thread.start();

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

  @Override
  public void run() {
    call();
  }
}