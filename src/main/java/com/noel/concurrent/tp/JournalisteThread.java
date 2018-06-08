package com.noel.concurrent.tp;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class JournalisteThread extends Thread {

  private Conversation conversation;
  private Lock verrou;
  private Condition conditionSurLesQuestions;
  private Condition conditionSurLesReponses;
  private Scanner scanner = new Scanner(System.in);

  public JournalisteThread(Conversation conversation1, Lock lock1,
      Condition conditionSurLesQuestions1, Condition conditionSurLesReponses1) {
    this.setName("Journaliste");
    conversation = conversation1;
    verrou = lock1;
    conditionSurLesQuestions = conditionSurLesQuestions1;
    conditionSurLesReponses = conditionSurLesReponses1;

  }

  public void run() {

    int i = 0;
    while (true) {
      poserQuestion(i);
      i++;
    }
  }

  private void poserQuestion(int i) {

    try {
      verrou.lock();

      System.out.println("PPDA, posez votre question : ");
      scanner.nextLine();

//      String question = conversation.getQuestion(i);
//      System.out.println("Journaliste : " + question);

      if (i != 0) {
        conditionSurLesReponses.notifyAll();
      }
      conditionSurLesQuestions.await();
      
    } catch (InterruptedException  e) {
      e.printStackTrace();
    } catch (IllegalMonitorStateException  e) {
      e.printStackTrace();
    } finally {
      verrou.unlock();
    }
  }

}
