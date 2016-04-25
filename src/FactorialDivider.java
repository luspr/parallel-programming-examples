/**
 * 
 */

/**
 * @author Lukas Spranger
 *
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FactorialDivider {
  
  class factorialCaller implements Callable<Long> {
    int n;
    
    public factorialCaller(int n) {
      super();
      this.n = n;
    }


    @Override
    public Long call() {
      System.err.println("Hey, i'm in Thread " + Thread.currentThread());
      long res = 1;
      for (int i = 1; i <= n; i++) {
        res *= i;
      }
      return new Long(res);
    }
  }
  
  public long divideFactorials(int n, int m) {
    // create executor service 
    ExecutorService ex = Executors.newFixedThreadPool(2);
    // submit the tasks. 
    Future<Long> f1 = ex.submit(new factorialCaller(n));
    Future<Long> f2 = ex.submit(new factorialCaller(m));
    
    // wait for the results.
    long numerator = 1;
    try {
      numerator = f1.get(15, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
    }
    long denominator = 1;
    try {
      denominator = f2.get(15, TimeUnit.SECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
    }
  
    ex.shutdown();
    
    return numerator / denominator;
  }
  
}
