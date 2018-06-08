package com.noel.concurrent.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 */
public class CompletableFutureAsSimpleFuture {

  public static Future<String> calculateAsync() throws InterruptedException {

    CompletableFuture<String> completableFuture = new CompletableFuture<>();

    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.submit(() -> {

      Thread.sleep(500);

      completableFuture.complete("Hello");

      return null;
    });

    return completableFuture;
  }


  public static void main(String[] args) {

    try {
      Future<String> result = calculateAsync();
      System.out.println("Result = " + result.get());
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}