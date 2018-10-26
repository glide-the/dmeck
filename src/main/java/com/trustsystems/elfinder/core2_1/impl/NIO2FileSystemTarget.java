
package com.trustsystems.elfinder.core2_1.impl;


import com.trustsystems.elfinder.core2_1.Target;
import com.trustsystems.elfinder.core2_1.Volume;

import java.nio.file.Path;

public class NIO2FileSystemTarget implements Target {

    private final Path path;
    private final Volume volume ;

    public NIO2FileSystemTarget(NIO2FileSystemVolume volume, Path path) {
        this.path = path;
        this.volume = volume;
    }


    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path.toAbsolutePath().toString();
    }

    @Override
    public Volume getVolume() {
        return null;
    }
}
