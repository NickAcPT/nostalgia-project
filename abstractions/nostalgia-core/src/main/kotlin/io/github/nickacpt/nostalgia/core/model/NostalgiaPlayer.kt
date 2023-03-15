package io.github.nickacpt.nostalgia.core.model

import net.kyori.adventure.audience.Audience

interface NostalgiaPlayer : Identifiable, Audience {
    val currentShard: NostalgiaShard
}