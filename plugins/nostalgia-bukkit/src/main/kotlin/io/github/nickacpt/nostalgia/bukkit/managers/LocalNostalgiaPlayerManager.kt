package io.github.nickacpt.nostalgia.bukkit.managers

import io.github.nickacpt.nostalgia.bukkit.model.LocalNostalgiaPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object LocalNostalgiaPlayerManager : Listener {
    private val playerMap: MutableMap<UUID, LocalNostalgiaPlayer> = ConcurrentHashMap()

    fun getNostalgiaPlayer(id: UUID) = playerMap.getOrPut(id) {
        LocalNostalgiaPlayer(id)
    }

    private fun removePlayer(id: UUID) {
        playerMap.remove(id)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onJoin(e: PlayerJoinEvent) {
        // Force fetching of our player
        getNostalgiaPlayer(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerLeave(e: PlayerQuitEvent) {
        removePlayer(e.player.uniqueId)
    }
}