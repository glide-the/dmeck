package com.trustsystems.elfinder.controller.executor;

public interface CommandExecutorFactory {
    CommandExecutor get (String commandName);
}
