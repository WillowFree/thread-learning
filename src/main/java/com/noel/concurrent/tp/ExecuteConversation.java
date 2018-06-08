package com.noel.concurrent.tp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecuteConversation {

  public static void main(String[] args) {

    Conversation conversation = new Conversation();
    Lock verrou = new ReentrantLock();
    Condition conditionSurLesQuestions = verrou.newCondition();
    Condition conditionSurLesReponses = verrou.newCondition();

    Thread journalisteThread = new JournalisteThread(conversation, verrou, conditionSurLesQuestions, conditionSurLesReponses);
    Thread personneThread = new PersonneThread(conversation, verrou, conditionSurLesQuestions, conditionSurLesReponses);

    journalisteThread.start();
    personneThread.start();
  }

}
