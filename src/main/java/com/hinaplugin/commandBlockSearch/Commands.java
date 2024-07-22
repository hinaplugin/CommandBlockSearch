package com.hinaplugin.commandBlockSearch;

import com.google.common.collect.Lists;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            if (strings.length != 1){
                return true;
            }
            if (strings[0].isEmpty()){
                return true;
            }
            final int radius = Integer.parseInt(strings[0]);
            final List<String> search = Lists.newArrayList();
            new BukkitRunnable(){
                @Override
                public void run() {
                    int chunkRadius = (radius + 15) / 16;

                    int playerChunkX = player.getLocation().getChunk().getX();
                    int playerChunkZ = player.getLocation().getChunk().getZ();
                    player.sendMessage("Starting search...");

                    for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
                        for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                            Chunk chunk = player.getWorld().getChunkAt(playerChunkX + dx, playerChunkZ + dz);

                            // チャンクがまだロードされていない場合、ロードする
                            if (!chunk.isLoaded()) {
                                chunk.load(true);
                            }

                            for (int x = 0; x < 16; x++) {
                                for (int y = 0; y < player.getWorld().getMaxHeight(); y++) {
                                    for (int z = 0; z < 16; z++) {
                                        Block block = chunk.getBlock(x, y, z);
                                        if (block.getType() == Material.COMMAND_BLOCK || block.getType() == Material.CHAIN_COMMAND_BLOCK || block.getType() == Material.REPEATING_COMMAND_BLOCK) {
                                            String location = "Type: " + block.getType() + ", X: " + block.getX() + ", Y: " + block.getY() + ", Z: " + block.getZ();
                                            search.add(location);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (final String message : search){
                        player.sendMessage(message);
                    }
                }
            }.runTaskAsynchronously(CommandBlockSearch.plugin);

        }

        return false;
    }
}
