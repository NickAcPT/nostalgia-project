package io.github.nickacpt.nostalgia.rpc.test

import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.connection.InMemoryTransport
import io.github.nickacpt.nostalgia.rpc.connection.inMemoryTransport
import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer
import org.junit.jupiter.api.BeforeAll

object RpcTest {
    private val server = NostalgiaRpcServer()
    private val client = NostalgiaRpcClient(InMemoryTransport(server)).also {
        server.inMemoryTransport(it)
    }
    val clientProxy by lazy {
        client.proxy<TestRpcInterface>()
    }

    @JvmStatic
    @BeforeAll
    fun init(): Unit {
        server.expose<TestRpcInterface>(TestRpcInterfaceImpl())
    }
}