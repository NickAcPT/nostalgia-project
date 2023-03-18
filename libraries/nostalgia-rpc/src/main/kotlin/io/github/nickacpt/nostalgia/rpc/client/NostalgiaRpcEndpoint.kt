package io.github.nickacpt.nostalgia.rpc.client

import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.connection.TransportEndpointCallback
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage

interface NostalgiaRpcEndpoint : TransportEndpointCallback {
    fun handleReceivedMessage(message: RpcMessage, transport: RpcTransport)
}