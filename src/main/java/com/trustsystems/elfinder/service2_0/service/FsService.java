package com.trustsystems.elfinder.service2_0.service;

import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.core2_0.Volume;
import com.trustsystems.elfinder.service2_0.Checker.FsSecurityChecker;
import com.trustsystems.elfinder.service2_0.Config.FsServiceConfig;
import com.trustsystems.elfinder.service2_0.filter.TargetFilter;

import java.io.IOException;

/**
 * 封装NIO
 */
public interface FsService {
    Target fromHash(String hash) throws IOException;

    String getHash(Target target)throws IOException;

    FsSecurityChecker getFsSecurityChecker();

    String getVolumeId(Volume volume);

    Volume[] getVolumes();

    /* * 暂时不需要此接口方法，获取config*/

    FsServiceConfig getServiceConfig();

    /* * 全局文件查找,此方法暂时为实现
     */
    TargetEx[] find(TargetFilter filter) throws IOException;
}
