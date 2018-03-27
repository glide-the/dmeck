package com.trustsystems.elfinder.controller;

import com.trustsystems.elfinder.configuration.ElFinderConstants;
import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.core2_0.Volume;
import com.trustsystems.elfinder.service2_0.service.FsService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * TargetEX是Target的帮助类，包装了taoget和FsService，Volume
 */
public class TargetEx {
    private final Target target;

    private final FsService fsService;

    private final Volume volume;

    public TargetEx(Target target, FsService fsService)
    {
        this.target = target;
        this.fsService = fsService;
        this.volume = target.getVolume();
    }
    public TargetEx(TargetEx parent,String name)throws IOException
    {
        this.volume= parent.volume;
        this.fsService= parent.fsService;


        //目录可能已经有了"/",需要检查有没有多出的
        String path =   volume.getPath(parent.target);
            if (path != null)
            {
                if (!path.endsWith(ElFinderConstants.ELFINDER_PARAMETER_FILE_SEPARATOR))
                {
                    path = path +  ElFinderConstants.ELFINDER_PARAMETER_FILE_SEPARATOR;
                }
                path = path + name;
            }
            else
            {
                path = name;
            }
        this.target= volume.fromPath(path);
    }

    public TargetEx createChild(String name) throws IOException
    {
        return new TargetEx(this, name);
    }

    public void createFile() throws IOException
    {
        volume.createFile(target);
    }

    public void createFolder() throws IOException {
        volume.createFolder(target);
    }

    public void delete() throws IOException {

        if (volume.isFolder(target)) {

            volume.deleteFolder(target);

        } else {
            volume.deleteFile(target);
        }
    }

    public void deleteFile() throws IOException
    {
        volume.deleteFile(target);
    }

    public void deleteFolder() throws IOException
    {
        volume.deleteFolder(target);
    }

    public boolean exists() {
        return volume.exists(target);
    }

    public String getHash() throws IOException {
        return fsService.getHash(target);
    }

    public long getLastModified() throws IOException {
        return volume.getLastModified(target);
    }

    public String getMimeType() throws IOException {
        return volume.getMimeType(target);
    }

    public String getName() {
        return volume.getName(target);
    }

    public TargetEx getParent() {
        return new TargetEx(volume.getParent(target), fsService);
    }

    public String getPath () throws  IOException
    {
        return volume.getPath(target);
    }

    public long getSize() throws IOException {
        return volume.getSize(target);
    }

    public String getVolumeId() {
        return fsService.getVolumeId(volume);
    }

    /**
     * 此处实现不同需要修，元方法中没有传入target类
     * @return
     */
    public String getVolumnName(){
        return volume.getName(target);
    }

    /**
     * 测试给定目录有没有子目录
     * @return
     * @throws IOException
     */
    public boolean hasChildFolder() throws IOException {
        return volume.hasChildFolder(target);
    }

    public boolean isFolder() {
        return volume.isFolder(target);
    }


    public boolean isRoot() throws IOException {
        return volume.isRoot(target);
    }

    public List<TargetEx> listChildren() throws IOException {
        List<TargetEx> list = new ArrayList<>();
        for (Target child : volume.listChildren(target)) {
            list.add(new TargetEx(child, fsService));
        }
        return list;
    }

    public InputStream openInputStream() throws IOException {
        return volume.openInputStream(target);
    }

    public OutputStream openOutputStream() throws IOException {
        return volume.openOutputStream(target);
    }


    public void renameTo(TargetEx dst) throws IOException {
        volume.rename(target, dst.target);
    }

    public void writeStream(InputStream is) throws IOException
    {
        volume.writeStream(target, is);
    }


    public String getVolumeAlias() {
        return volume.getAlias();
    }



    /**
     * 权限相关
     * @param targetes
     * @return
     * @throws IOException
     */
    public boolean isLocked(TargetEx targetes) throws IOException {
        return this.fsService.getFsSecurityChecker().isLocked(fsService,target);
    }

    public boolean isReadable(TargetEx targetes) throws IOException {
        return this.fsService.getFsSecurityChecker().isReadable(fsService,target);
    }

    public boolean isWritable(TargetEx targetes) throws IOException {
        return this.fsService.getFsSecurityChecker().isWritable(fsService,target);
    }
    //权限相关结束


}
