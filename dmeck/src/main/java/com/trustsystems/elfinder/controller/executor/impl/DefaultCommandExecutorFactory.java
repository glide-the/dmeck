package com.trustsystems.elfinder.controller.executor.impl;

import com.trustsystems.elfinder.controller.executor.CommandExecutor;
import com.trustsystems.elfinder.controller.executor.CommandExecutorFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultCommandExecutorFactory implements CommandExecutorFactory {

    String _classNamePattern;
    private Map<String,CommandExecutor>_map= new HashMap<String,CommandExecutor>();

    private CommandExecutor _fallbackCommand;

    @Override
    public CommandExecutor get(String commandName) {

        if (_map.containsKey(commandName)){
            return _map.get(commandName);
        }

        try {
            String className = String.format(_classNamePattern,commandName.
                    substring(0,1).toUpperCase()+commandName.substring(1));
            return (CommandExecutor) Class.forName(className).newInstance();

        } catch (Exception e) {
            return _fallbackCommand;
        }

    }
    public String get_classNamePattern() {
        return _classNamePattern;
    }

    public void set_classNamePattern(String _classNamePattern) {
        this._classNamePattern = _classNamePattern;
    }

    public Map<String, CommandExecutor> get_map() {
        return _map;
    }

    public void set_map(Map<String, CommandExecutor> _map) {
        this._map = _map;
    }

    public CommandExecutor get_fallbackCommand() {
        return _fallbackCommand;
    }

    public void set_fallbackCommand(CommandExecutor _fallbackCommand) {
        this._fallbackCommand = _fallbackCommand;
    }

}
