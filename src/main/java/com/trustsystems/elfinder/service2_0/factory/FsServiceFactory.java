package com.trustsystems.elfinder.service2_0.factory;


import com.trustsystems.elfinder.service2_0.service.FsService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface FsServiceFactory {
    FsService getFileService(HttpServletRequest request, ServletContext servletContext);
}

