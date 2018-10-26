package com.trustsystems.elfinder.service2_0.Config.impl;

import com.trustsystems.elfinder.service2_0.Config.FsServiceConfig;

public class DefaultFsServiceConfig implements FsServiceConfig {

    private int tmbWidth;

    public void setTmbWidth(int tmbWidth) {
        this.tmbWidth = tmbWidth;
    }

    @Override
    public int getTmbWidth() {
        return tmbWidth;
    }
}
