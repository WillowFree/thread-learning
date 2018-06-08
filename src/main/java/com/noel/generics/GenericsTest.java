package com.noel.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {

  static List<Voiture> allVoitures = new ArrayList<>();

  public static void main(String[] args) {

    List<Voiture> voitures = new ArrayList<>();
    List<VoitureSansPermis> voituresSansPermis = new ArrayList<>();

    Voiture voiture = new Voiture();
    VoitureSansPermis voitureSansPermis = new VoitureSansPermis();

    voitures.add(voiture);
    voitures.add(voitureSansPermis);

    // FORBIDDEN
//    voituresSansPermis.add(voiture);
    voituresSansPermis.add(voitureSansPermis);

    // FORBIDDEN
//    voitures = voituresSansPermis;

    // FORBIDDEN : READ ONLY AUTHORIZED
//    allVoitures.add(voiture);
//    allVoitures.add(voitureSansPermis);

    ajouterVoitures(voitures);
    ajouterVoitures(voituresSansPermis);

    affiche(allVoitures);
  }

  // READ ONY : AUTHORIZED when wilcard !
  //Avec cette méthode, on accepte aussi bien les collections de Voiture que les collection de VoitureSansPermis
  static void affiche(List<? extends Voiture> list) {
    for (Voiture v : list) {
      System.out.println("list " + list + ", " + v.toString());
    }
  }

  static void ajouterVoitures(List<? extends Voiture> list) {
    for (Voiture voiture : list) {
      allVoitures.add(voiture);
    }
  }

}

