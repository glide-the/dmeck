package com.trustsystems.elfinder.controller.executor;

/**
 * 抽象工厂AbstractCommandExecutor的接口
 */
public interface CommandExecutor {
    void execute(CommandExectionContext commandExectionContext) throws  Exception;
}
