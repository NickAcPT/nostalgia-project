package io.github.nickacpt.nostalgia.bukkit.model

import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult
import java.util.*

object LocalNostalgiaShard : NostalgiaShard {
    override val id: UUID = UUID.randomUUID()

    override val players: Collection<NostalgiaPlayer>
        get() = TODO("Not yet implemented")

    override fun acknowledgePlayerRoamRequest(player: NostalgiaPlayer): PlayerRoamingShardAcknowledgeResult {
        TODO("Not yet implemented")
    }
}