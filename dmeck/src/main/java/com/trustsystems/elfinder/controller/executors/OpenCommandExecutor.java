package com.trustsystems.elfinder.controller.executors;

import com.trustsystems.elfinder.configuration.ElFinderConstants;
import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.controller.executor.impl.AbstractorJsonCommandExecutor;
import com.trustsystems.elfinder.core2_0.Volume;
import com.trustsystems.elfinder.service2_0.service.FsService;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class OpenCommandExecutor extends AbstractorJsonCommandExecutor
    implements CommandExecutor{
    @Override
    protected void execute(FsService fsService, HttpServletRequest request,
                           ServletContext servletContext, JSONObject json) throws Exception
    {
        boolean init = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_INIT) !=null;
        boolean tree = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_TREE) !=null;
        String target = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_TARGET);

        Map<String ,TargetEx> files= new LinkedHashMap<String, TargetEx>();
        if (init){
            json.put(ElFinderConstants.ELFINDER_PARAMETER_API,2.1);
            json.put(ElFinderConstants.ELFINDER_PARAMETER_NETDRIVERS,new Object[0]);
        }
        if (tree){
            for (Volume v : fsService.getVolumes()) {
                TargetEx root = new TargetEx(v.getRoot(), fsService);
                files.put(root.getHash(),root);
                addSubFolders(files,root);
            }
        }

        TargetEx cwd = finderCwd(fsService, target);
        files.put(cwd.getHash(),cwd);
        addChildren(files,cwd);

        json.put(ElFinderConstants.ELFINDER_PARAMETER_FILES,buildJsonFilesArray(request,files.values()));
        json.put(ElFinderConstants.ELFINDER_PARAMETER_CWD,getTargetInfo(request,cwd));
        json.put(ElFinderConstants.ELFINDER_PARAMETER_OPTIONS,getOptions(cwd));

    }
}
