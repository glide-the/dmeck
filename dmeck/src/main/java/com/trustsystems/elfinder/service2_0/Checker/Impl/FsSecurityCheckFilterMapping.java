package com.trustsystems.elfinder.service2_0.Checker.Impl;

import com.trustsystems.elfinder.service2_0.Checker.FsSecurityChecker;

import java.util.regex.Pattern;

public class FsSecurityCheckFilterMapping {

    FsSecurityChecker checker;

    String pattern;

    /**
     * 匹配hash
     * @param hash
     * @return
     */
    public boolean matches(String hash){
       return Pattern.compile(pattern).matcher(hash).matches();
    }

    public FsSecurityChecker getChecker() {
        return checker;
    }

    public void setChecker(FsSecurityChecker checker) {
        this.checker = checker;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
