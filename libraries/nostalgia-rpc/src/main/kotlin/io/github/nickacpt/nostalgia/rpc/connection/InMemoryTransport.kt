package io.github.nickacpt.nostalgia.rpc.connection

import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer

data class InMemoryTransport(val server: NostalgiaRpcServer) : RpcTransport
