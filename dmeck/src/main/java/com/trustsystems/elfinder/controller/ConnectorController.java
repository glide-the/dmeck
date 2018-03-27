package com.trustsystems.elfinder.controller;

import com.trustsystems.elfinder.controller.executor.CommandExectionContext;
import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.controller.executor.CommandExecutorFactory;
import com.trustsystems.elfinder.exception.FsException;
import com.trustsystems.elfinder.service2_0.factory.FsServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("connector")
public class ConnectorController {

    @Resource(name = "commandExecutorFactory")
    private CommandExecutorFactory commandExecutorFactory;

    @Resource(name ="fsServiceFactory")
    private FsServiceFactory fsServiceFactory;

    @RequestMapping
    public void connector(HttpServletRequest request,
                          final HttpServletResponse response)throws IOException{
        /**
         * 文件上传暂时不做
         */
        String cmd = request.getParameter("cmd");
        CommandExecutor executor = commandExecutorFactory.get(cmd);
        if(executor==null){
            throw new FsException(String.format("unknown command: %s", cmd));
        }

        try {
            final HttpServletRequest finalRequest = request;
                executor.execute(new CommandExectionContext() {
                    /**
                     * 总是返回一个factory
                     * @return
                     */
                    @Override
                    public FsServiceFactory getFsServiceFactory() {
                        return fsServiceFactory;
                    }

                    @Override
                    public HttpServletRequest getRequest() {
                        return finalRequest;
                    }

                    @Override
                    public HttpServletResponse getResponse() {
                        return response;
                    }

                    @Override
                    public ServletContext getServletContext() {
                    return finalRequest.getSession().getServletContext();
                }
                 });
        } catch (Exception e) {
            throw new FsException("unknown error", e);
        }

    }

    public CommandExecutorFactory getCommandExecutorFactory() {
        return commandExecutorFactory;
    }

    public void setCommandExecutorFactory(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public FsServiceFactory getFsServiceFactory() {
        return fsServiceFactory;
    }

    public void setFsServiceFactory(FsServiceFactory fsServiceFactory) {
        this.fsServiceFactory = fsServiceFactory;
    }
}
