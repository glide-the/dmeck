package com.trustsystems.elfinder.servlet;

import com.trustsystems.elfinder.controller.ConnectorController;
import com.trustsystems.elfinder.controller.executor.CommandExecutorFactory;
import com.trustsystems.elfinder.controller.executor.impl.DefaultCommandExecutorFactory;
import com.trustsystems.elfinder.controller.executor.impl.MissingCommandExecutor;
import com.trustsystems.elfinder.core2_0.impl.LocalSystemVolume;
import com.trustsystems.elfinder.service2_0.Checker.Impl.FsSecurityCheckerForAll;
import com.trustsystems.elfinder.service2_0.Config.impl.DefaultFsServiceConfig;
import com.trustsystems.elfinder.service2_0.factory.FsServiceFactory;
import com.trustsystems.elfinder.service2_0.factory.impl.StaticFsServiceFactory;
import com.trustsystems.elfinder.service2_0.service.impl.DefaultFsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ConnectorServlet is an example servlet
 * it creates a ConnectorController on init() and use it to handle requests on doGet()/doPost()
 * 
 * users should extend from this servlet and customize required protected methods
 * 
 * @author bluejoe
 *
 */
public class ConnectorServlet extends HttpServlet
{
	// core member of this Servlet
	ConnectorController _connectorController;

	/**
	 * create a command executor factory
	 * 
	 * @param config
	 * @return
	 */
	protected CommandExecutorFactory createCommandExecutorFactory(
			ServletConfig config)
	{
		DefaultCommandExecutorFactory defaultCommandExecutorFactory = new DefaultCommandExecutorFactory();
		defaultCommandExecutorFactory
				.set_classNamePattern("com.trustsystems.elfinder.controller.executors.%sCommandExecutor");
		defaultCommandExecutorFactory
				.set_fallbackCommand(new MissingCommandExecutor());
		return defaultCommandExecutorFactory;
	}

	/**
	 * create a connector controller
	 * 创建连接器控制器
	 * 初始化Controller控制器
	 * 此方法不是spring实现
	 * @param config
	 * @return
	 */
	protected ConnectorController createConnectorController(ServletConfig config)
	{
		ConnectorController connectorController = new ConnectorController();

		connectorController
				.setCommandExecutorFactory(createCommandExecutorFactory(config));
		connectorController.setFsServiceFactory(createServiceFactory(config));

		return connectorController;
	}

	/**
	 * 初始化DefaultFsService对象，让FsService接口有自己的行为
	 * @return
	 */
	protected DefaultFsService createFsService()
	{
		DefaultFsService fsService = new DefaultFsService();
		fsService.setSecurityChecker(new FsSecurityCheckerForAll());

		DefaultFsServiceConfig serviceConfig = new DefaultFsServiceConfig();
		serviceConfig.setTmbWidth(80);

		fsService.setServiceConfig(serviceConfig);

		fsService.addVolume("A",
				createLocalFsVolume("My Files", Paths.get(System.getProperty("user.home"), "aaaa")));
		fsService.addVolume("B",
				createLocalFsVolume("Shared",  Paths.get(System.getProperty("user.home"), "bbbb")));

		return fsService;
	}

	private LocalSystemVolume createLocalFsVolume(String name, Path rootDir)
	{
		LocalSystemVolume localFsVolume = new LocalSystemVolume(name,rootDir);
		return localFsVolume;
	}

	/**
	 * create a service factory
	 * 
	 * @param config
	 * @return
	 */
	protected FsServiceFactory createServiceFactory(ServletConfig config)
	{
		/**
		 * 总是返回一个FsService对象
		 */
		StaticFsServiceFactory staticFsServiceFactory = new StaticFsServiceFactory();

		DefaultFsService fsService = createFsService();

		staticFsServiceFactory.setFsService(fsService);
		return staticFsServiceFactory;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		_connectorController.connector(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		_connectorController.connector(req, resp);
	}

	/**
	 * servlet初始化ServletConfig配置
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		_connectorController = createConnectorController(config);
	}
}
