package com.noel.concurrent.synchronizers.exchanger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class FileFinder implements Runnable {

  private List<String> listDocumentExchanged = new ArrayList<>();
  private List<String> listDocumentInitial = new ArrayList<>();

  private Exchanger exchanger;

  public FileFinder(Exchanger ex) {
    exchanger = ex;

    listDocumentInitial.add("fichier 1");
    listDocumentInitial.add("fichier 2");
    listDocumentInitial.add("fichier 3");
    listDocumentInitial.add("fichier 4");
    listDocumentInitial.add("fichier 5");
  }

  public void run() {
    int numEchange = 1;
    while (true) {
      System.out.println("---------------------------------------");
      System.out.println("Contenu de la liste à échanger côté trouveur : " + listDocumentExchanged);
      System.out.println("---------------------------------------");


      for (String document : listDocumentInitial) {
        // On traite avec notre objet
        document = " numéro de l'échange=" +  numEchange + " - " + document;
        listDocumentExchanged.add(document);
        System.out.println("[++++] Ajout de " + document + " dans la collection à échanger");

        try {
          Thread.sleep(2500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }

      // Lorsque la liste est vide, on demande à récupérer une liste pleine
      try {
        System.err.println("\t -> Liste remplie du côte du trouveur de fichier !");
        listDocumentExchanged = (List<String>) exchanger.exchange(listDocumentExchanged);
        numEchange++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
