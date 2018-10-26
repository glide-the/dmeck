package com.trustsystems.elfinder.controller.executors;

import com.trustsystems.elfinder.configuration.ElFinderConstants;
import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.controller.executor.impl.AbstractorJsonCommandExecutor;
import com.trustsystems.elfinder.service2_0.service.FsService;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ParentsCommandExecutor extends AbstractorJsonCommandExecutor implements CommandExecutor {
    @Override
    protected void execute(FsService fsService, HttpServletRequest request, ServletContext servletContext, JSONObject json) throws Exception {
        final String string = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_TARGET);

        Map<String , TargetEx> files= new HashMap<String,TargetEx>();

        TargetEx target = findTarget(fsService, string);

        while (!target.isRoot()) {
            addSubFolders(files,target);
            target = target.getParent();
        }
        json.put(ElFinderConstants.ELFINDER_PARAMETER_TREE,buildJsonFilesArray(request,files.values()));
    }
}
