package com.trustsystems.elfinder.controller.executor.impl;

import com.trustsystems.elfinder.exception.ErrorException;
import com.trustsystems.elfinder.service2_0.service.FsService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 抽象工厂装饰类
 */
public abstract class AbstractorJsonCommandExecutor extends AbstractCommandExecutor {
    @Override
    final public void execute(FsService fsService, HttpServletRequest request, HttpServletResponse response,
                              ServletContext servletContext) throws Exception {

        JSONObject json = new JSONObject();

        try {
            execute(fsService, request, servletContext, json);
        } catch (ErrorException e) {
            /**
             * 自定义异常处理
             */
            if (e.getArgs() == null || e.getArgs().length == 0)
            {
                json.put("error", e.getError());
            } else
                {
                JSONArray errors = new JSONArray();
                errors.put(e.getError());
                for (String s : e.getArgs()) {
                    errors.put(s);
                }
                json.put("error", errors);
            }
        } catch (Exception e) {
            json.put("error", e.getMessage());
        } finally {
            response.setContentType("text/html;charset= UTF-8");

            PrintWriter writer = response.getWriter();
            json.write(writer);
            System.out.println(json);
            writer.flush();
            writer.close();
        }

    }

    protected abstract void execute(FsService fsService, HttpServletRequest request,
                                    ServletContext servletContext, JSONObject json) throws Exception;
}
