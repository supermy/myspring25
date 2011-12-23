package com.supermy.spring25.log;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 日志业务逻辑方法
 * 
 * @author jamesmo
 * @version 创建时间：2011-12-23 下午3:37:56
 * 
 */
@Service
public class Log2DB {
	private final Logger log = Logger.getLogger(Log2DB.class);

	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * 异步执行
	 * 
	 * @param userId
	 * @param time
	 */
	public void execAsyncAddUserLog(final String userId, final long time) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					addUserLog(userId, time);
					log.debug("用户日志记录成功！");
				} catch (Exception e) {
					log.debug("用户日志记录失败！，异常信息：" + e.getMessage());
				}
			}
		});
	}

	/**
	 * 增加用户日志
	 * 
	 * @param userName
	 * @param time
	 */
	public void addUserLog(String userName, long time) {
		System.out.println("this is add User log method! 花费时间：" + time + "毫秒");
	}

}
