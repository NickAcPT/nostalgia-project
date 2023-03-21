package io.github.nickacpt.nostalgia.bukkit.model

import io.github.nickacpt.nostalgia.bukkit.managers.LocalNostalgiaPlayerManager
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult
import org.bukkit.Bukkit
import java.util.*

object LocalNostalgiaShard : NostalgiaShard {
    override val id: UUID = UUID.randomUUID()

    override val players: Collection<NostalgiaPlayer>
        get() = LocalNostalgiaPlayerManager.players

    override fun acknowledgePlayerRoamRequest(player: NostalgiaPlayer): PlayerRoamingShardAcknowledgeResult {
        val newPlayerCount = Bukkit.getOnlinePlayers().size + 1

        // If we have too many players in this shard, we have to say that we're full
        if (newPlayerCount > Bukkit.getMaxPlayers()) {
            return PlayerRoamingShardAcknowledgeResult.FullShard
        }

        // Now we have to migrate the player data to this shard
        // and prepare everything to load them in once they get teleported by the proxy server.
        // This means we need to set up their current inventory and other player data.
        TODO("Not yet implemented")
    }
}