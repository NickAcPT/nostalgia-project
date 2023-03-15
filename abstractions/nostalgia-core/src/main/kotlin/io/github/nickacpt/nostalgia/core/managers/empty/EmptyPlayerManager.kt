package io.github.nickacpt.nostalgia.core.managers.empty

import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult

class EmptyPlayerManager : PlayerManager {
    override fun teleportPlayerToShard(
        player: NostalgiaPlayer,
        shard: NostalgiaShard,
        ack: PlayerRoamingShardAcknowledgeResult
    ) {
        blowUp()
    }

    private fun blowUp(): Nothing {
        throw Exception("Empty player manager called! Did you forget to change NostalgiaCore#playerManager?!")
    }
}