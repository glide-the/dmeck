package com.trustsystems.elfinder.controller.executor.impl;

import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.exception.ErrorException;
import com.trustsystems.elfinder.service2_0.service.FsService;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class MissingCommandExecutor extends AbstractorJsonCommandExecutor implements CommandExecutor {
    @Override
    protected void execute(FsService fsService, HttpServletRequest request, ServletContext servletContext, JSONObject json) throws Exception {
        String cmd = request.getParameter("cmd");
        throw new ErrorException("没有这个指令类型",cmd);
    }
}
