package com.noel.concurrent.threadlocal;

/**
 * <li>Tous les threads peuvent accéder à l'instance de type ThreadLocal : seul le thread qui a
 * ajouté une donnée en utilisant la méthode set() pourra obtenir cette variable qui lui est propre.
 * Si plusieurs threads invoquent la méthode set() sur l'unique instance de ThreadLocal, chacun
 * obtiendra la variable qu'il a passée en paramètre. Un thread ne peut pas obtenir la variable qui
 * est associé à un autre thread.
 * </li>
 * <p></p>
 * <li>
 * Un ThreadLocal ne peut encapsuler qu'une variable pour un thread. Si plusieurs variables doivent
 * être stockées pour un même thread, il faut déclarer une instance de type ThreadLocal pour chaque
 * variable ou encapsuler les différentes variables dans une classe.
 * </li>
 */
public class ThreadLocalSample {

  public static class MyRunnable implements Runnable {

    // conseillé de creer un threadlocal static
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal();

    @Override
    public void run() {
      threadLocal.set((int) (Math.random() * 100D));

//      try {
//        Thread.sleep(2000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }

      System.out.println(
          "Le thread '" + Thread.currentThread().getName() + "' a récupéré : '" + threadLocal.get()
              + "' du MEME threadLocal : " + threadLocal);
    }
  }


  public static void main(String[] args) throws InterruptedException {
    MyRunnable sharedRunnableInstance = new MyRunnable();

    Thread thread1 = new Thread(sharedRunnableInstance);
    Thread thread2 = new Thread(sharedRunnableInstance);

    thread1.start();
    thread2.start();
//
//    thread1.join(); //wait for thread 1 to terminate
//    thread2.join(); //wait for thread 2 to terminate
  }

}
