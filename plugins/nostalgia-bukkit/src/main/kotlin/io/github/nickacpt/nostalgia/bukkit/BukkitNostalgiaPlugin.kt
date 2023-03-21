package io.github.nickacpt.nostalgia.bukkit

import io.github.nickacpt.nostalgia.bukkit.managers.LocalNostalgiaPlayerManager
import io.github.nickacpt.nostalgia.core.NostalgiaCore
import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class BukkitNostalgiaPlugin : JavaPlugin() {
    private val client = NostalgiaRpcClient(NostalgiaCore.rpc)

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(LocalNostalgiaPlayerManager, this)

        val remotePlayerManager = client.proxy<PlayerManager>()
        NostalgiaCore.init(remotePlayerManager, Bukkit.getConsoleSender())
    }
}