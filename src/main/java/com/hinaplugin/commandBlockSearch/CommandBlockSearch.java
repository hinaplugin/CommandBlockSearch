package com.hinaplugin.commandBlockSearch;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandBlockSearch extends JavaPlugin {
    public static CommandBlockSearch plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        PluginCommand command = this.getCommand("commandblocksearch");
        if (command != null){
            command.setExecutor(new Commands());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
