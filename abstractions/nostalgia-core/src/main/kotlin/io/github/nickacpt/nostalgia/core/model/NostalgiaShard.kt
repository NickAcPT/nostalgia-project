package io.github.nickacpt.nostalgia.core.model

import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience

interface NostalgiaShard : Identifiable, ForwardingAudience {

    val players: Collection<NostalgiaPlayer>

    override fun audiences(): Iterable<Audience> {
        return players
    }

    fun acknowledgePlayerRoamRequest(player: NostalgiaPlayer): PlayerRoamingShardAcknowledgeResult
}