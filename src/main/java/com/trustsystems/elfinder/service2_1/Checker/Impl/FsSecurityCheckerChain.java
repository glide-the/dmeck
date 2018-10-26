package com.trustsystems.elfinder.service2_1.Checker.Impl;

import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.service2_0.service.FsService;
import com.trustsystems.elfinder.service2_1.Checker.FsSecurityChecker;

import java.io.IOException;
import java.util.List;

public class FsSecurityCheckerChain implements FsSecurityChecker {

    public static final FsSecurityChecker DEFAULT_SECURITY_CHECKER = new FsSecurityCheckerForAll();

    List<FsSecurityCheckFilterMapping> filterMappings;
    private FsSecurityChecker getChecher(FsService fsService, Target target) throws  IOException{
        String hash = fsService.getHash(target);
        for (FsSecurityCheckFilterMapping filterMapping : filterMappings) {

            if (filterMapping.matches(hash)){

                return filterMapping.getChecker();
            }
        }
        return  DEFAULT_SECURITY_CHECKER;
    }

    public List<FsSecurityCheckFilterMapping> getFilterMappings() {
        return filterMappings;
    }

    public void setFilterMappings(List<FsSecurityCheckFilterMapping> filterMappings) {
        this.filterMappings = filterMappings;
    }

    @Override
    public boolean isLocked(FsService fsService, Target target) throws IOException {
        return getChecher(fsService ,target).isLocked(fsService,target);
    }

    @Override
    public boolean isReadable(FsService fsService, Target target) throws IOException {
        return getChecher(fsService,target).isReadable(fsService,target);
    }

    @Override
    public boolean isWritable(FsService fsService, Target target) throws IOException {
        return getChecher(fsService,target).isWritable(fsService,target);
    }
}
