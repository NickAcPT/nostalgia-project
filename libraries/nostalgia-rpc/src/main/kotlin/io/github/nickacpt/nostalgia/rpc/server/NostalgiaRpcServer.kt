package io.github.nickacpt.nostalgia.rpc.server

import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcEndpoint
import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class NostalgiaRpcServer : NostalgiaRpcEndpoint {
    private val connections = ConcurrentHashMap<UUID, RpcClientConnection>()

    /**
     * Expose an RPC service on this server.
     */
    inline fun <reified T> expose(service: T) {
        val serviceClazz = T::class.java
        check(serviceClazz.isInterface) {
            "When exposing a service, pass an interface as the generic T parameter. " +
                    "${serviceClazz.name} isn't an interface!"
        }

        // TODO: Expose services
    }

    internal fun addConnection(connection: RpcClientConnection) {
        connections[connection.id] = connection
    }

    override fun handleReceivedMessage(message: RpcMessage, transport: RpcTransport) {
        // TODO: Handle messages
    }
}