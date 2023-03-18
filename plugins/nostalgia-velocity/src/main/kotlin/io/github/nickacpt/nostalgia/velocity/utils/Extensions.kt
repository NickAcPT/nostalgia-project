package io.github.nickacpt.nostalgia.velocity.utils

import com.velocitypowered.api.proxy.Player
import io.github.nickacpt.nostalgia.core.NostalgiaCore
import io.github.nickacpt.nostalgia.velocity.managers.VelocityPlayerManager

private val velocityPlayerManager
    get() = NostalgiaCore.playerManager as VelocityPlayerManager

val Player.nostalgiaPlayer
    get() = velocityPlayerManager.getNostalgiaPlayer(uniqueId)