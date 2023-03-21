package io.github.nickacpt.nostalgia.core.logic.mapping

import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.rpc.RpcArgumentMapper
import java.util.*

internal object RemoteNostalgiaPlayerReferenceMapper :
    RpcArgumentMapper<NostalgiaPlayer, RemoteNostalgiaPlayerReferenceMapper.PlayerData> {
    data class PlayerData(val id: UUID, val shardId: UUID)

    override fun mapOutgoing(source: NostalgiaPlayer): PlayerData {
        return PlayerData(source.id, source.currentShard.id)
    }

    override fun mapIncoming(target: PlayerData): NostalgiaPlayer {
        TODO("Not yet implemented")
    }
}
