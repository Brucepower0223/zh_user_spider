import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by fangjin on 2017/7/1.
 */
public class ThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
/*        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "------------");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        pool.execute(t1);*/
      /*  for (int i = 0; i < 5; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName() + "------------");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        Thread.sleep(5000);*/

        for (int i = 0; i < 15; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){

                    }
                }
            });
            pool.execute(t);
            System.out.println();
        }


        System.out.println("active count" + ((ThreadPoolExecutor) pool).getActiveCount());
    }

    /**
     * newCacheThreadExecutor---线程池长度无限大
     * newFixedThreadExecutor----定长的线程池
     * newScheduledThreadExecutor---定时周期任务执行
     * newSimpleThreadExecutors---保证所有的任务按照FIFO,LIFO以及优先级执行
     */
    @Test
    public void threadPoolTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println(Thread.currentThread().getName() + "+++");
                    }
                }
            });
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testBlockingQuene() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("-----");
                }
            }
        }).start();


    }


}
