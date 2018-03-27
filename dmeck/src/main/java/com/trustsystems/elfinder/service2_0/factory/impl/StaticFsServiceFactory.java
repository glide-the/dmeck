package com.trustsystems.elfinder.service2_0.factory.impl;

import com.trustsystems.elfinder.service2_0.factory.FsServiceFactory;
import com.trustsystems.elfinder.service2_0.service.FsService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 * 此方法总是返回一个FsService，不管是什么请求
 */
public class StaticFsServiceFactory implements FsServiceFactory {

    FsService fsService;

    @Override
    public FsService getFileService(HttpServletRequest request, ServletContext servletContext) {
        return fsService;
    }

    public FsService getFsService() {
        return fsService;
    }
    public void setFsService(FsService fsService){
        this.fsService = fsService;
    }
}
