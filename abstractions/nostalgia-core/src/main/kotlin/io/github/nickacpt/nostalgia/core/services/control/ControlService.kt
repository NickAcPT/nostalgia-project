package io.github.nickacpt.nostalgia.core.services.control

import io.github.nickacpt.nostalgia.core.model.NostalgiaShard

interface ControlService {

    fun notifyShardStatus(shard: NostalgiaShard, status: ShardStatus)

}