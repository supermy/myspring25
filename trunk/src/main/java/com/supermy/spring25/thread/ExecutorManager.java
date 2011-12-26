package com.supermy.spring25.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {

	private static ExecutorManager instance;

	public synchronized static ExecutorManager getInstance() {
		if (instance == null) {
			instance = new ExecutorManager();
		}
		return instance;
	}

	protected ExecutorService threadPool;
	protected Map<String, List<RunnableWrapper>> waitingListByKey;

	private ExecutorManager() {
		// TODO read size from configure file
		int nThreads = 10;
		threadPool = Executors.newFixedThreadPool(nThreads);
		waitingListByKey = new HashMap<String, List<RunnableWrapper>>();
	}

	public void addTask(Runnable task) {
		addTask(task, null);
	}

	public void addTask(Runnable task, String key) {
		if (key == null) {
			threadPool.submit(task);
			return;
		}

		synchronized (waitingListByKey) {
			List<RunnableWrapper> waitingList = waitingListByKey.get(key);
			RunnableWrapper taskWrapper = new RunnableWrapper(task, key);
			if (waitingList == null) {
				waitingList = new ArrayList<RunnableWrapper>();
				waitingList.add(taskWrapper);
				waitingListByKey.put(key, waitingList);
				threadPool.submit(taskWrapper);
			} else {
				waitingList.add(taskWrapper);
			}
		}

	}

	private class RunnableWrapper implements Runnable {

		private Runnable task;
		private String key;

		public RunnableWrapper(Runnable task, String key) {
			super();
			this.task = task;
			this.key = key;
		}

		@Override
		public void run() {
			try {
				task.run();
			} finally {
				synchronized (waitingListByKey) {
					List<RunnableWrapper> waitingList = waitingListByKey
							.get(key);
					waitingList.remove(this);
					if (waitingList.size() == 0) {
						waitingListByKey.remove(key);
					} else {
						threadPool.submit(waitingList.get(0));
					}
				}
			}
		}
	}
}
