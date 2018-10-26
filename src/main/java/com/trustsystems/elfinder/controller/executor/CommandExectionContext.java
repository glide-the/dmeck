package com.trustsystems.elfinder.controller.executor;

import com.trustsystems.elfinder.service2_0.factory.FsServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandExectionContext
{
    FsServiceFactory getFsServiceFactory();

    HttpServletRequest getRequest();

    HttpServletResponse getResponse();

    ServletContext getServletContext();
}
