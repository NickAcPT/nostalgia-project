package io.github.nickacpt.nostalgia.rpc.server

import io.github.nickacpt.nostalgia.rpc.NostalgiaRPC
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcEndpoint
import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class NostalgiaRpcServer(val rpc: NostalgiaRPC) : NostalgiaRpcEndpoint {
    private val connections = ConcurrentHashMap<UUID, RpcClientConnection>()
    private val serviceHandler = NostalgiaServiceHandler()

    var bootstrapTransport: RpcTransport? = null
        set(value) {
            field = value
            value?.init(this)
        }

    /**
     * Expose an RPC service on this server.
     */
    inline fun <reified T : Any> expose(service: T) {
        val serviceClazz = T::class.java
        check(serviceClazz.isInterface) {
            "When exposing a service, pass an interface as the generic T parameter. " +
                    "${serviceClazz.name} isn't an interface!"
        }

        expose(serviceClazz, service)
    }

    fun <T : Any> expose(serviceClazz: Class<T>, service: T) {
        serviceHandler.exposeService(serviceClazz, service)
    }

    override fun handleReceivedMessage(message: RpcMessage, transport: RpcTransport) {
        // TODO: Handle messages
        serviceHandler.handleMessage(message, transport, rpc)
    }

    override fun addConnection(connectionTransport: RpcTransport) {
        addConnection(RpcClientConnection(connectionTransport))
    }

    internal fun addConnection(connection: RpcClientConnection) {
        connections[connection.id] = connection
    }
}