package io.github.nickacpt.nostalgia.velocity.model

import com.velocitypowered.api.proxy.Player
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import java.util.*

data class VelocityNostalgiaPlayer(val player: Player) : NostalgiaPlayer, ForwardingAudience.Single {
    override val currentShard: NostalgiaShard
        get() = TODO("Not yet implemented")

    override val id: UUID
        get() = player.uniqueId

    override fun audience(): Audience {
        return player
    }
}