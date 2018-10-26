package com.trustsystems.elfinder.controller.executors;

import com.trustsystems.elfinder.configuration.ElFinderConstants;
import com.trustsystems.elfinder.controller.TargetEx;
import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.controller.executor.impl.AbstractorJsonCommandExecutor;
import com.trustsystems.elfinder.service2_0.service.FsService;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class MkfileCommandExecutory extends AbstractorJsonCommandExecutor implements CommandExecutor {
    @Override
    protected void execute(FsService fsService, HttpServletRequest request, ServletContext servletContext, JSONObject json) throws Exception {
        String target = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_TARGET);
        String name = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_DIRECTORY_FILE_NAME);

        TargetEx fs = super.findTarget(fsService, target);
        TargetEx dir = new TargetEx(fs, name);
        dir.createFile();


        json.put(ElFinderConstants.ELFINDER_JSON_RESPONSE_ADDED,new Object[]{getTargetInfo(request,dir)});
    }
}
