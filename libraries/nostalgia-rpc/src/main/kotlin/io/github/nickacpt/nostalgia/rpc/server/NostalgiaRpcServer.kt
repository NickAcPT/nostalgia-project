package io.github.nickacpt.nostalgia.rpc.server

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class NostalgiaRpcServer {
    private val connections = ConcurrentHashMap<UUID, RpcClientConnection>()
}