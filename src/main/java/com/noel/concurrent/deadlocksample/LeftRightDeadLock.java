package com.noel.concurrent.deadlocksample;

/**
 * Found one Java-level deadlock:
 * =============================
 * "Thread-1":
 *   waiting to lock monitor 0x000000001c3d0d08 (object 0x000000076b4f1820, a java.lang.Object),
 *   which is held by "Thread-0"
 * "Thread-0":
 *   waiting to lock monitor 0x000000001c3d3388 (object 0x000000076b4f1830, a java.lang.Object),
 *   which is held by "Thread-1"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "Thread-1":
 *         at com.noel.concurrent.deadlock.LeftRightDeadLock.rightLeft(LeftRightDeadLock.java:31)
 *         - waiting to lock <0x000000076b4f1820> (a java.lang.Object)
 *         - locked <0x000000076b4f1830> (a java.lang.Object)
 *         at com.noel.concurrent.deadlock.LeftRightDeadLock.lambda$main$1(LeftRightDeadLock.java:45)
 *         at com.noel.concurrent.deadlock.LeftRightDeadLock$$Lambda$2/1831932724.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 * "Thread-0":
 *         at com.noel.concurrent.deadlock.LeftRightDeadLock.leftRight(LeftRightDeadLock.java:17)
 *         - waiting to lock <0x000000076b4f1830> (a java.lang.Object)
 *         - locked <0x000000076b4f1820> (a java.lang.Object)
 *         at com.noel.concurrent.deadlock.LeftRightDeadLock.lambda$main$0(LeftRightDeadLock.java:41)
 *         at com.noel.concurrent.deadlock.LeftRightDeadLock$$Lambda$1/1096979270.run(Unknown Source)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 *
 */
public class LeftRightDeadLock {

  private Object left = new Object();
  private Object right = new Object();

  public void leftRight() {
    synchronized (left) {
      System.out.println(Thread.currentThread().getName() + " has locked left resource");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      synchronized (right) {
        System.out.println(Thread.currentThread().getName() + " has locked right resource");
      }
    }
  }

  public void rightLeft() {
    synchronized (right) {
      System.out.println(Thread.currentThread().getName() + " has locked right resource");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      synchronized (left) {
        System.out.println(Thread.currentThread().getName() + " has locked left resource");
      }
    }
  }

  public static void main(String[] args) {

    LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();

    Thread lr = new Thread(() -> {
      leftRightDeadLock.leftRight();
    });

    Thread rl = new Thread(() -> {
      leftRightDeadLock.rightLeft();
    });

    lr.start();
    rl.start();


  }

}
