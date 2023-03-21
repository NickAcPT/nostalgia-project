package io.github.nickacpt.nostalgia.core

import io.github.nickacpt.nostalgia.core.logic.mapping.RemoteNostalgiaPlayerReferenceMapper
import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import io.github.nickacpt.nostalgia.rpc.NostalgiaRPC
import net.kyori.adventure.audience.Audience

object NostalgiaCore {
    val rpc = NostalgiaRPC().apply {
        registerArgumentMapper(RemoteNostalgiaPlayerReferenceMapper)
    }

    var playerManager: PlayerManager = PlayerManager.EMPTY
        private set

    fun init(manager: PlayerManager, loggerAudience: Audience) {
        // Initialize our variables
        this.playerManager = manager

        // Initialize our logger
        Logger.init(loggerAudience)
    }
}