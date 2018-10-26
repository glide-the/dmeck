package com.trustsystems.elfinder.service2_0.service.impl;

import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.core2_0.Volume;
import com.trustsystems.elfinder.service2_0.Checker.FsSecurityChecker;
import com.trustsystems.elfinder.service2_0.Config.FsServiceConfig;
import com.trustsystems.elfinder.service2_0.filter.TargetFilter;
import com.trustsystems.elfinder.service2_0.service.FsService;


import org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.util.*;

public class DefaultFsService implements FsService {

    FsSecurityChecker securityChecker;

    FsServiceConfig serviceConfig;

    Map<String,Volume> volumeMap = new HashMap<String,Volume>();

    String[][] escapes= { { "+", "_P" }, { "-", "_M" }, { "/", "_S" },
            { ".", "_D" }, { "=", "_E" } };

    /**
     * 一个递归迭代按名称模式查找文件
     * 查找文件不确定有io异常
     * @param filter
     * @return
     */
    @Override
    public TargetEx[] find(TargetFilter filter) throws IOException {

        List<TargetEx> results = new ArrayList<TargetEx>();
        for (Volume vol : volumeMap.values()) {
            Target root = vol.getRoot();
            results.addAll(findRecursively(filter,root));

        }
        return  results.toArray(new TargetEx[0]);


    }

    private Collection<TargetEx> findRecursively(TargetFilter filter, Target root) throws IOException {

        List<TargetEx> results  =new ArrayList<TargetEx>();
        Volume vol= root.getVolume();
        for (Target child : vol.listChildren(root)) {
            if (vol.isFolder(child)){
                results.addAll(findRecursively(filter,root));
            }
            else {
                TargetEx item = new TargetEx(child, this);
                if (filter.accepts(item)){
                    results.add(item);
                }
            }
        }
        return results;
    }

    public void setServiceConfig(FsServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Override
    public Target fromHash(String hash) throws IOException {

        for (Volume v : volumeMap.values()) {
            String prefix = getVolumeId(v)+"_";

            if (hash.equals(prefix)){
                return v.getRoot();
            }

            if (hash.startsWith(prefix)){
                String substring = hash.substring(prefix.length());

                for (String[] pair : escapes) {
                    substring = substring.replace(pair[1],pair[0]);
                }

                String s = new String(Base64.decodeBase64(substring));
                return  v.fromPath(s);
            }
        }
        return null;

    }


    @Override
    public String getHash(Target target) throws IOException {

        String relativePath = target.getVolume().getPath(target);
        String base = new String(Base64.encodeBase64(relativePath.getBytes()));

        for (String[] pair : escapes) {
            base = base.replace(pair[0],pair[1]);
        }

        return getVolumeId(target.getVolume())+"_"+base;

    }

    @Override
    public FsSecurityChecker getFsSecurityChecker() {
        return securityChecker;
    }
    public void setSecurityChecker(FsSecurityChecker securityChecker)
    {
        this.securityChecker = securityChecker;
    }
    @Override
    public String getVolumeId(Volume volume) {
        for (Map.Entry<String, Volume> en : volumeMap.entrySet()) {
            if (en.getValue() == volume){
                return en.getKey();
            }
        }
        return null;
    }

    @Override
    public Volume[] getVolumes() {
        return volumeMap.values().toArray(new Volume[0]);
    }


    /**
     * @deprecated {@link #setVolumeMap(Map)}
     * @param volumes
     *            The volumes available.
     * @throws IOException
     *             If there is a problem with using one of the volumes.
     */
    public void setVolumes(Volume[] volumes){
        char vid = 'A';
        for (Volume volume : volumes) {
            volumeMap.put(""+vid,volume);
            vid++;
        }
    }
    @Override
    public FsServiceConfig getServiceConfig() {
        return serviceConfig;
    }


    public Map<String, Volume> getVolumeMap() {
        return volumeMap;
    }

    public void setVolumeMap(Map<String, Volume> volumeMap) {

        for (Map.Entry<String, Volume> entry : volumeMap.entrySet()) {
            addVolume(entry.getKey(),entry.getValue());
        }
    }
    public void addVolume(String name,Volume volume){
        volumeMap.put(name,volume);
        /**
         * 日志没有
         */
    }
}
