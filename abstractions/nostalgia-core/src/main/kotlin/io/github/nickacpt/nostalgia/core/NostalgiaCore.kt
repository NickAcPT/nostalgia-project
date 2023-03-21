package io.github.nickacpt.nostalgia.core

import io.github.nickacpt.nostalgia.core.logic.mapping.RemoteNostalgiaPlayerReferenceMapper
import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import io.github.nickacpt.nostalgia.rpc.NostalgiaRPC
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer
import net.kyori.adventure.audience.Audience

object NostalgiaCore {
    private val rpc = NostalgiaRPC().apply {
        registerArgumentMapper(RemoteNostalgiaPlayerReferenceMapper)
    }

    fun createRpcClient(): NostalgiaRpcClient {
        return NostalgiaRpcClient(rpc)
    }

    fun createRpcServer(): NostalgiaRpcServer {
        return NostalgiaRpcServer(rpc)
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