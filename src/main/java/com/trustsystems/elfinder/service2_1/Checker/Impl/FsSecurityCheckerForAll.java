package com.trustsystems.elfinder.service2_1.Checker.Impl;

import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.service2_0.service.FsService;
import com.trustsystems.elfinder.service2_1.Checker.FsSecurityChecker;

import java.io.IOException;

public class FsSecurityCheckerForAll implements FsSecurityChecker {

    boolean locked = false;

    boolean readable = true;

    boolean weitable = true;


    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isWeitable() {
        return weitable;
    }

    public void setWeitable(boolean weitable) {
        this.weitable = weitable;
    }


    @Override
    public boolean isLocked(FsService fsService, Target target) throws IOException {
        return locked;
    }

    @Override
    public boolean isReadable(FsService fsService, Target target) throws IOException {
        return readable;
    }

    @Override
    public boolean isWritable(FsService fsService, Target target) throws IOException {
        return weitable;
    }
}
