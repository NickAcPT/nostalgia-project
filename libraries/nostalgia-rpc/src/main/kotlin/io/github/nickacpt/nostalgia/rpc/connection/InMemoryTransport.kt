package io.github.nickacpt.nostalgia.rpc.connection

import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcEndpoint
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer
import io.github.nickacpt.nostalgia.rpc.server.RpcClientConnection

class InMemoryTransport(private val self: NostalgiaRpcEndpoint, private val other: NostalgiaRpcEndpoint) : RpcTransport {

    internal val flipped by lazy {
        InMemoryTransport(other, self)
    }

    override fun sendMessage(message: RpcMessage) {
        other.handleReceivedMessage(message, flipped)
    }

    override fun init() {
        // No-op
        // TODO: Abstract this to a endpoint callback interface
        //if (other is NostalgiaRpcServer) {
        //    other.addConnection(RpcClientConnection(transport = this))
        //}
    }

    override fun close() {
        // No-op
    }
}

fun NostalgiaRpcServer.inMemoryTransport(client: NostalgiaRpcClient) = this.also {
    val transport = InMemoryTransport(client, it)
    client.transport = transport

    addConnection(RpcClientConnection(transport.flipped))
}