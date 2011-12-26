package com.supermy.spring25.thread;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExecutorManagerTest {

	private String resultString;

	@Before
	public void setUp() throws Exception {
		resultString = "";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddTaskRunnable() {
		ExecutorManager manager = ExecutorManager.getInstance();
		manager.threadPool = Executors.newFixedThreadPool(2);

		manager.addTask(new MyTask("AA"));
		manager.addTask(new MyTask("AA"));
		manager.addTask(new MyTask("BB"));

		manager.threadPool.shutdown();
		while (!manager.threadPool.isTerminated()) {
		}

		assertEquals("AAAABB", resultString);
	}

	@Test
	public void testAddTaskRunnableKey() throws InterruptedException {
		ExecutorManager manager = ExecutorManager.getInstance();
		manager.threadPool = Executors.newFixedThreadPool(2);

		manager.addTask(new MyTask("AA"), "KEY1");
		manager.addTask(new MyTask("AA"), "KEY1");
		manager.addTask(new MyTask("BB"), "KEY2");
		manager.addTask(new MyTask("CC"), "KEY1");

		Thread.sleep(10000);

		manager.threadPool.shutdown();
		while (!manager.threadPool.isTerminated()) {
		}

		assertEquals("AABBAACC", resultString);
	}

	class MyTask implements Runnable {

		private String msg;

		public MyTask(String msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resultString += msg;
		}

	}
}