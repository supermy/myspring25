package com.supermy.spring25;

/**
 * 每个 Thread 都保存一个自己的 Map，key 就是 ThreadLocal 变量，而 value 就是在对应 Thread 运行语句
 * threadLocal.set(object) 时放进去的那个 object ！！所以，即使 ThreadLocal 变量是同一个，但因为在不同的
 * Thread 里面访问的是不同的 Map，所以 threadLocal.get() 总返回不同的object！！
 * 
 * @author jamesmo
 * @version 创建时间：2011-12-23 下午11:02:06
 * 
 */
public class ThreadLocalTest {
	private static final ThreadLocal<Object> session = new ThreadLocal<Object>();

	private static class TestThread implements Runnable {
		private ThreadLocal<Object> sessionInThread;

		public TestThread(ThreadLocal<Object> session) {
			sessionInThread = session;
		}

		public void run() {
			sessionInThread.set(new Object());
			showInfo();
			showInfo(); // Show again, for verification.
		}

		private void showInfo() {
			try {
				Thread.sleep(300);
			} catch (InterruptedException ie) {
				return;
			}
			System.out.println(Thread.currentThread().getName()
					+ ":ThreadLocal object passed in:" + sessionInThread
					+ ",ThreadLocal object content: " + sessionInThread.get());
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 6; i++) {
			new Thread(new TestThread(session), "thread_" + i).start();
		}
	}
}