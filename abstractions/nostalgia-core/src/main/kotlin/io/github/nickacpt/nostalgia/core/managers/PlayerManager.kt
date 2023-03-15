package io.github.nickacpt.nostalgia.core.managers

import io.github.nickacpt.nostalgia.core.managers.empty.EmptyPlayerManager
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult

interface PlayerManager {
    companion object {
        internal val EMPTY by lazy {
            EmptyPlayerManager()
        }
    }

    fun teleportPlayerToShard(
        player: NostalgiaPlayer,
        shard: NostalgiaShard,
        ack: PlayerRoamingShardAcknowledgeResult
    )

}

