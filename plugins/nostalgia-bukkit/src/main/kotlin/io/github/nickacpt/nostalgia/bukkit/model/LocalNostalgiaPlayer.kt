package io.github.nickacpt.nostalgia.bukkit.model

import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import java.util.*

data class LocalNostalgiaPlayer(override val id: UUID) : NostalgiaPlayer {
    override val currentShard: NostalgiaShard
        get() = LocalNostalgiaShard
}