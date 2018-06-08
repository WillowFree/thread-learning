package com.noel.concurrent.tp;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PersonneThread extends Thread {

  private Conversation conversation;
  private Lock verrou;
  private Condition conditionSurLesQuestions;
  private Condition conditionSurLesReponses;
  private Scanner scanner = new Scanner(System.in);

  public PersonneThread(Conversation conversation1, Lock lock1, Condition conditionSurLesQuestions1,
      Condition conditionSurLesReponses1) {

    this.setName("Interviewé");
    conversation = conversation1;
    verrou = lock1;
    conditionSurLesQuestions = conditionSurLesQuestions1;
    conditionSurLesReponses = conditionSurLesReponses1;

  }

  public void run() {
    int i = 0;
    while (true) {
      repondre(i);
      i++;
    }
  }

  private void repondre(int i) {

    try {
      verrou.lock();

      System.out.println("Interviewvé, votre reponse : ");
      scanner.nextLine();

//      String reponse = conversation.getReponse(i);
//      System.out.println("Personne : " + reponse);


        conditionSurLesQuestions.notifyAll();
      conditionSurLesReponses.await();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IllegalMonitorStateException e) {
        e.printStackTrace();
    } finally {
      verrou.unlock();
    }

  }
}
