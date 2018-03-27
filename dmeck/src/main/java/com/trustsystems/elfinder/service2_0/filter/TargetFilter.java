package com.trustsystems.elfinder.service2_0.filter;

import com.trustsystems.elfinder.controller.TargetEx;

import java.io.IOException;

/*
    过滤当前工作没空间接口*/
public interface TargetFilter {
    public  boolean accepts(TargetEx target) throws IOException;
}
