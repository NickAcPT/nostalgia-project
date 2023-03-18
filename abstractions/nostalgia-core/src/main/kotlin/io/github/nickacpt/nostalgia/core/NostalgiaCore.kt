package io.github.nickacpt.nostalgia.core

import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import net.kyori.adventure.audience.Audience

object NostalgiaCore {
    var playerManager: PlayerManager = PlayerManager.EMPTY
        private set

    fun init(manager: PlayerManager, loggerAudience: Audience) {
        // Initialize our variables
        this.playerManager = manager

        // Initialize our logger
        Logger.init(loggerAudience)
    }
}