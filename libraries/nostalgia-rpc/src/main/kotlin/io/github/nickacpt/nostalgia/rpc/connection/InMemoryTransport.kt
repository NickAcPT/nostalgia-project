package io.github.nickacpt.nostalgia.rpc.connection

import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcEndpoint
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer

internal class InMemoryTransport(private val self: NostalgiaRpcEndpoint, private val other: NostalgiaRpcEndpoint) :
    RpcTransport {

    private val flipped by lazy {
        InMemoryTransport(other, self)
    }

    override fun sendMessage(message: RpcMessage) {
        other.handleReceivedMessage(message, flipped)
    }

    override fun init(callback: TransportEndpointCallback) {
        callback.addConnection(this)
        other.addConnection(flipped)
    }

    override fun close() {
        // No-op
    }
}

internal fun NostalgiaRpcServer.inMemoryTransport(client: NostalgiaRpcClient) = this.also {
    val transport = InMemoryTransport(client, it)
    client.transport = transport
}