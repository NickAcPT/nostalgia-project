package io.github.nickacpt.nostalgia.rpc.client

import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage

interface NostalgiaRpcEndpoint {
    fun handleReceivedMessage(message: RpcMessage, transport: RpcTransport)
}