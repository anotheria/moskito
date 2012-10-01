package net.anotheria.moskitodemo;

import java.util.Random;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class JustTest extends NotificationBroadcasterSupport implements JustTestMBean{

	private Random rnd = new Random(System.currentTimeMillis());
	
	@Override
	public int getRequestCount() {
		return rnd.nextInt(100);
	}

	@Override
	public int getRequestCount(String intName) {
		if (intName.equals("not"))
			sendNotification(new Notification("JustTest", this, rnd.nextLong(), "test...."));
		return rnd.nextInt(100);
	}
	
}
