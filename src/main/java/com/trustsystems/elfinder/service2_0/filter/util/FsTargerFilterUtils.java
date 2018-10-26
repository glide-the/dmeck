package com.trustsystems.elfinder.service2_0.filter.util;

import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.service2_0.filter.TargetFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FsTargerFilterUtils {

    public static TargetFilter FILTER_ALL =  new TargetFilter() {
        @Override
        public boolean accepts(TargetEx target) {
            return true;
        }
    };

    /**
     *
     * 判断是否是文件夹
     */
    public static TargetFilter FILTER_FOLDER= new TargetFilter() {
        @Override
        public boolean accepts(TargetEx target) {
            return target.isFolder();
        }
    };

    /**
     * 当且仅当传入字符串包含指定的 char 值序列时，返回 true。
     * @param keyword
     * @return
     */
    public static TargetFilter createFileNameKeywordFilter(final String keyword){
        return new TargetFilter() {
            @Override
            public boolean accepts(TargetEx target) {
                return target.getName().contains(keyword);
            }
        };
    }

    /**
     * 返回一个TargetEX数组
     * @param sourceFiles
     * @param filter
     * @return
     */
    public static TargetEx[] filterFiles(TargetEx [] sourceFiles,
                                         TargetFilter filter) throws IOException {
        List<TargetEx> filtered = new ArrayList<TargetEx>();

        for (TargetEx file : sourceFiles) {
            if (filter.accepts(file))
                filtered.add(file);
        }

        return filtered.toArray(new TargetEx[0]);

    }

    public static TargetFilter createMimeFilter(final  String[] mimeFilters){

        if (mimeFilters == null || mimeFilters.length == 0){
            return FILTER_ALL;
        }

        return new TargetFilter() {
            @Override
            public boolean accepts(TargetEx target) throws IOException {

                    String mimetype = target.getMimeType().toUpperCase();


                for (String mimeFilter : mimeFilters) {
                    mimeFilter = mimeFilter.toUpperCase();
                    if (mimetype.startsWith(mimeFilter ="/") || mimetype.equals(mimeFilter)){
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
