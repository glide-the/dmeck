package com.trustsystems.elfinder.service2_0.filter.util;

import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.service2_0.service.FsService;

import java.io.IOException;

public class FsServiceUtils {

    public  static TargetEx findTarget(FsService fsService , String hash) throws IOException {
        Target target = fsService.fromHash(hash);

        if (target == null){
            return null;
        }

        return new TargetEx(target,fsService);
    }

}
