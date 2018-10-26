package com.trustsystems.elfinder.controller.executor.impl;

import com.trustsystems.elfinder.configuration.ElFinderConstants;
import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.controller.executor.CommandExectionContext;
import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.core2_0.Target;
import com.trustsystems.elfinder.service2_0.filter.util.FsServiceUtils;
import com.trustsystems.elfinder.service2_0.service.FsService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 抽象工厂
 */
public abstract class AbstractCommandExecutor implements CommandExecutor {


    private static final String CMD_TMB_TARGET = "?cmd=tmb&target=%s";
    private Map<String, Object> map = new HashMap<>();

    @Override
    public void execute(CommandExectionContext ctx) throws Exception {
        FsService fileService = ctx.getFsServiceFactory().getFileService(ctx.getRequest(),ctx.getServletContext());

        execute(fileService,ctx.getRequest(),ctx.getResponse(),ctx.getServletContext());
    }

    public abstract void execute(FsService fileService, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)throws  Exception;


    /**
     * 不同的实现方法
     * @param map
     * @param target
     * @throws IOException
     */
    protected void addSubFolders(Map<String, TargetEx> map, TargetEx target) throws IOException {
        for (TargetEx f : target.listChildren()) {
            if (f.isFolder()) {
                map.put(f.getHash(), f);
                addSubFolders(map, f);
            }
        }
    }

    /**
     * 插入子类的数据
     * @param map
     * @param target
     * @throws IOException
     */
    protected void addChildren(Map<String,TargetEx> map,TargetEx target) throws IOException {
        for (TargetEx targetEx : target.listChildren()) {
            map.put(targetEx.getHash(),targetEx);
        }
    }
    /**
     * 返回当前target工作空间
     * @param fsService
     * @param target
     * @return
     */
    protected  TargetEx finderCwd(FsService fsService,String target) throws IOException {
        TargetEx cwd= null;
        if (target!=null){
            cwd = findTarget(fsService, target);
        }

        if (cwd==null){
            cwd= new TargetEx(fsService.getVolumes()[0].getRoot(),fsService);
        }
        return cwd;
    }

    protected TargetEx findTarget(FsService fsService,String hash) throws IOException {
        return FsServiceUtils.findTarget(fsService, hash);
    }



    protected Object[] buildJsonFilesArray(HttpServletRequest request, Collection<TargetEx> list) throws IOException {
        List<Map<String,Object>> jsonFilelist = new ArrayList<>();
        for (TargetEx targetEx : list) {
            jsonFilelist.add(getTargetInfo(request,targetEx));
        }

        return jsonFilelist.toArray();
    }

    /**
     * 获取初始化当前文件的集合
     * @param request
     * @param target
     * @return
     * @throws IOException
     */
    protected Map<String,Object> getTargetInfo(final HttpServletRequest request,final TargetEx target) throws IOException {
        Map<String,Object> info = new HashMap<>();
        info.put(ElFinderConstants.ELFINDER_PARAMETER_HASH, target.getHash());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_MIME, target.getMimeType());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_TIMESTAMP, target.getLastModified());
        info.put(ElFinderConstants.ELFINDER_PARAMETER_SIZE, target.getSize());

        info.put("read", target.isReadable(target) ? 1 : 0);
        info.put("write", target.isWritable(target) ? 1 : 0);
        info.put("locked", target.isLocked(target) ? 1 : 0);

        //还差权限模块


        if (target.getMimeType()!=null && target.getMimeType().startsWith("image")){
            StringBuffer urlbuff = request.getRequestURL();
            info.put(ElFinderConstants.ELFINDER_PARAMETER_THUMBNAIL,urlbuff.append(String.format(CMD_TMB_TARGET, target.getHash())));
        }
        if (target.isRoot()){
            info.put("name", target.getVolumnName());
            info.put("volumeid", target.getVolumeId());
        }
        else {
            info.put(ElFinderConstants.ELFINDER_PARAMETER_DIRECTORY_FILE_NAME,target.getName());
            info.put(ElFinderConstants.ELFINDER_PARAMETER_PARENTHASH,target.getParent().getHash());
        }

        if (target.isFolder()){
            info.put(ElFinderConstants.ELFINDER_PARAMETER_HAS_DIR,target.hasChildFolder() ? ElFinderConstants.ELFINDER_TRUE_RESPONSE : ElFinderConstants.ELFINDER_FALSE_RESPONSE);

        }
        return info;
    }
    protected Map<String, Object> getOptions(TargetEx cwd) {
        String[] emptyArray = {};
        map.put(ElFinderConstants.ELFINDER_PARAMETER_PATH, cwd.getName());
        map.put(ElFinderConstants.ELFINDER_PARAMETER_COMMAND_DISABLED, emptyArray);
        map.put("separator", ElFinderConstants.ELFINDER_PARAMETER_FILE_SEPARATOR);
        map.put(ElFinderConstants.ELFINDER_PARAMETER_OVERWRITE_FILE, ElFinderConstants.ELFINDER_TRUE_RESPONSE);
        //此块暂未支持
        map.put(ElFinderConstants.ELFINDER_PARAMETER_ARCHIVERS, new Object[0]);
        map.put("uploadMaxConn", "-1");
        // We don't have an implementation of zipdl at the moment.
        map.put("disabled", Arrays.asList(new String[] { "zipdl" }));
        /**
         * 没有分块上传，下载，分段解压
         */
        return map;
    }

}
