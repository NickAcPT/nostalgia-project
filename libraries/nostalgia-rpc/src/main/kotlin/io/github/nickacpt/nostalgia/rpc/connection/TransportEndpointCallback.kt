package io.github.nickacpt.nostalgia.rpc.connection

interface TransportEndpointCallback {
    fun addConnection(connectionTransport: RpcTransport)
}