package com.trustsystems.elfinder.service2_1.Checker;

import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.service2_0.service.FsService;

import java.io.IOException;

public interface FsSecurityChecker {
    boolean isLocked(FsService fsService, Target target)throws IOException;

    boolean isReadable(FsService fsService, Target target) throws  IOException;

    boolean isWritable(FsService fsService, Target target) throws  IOException;
}
