package com.github.wolfie.jumppunch;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServerStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		Server.start();
	}

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
		Server.stop();
	}
}
