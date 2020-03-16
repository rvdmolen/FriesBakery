package nl.molen.belgiumfries.baker.recipe;

import lombok.extern.java.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@Log
public class CompletableFutureTests {

    /*
      Futures can Run as
      1.   supplyAsync     => supplyAsync has return value
      2.   runAsync        => runAsync has no return values while supplyAsync has
     */

    @Test
    @DisplayName("Test supplyAsync")
    void supplyAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture
            .supplyAsync(() -> "Hello");
        assertEquals("Hello", future.get());
    }

    @Test
    @DisplayName("Test runAsync")
    void runAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<?> future = CompletableFuture
            .runAsync(() -> log.info("runAsync has no return values"));
        assertNull(future.get()); // always null
    }

    @Test
    @DisplayName("Chain CompletableFuture with runAsync")
    void runAsyncWithCallbacks() throws ExecutionException, InterruptedException {
        // You can attach thenRun* to the runAsync callbacks chain
        CompletableFuture<?> future = CompletableFuture
            .runAsync(() -> log.info("runAsync"))
            .thenRunAsync(() -> log.info("callback"));

        assertNull(future.get());
    }

    @Test
    @DisplayName("Chain CompletableFuture with supplyAsync")
    void supplyAsyncWithCallbacks() throws ExecutionException, InterruptedException {
        // You can attach thenApply*, thenCombine*, thenCompose*, thenAccept* and thenRun* to the supplyAsync callbacks chain
        CompletableFuture<?> future = computeSomething()
            .thenApply(s -> s + " callback 1")
            .thenApplyAsync((s) -> s + " callback 2");

        assertEquals("Hello callback 1 callback 2", future.get());
    }

    /*
        thenApply
        The result is a transformation (like map), you don't need to return a new completable future
     */
    @Test
    @DisplayName("CompletableFuture with thenApply")
    void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = computeSomething();

        CompletableFuture<String> future = completableFuture
            .thenApply(s -> 1)
            .thenApply(s -> "new value" + s.toString());

        assertEquals("new value1", future.get());
    }

    /*
        thenCompose
        The result is flatMappened, you have to return a new completable future
        the next chain will be the flatMapped result of the future
     */
    @Test
    @DisplayName("CompletableFuture with thenCompose")
    void thenCompose() throws ExecutionException, InterruptedException {

        // with thenCompose the result is flattened
        CompletableFuture<Boolean> future1 =
            computeSomething()
                .whenComplete((a, b) -> log.info(a))
                .thenCompose(s -> computeInteger())
                .thenCompose(this::computeInteger)
                .thenCompose(s -> computeBoolean(10))
                .thenCompose(s -> computeBoolean());

        // with thenApply it is a nested Future
        CompletableFuture<CompletableFuture<Boolean>> future2 =
            computeSomething()
                .thenCompose(s -> computeInteger())
                .thenApply(this::computeBoolean);

        assertEquals(false, future1.get());
        assertEquals(false, future2.get().get());
    }


    /*
        thenAccept
§       - do something at the end of the chain without returning any result, and 1 parameter of the value
     */
    @Test
    @DisplayName("CompletableFuture with thenAccept")
    void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture
            .supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
            .thenAccept((val) -> log.info("Computation finished: " + val));

        future.get();
        assertNull(future.get()); // always null
    }

    /*
        thenRun
        - do something at the end of the chain without returning any result, and no parameter
     */
    @Test
    @DisplayName("CompletableFuture with thenRun")
    void thenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture
            .supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
            .thenRun(() -> log.info("Computation finished."));

        future.get();
        assertNull(future.get()); // always null
    }

    /*
       thenCombine
       - combine 2 completable futures and handle result into new completable future
    */
    @Test
    @DisplayName("CompletableFuture with thenCombine")
    void thenCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture
            .supplyAsync(() -> "First Value");

        CompletableFuture<String> future = completableFuture
            .thenCombine(computeSomething(), (future1, future2) -> String.format("%s %s", future1, future2));

        assertEquals("First Value Hello", future.get());
    }


     /*
        Futures apply vs ApplyAsync

        f.e.ThenApply vs ThenApplyAsync

        The difference has to do with the Executor that is responsible for running the code. Each operator on CompletableFuture generally has 3 versions.

        1. thenApply(fn) - runs fn on a thread defined by the CompleteableFuture on which it is called, so you generally cannot know where this will be executed. It might immediately execute if the result is already available.
        2. thenApplyAsync(fn) - runs fn on a environment-defined executor regardless of circumstances. For CompletableFuture this will generally be ForkJoinPool.commonPool().
        3. thenApplyAsync(fn,exec) - runs fn on exec.

        In the end the result is the same, but the scheduling behavior depends on the choice of method.
     */


    @Test
    @DisplayName("Test chaining Completable Futures")
    void chainFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = computeSomething();

        CompletableFuture<String> future = completableFuture
            .thenApply(s -> s + " World");

        assertEquals("Hello World", future.get());
    }


    @Test
    @DisplayName("CompletableFuture with whenComplete")
    void whenComplete() throws ExecutionException, InterruptedException {
        // with whenComplete check the result and if there is a exception
        CompletableFuture<Integer> future1 =
            computeSomething()
                .whenComplete((input, exception) -> log.info(input))
                .thenCompose(s -> computeInteger());

        assertEquals("42", future1.get().toString());
    }


    @Test
    @DisplayName("Test exceptions from CompletableFuture")
    void testExceptions() {
        int i = 0;

        CompletableFuture<Void> result = computeDivision(i)
            .whenComplete((input, exception) -> {
                if (exception != null) {
                    log.info("exception occurs");
                } else {
                    log.info("no exception, got result: " + input);
                }
            })
            .exceptionally(throwable ->
            {
                log.info("recovering in exceptionally: " + throwable);
                return 1;
            })
            .thenApply(input -> input * 3)
            .thenApply(Object::toString)
            .thenAccept(log::info);
    }

    private CompletableFuture<String> computeSomething() {
        return CompletableFuture.supplyAsync(() -> "Hello");
    }

    private CompletableFuture<Integer> computeInteger() {
        return CompletableFuture.supplyAsync(() -> 42);
    }

    private CompletableFuture<Boolean> computeBoolean() {
        return CompletableFuture.supplyAsync(() -> false);
    }

    private CompletableFuture<Boolean> computeBoolean(Integer i) {
        return CompletableFuture.supplyAsync(() -> i > 100000);
    }

    private CompletableFuture<Integer> computeInteger(int i) {
        return CompletableFuture.supplyAsync(() -> 42 + i);
    }

    private CompletableFuture<Integer> computeDivision(int i) {
        return CompletableFuture.supplyAsync(() -> 16 / i);
    }
}
