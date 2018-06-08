package com.noel.concurrent.tp;

public class Conversation {

  private static final String[] questions = {"Comment va ca monsieur ? ", "Ca progresse ? ", "au revoir "};
  private static final String[] reponses = {"Ca va bien merci journaliste", "On y travaille", "a bientot"};

  public String getQuestion(int i) {
    return questions[i];
  }

  public String getReponse(int i) {
    return reponses[i];
  }

}
