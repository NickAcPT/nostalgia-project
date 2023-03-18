package io.github.nickacpt.nostalgia.velocity.managers

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.proxy.ProxyServer
import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult
import io.github.nickacpt.nostalgia.velocity.model.VelocityNostalgiaPlayer
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class VelocityPlayerManager @Inject constructor(val server: ProxyServer) : PlayerManager {

    private val playerMap: MutableMap<UUID, NostalgiaPlayer> = ConcurrentHashMap<UUID, NostalgiaPlayer>()

    fun getNostalgiaPlayer(id: UUID) = playerMap[id]

    @Subscribe
    fun onPlayerLogin(e: LoginEvent) {
        playerMap.computeIfAbsent(e.player.uniqueId) {
            VelocityNostalgiaPlayer(e.player)
        }
    }

    @Subscribe
    fun onPlayerDisconnect(e: DisconnectEvent) {
        playerMap.remove(e.player.uniqueId)
    }

    override fun teleportPlayerToShard(
        player: NostalgiaPlayer,
        shard: NostalgiaShard,
        ack: PlayerRoamingShardAcknowledgeResult
    ) {
        TODO("Not yet implemented")
    }
}