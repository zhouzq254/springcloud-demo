package com.scnu.service.kafka;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class BlockedThreadPoolExecutor extends ThreadPoolExecutor {
    private Semaphore semaphore = null;

    public BlockedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, int watermark)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.semaphore = new Semaphore(watermark);
    }

    public BlockedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, int watermark)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.semaphore = new Semaphore(watermark);
    }

    protected void afterExecute(Runnable r, Throwable t)
    {
        this.semaphore.release();
        super.afterExecute(r, t);
    }

    public void execute(Runnable command)
    {
        try {
            this.semaphore.acquire();
            super.execute(command);
        } catch (InterruptedException e) {
            log.error("semaphore acquire error", e);
            throw new RuntimeException("semaphore acquire error", e);
        }
    }

    public void close()
    {
        shutdown();
        try {
            if (!awaitTermination(30L, TimeUnit.SECONDS))
            {
                shutdownNow();

                if (!awaitTermination(10L, TimeUnit.SECONDS))
                    log.error("ThreadPoolImpl did not terminate!");
            }
        }
        catch (InterruptedException ie)
        {
            shutdownNow();

            Thread.currentThread().interrupt();
        }
    }
}
