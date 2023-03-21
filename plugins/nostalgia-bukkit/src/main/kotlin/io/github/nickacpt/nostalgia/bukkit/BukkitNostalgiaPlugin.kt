package io.github.nickacpt.nostalgia.bukkit

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BukkitNostalgiaPlugin : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(LocalNostalgiaPlayerManager, this)
    }
}