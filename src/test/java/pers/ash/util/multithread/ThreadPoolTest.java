package pers.ash.util.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.junit.Test;

public class ThreadPoolTest {

	/**
	 * 单线程池，线程池中只有一个线程，如果线程意外终止，就新建一个线程执行任务，任何时间线程池中只会有一个线程
	 * 下面创建了10个线程，只有1个在执行
	 * @throws InterruptedException 
	 */
	@Test
	public void testSingleThread() throws InterruptedException{
		ExecutorService pool = Executors.newSingleThreadExecutor();
		for(int i = 0; i < 10; i++){
			Timer timer = new Timer();
			//将线程放进线程池
			pool.execute(timer);
		}
		Thread.sleep(20000);
		pool.shutdown();
	}
	
	/**
	 * 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
	 * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
	 * 以下创建了10个线程，只有5个在执行
	 * @throws InterruptedException 
	 */
	@Test
	public void testFixedThreadPool() throws InterruptedException{
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int i = 0; i < 10; i++){
			Thread timer = new Thread(new Timer());
			//将线程放进线程池
			pool.execute(timer);
		}
		Thread.sleep(60000);
		pool.shutdown();
	}
	
	/**
	 * 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
	 * 那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
	 * 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
	 * @throws InterruptedException 
	 */
	@Test
	public void testCachedThreadPool() throws InterruptedException{
		ExecutorService pool = Executors.newCachedThreadPool();
		for(int i = 0; i < 10; i++){
			Timer timer = new Timer();
			//将线程放进线程池
			pool.execute(timer);
		}
		Thread.sleep(20000);
		pool.shutdown();
	}
	
	/**
	 * 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
	 * 下面创建了10个线程，但只有两个线程在执行
	 * @throws InterruptedException 
	 */
	@Test
	public void testScheduledThreadPool() throws InterruptedException{
		ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2);
		for(int i = 0; i < 10; i++){
			Timer timer = new Timer();
			//每5秒执行一次
			pool.scheduleAtFixedRate(timer, 1000, 5000, TimeUnit.MILLISECONDS);
		}
		Thread.sleep(20000);
		pool.shutdown();
	}
	
	@Test
	public void testTimer(){
		Thread timer = new Thread(new Timer("计时器1"));
		Thread timer2 = new Thread(new Timer("计时器2"));
		timer.start();
		timer2.start();
		try {
			timer.join();
			timer2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testMultiThread(){
		TestRunnable runner = new TestRunnable(){

			@Override
			public void runTest() throws Throwable {
				Timer timer = new Timer();
				timer.run();
			}
		};
		TestRunnable[] runners = new TestRunnable[5];
		for(int i = 0; i < 5; i++){
			runners[i] = runner;
		}
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(runners);
		try {
			mttr.runTestRunnables();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
