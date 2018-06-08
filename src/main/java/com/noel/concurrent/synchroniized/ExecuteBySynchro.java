package com.noel.concurrent.synchroniized;


public class ExecuteBySynchro {

  public static IncrementBySynchroniized incrementBySynchroniized = new IncrementBySynchroniized();

  public static void main(String[] args) {

//        System.out.println("" + Instant.now().get());

    Thread t1 = new Thread(new MyRunnableBySynchroniized());
    Thread t2 = new Thread(new MyRunnableBySynchroniized());
    Thread t3 = new Thread(new MyRunnableBySynchroniized());
    Thread t4 = new Thread(new MyRunnableBySynchroniized());

    t1.start();
    t2.start();
    t3.start();
    t4.start();

  }
}
