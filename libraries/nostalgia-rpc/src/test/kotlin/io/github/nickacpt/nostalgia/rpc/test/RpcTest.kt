package io.github.nickacpt.nostalgia.rpc.test

import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.client.RemoteRpcException
import io.github.nickacpt.nostalgia.rpc.connection.inMemoryTransport
import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

object RpcTest {
    private val server = NostalgiaRpcServer()
    private val client = NostalgiaRpcClient().also {
        server.inMemoryTransport(it)
    }

    private val clientProxy by lazy {
        client.proxy<TestRpcInterface>()
    }

    @JvmStatic
    @BeforeAll
    fun init() {
        server.expose<TestRpcInterface>(TestRpcInterfaceImpl())
    }

    @Test
    fun `can call Void method`() {
        assertDoesNotThrow {
            clientProxy.voidMethod()
        }
    }

    @Test
    fun `can call method that returns null`() {
        val result = clientProxy.returnNullMethod()
        assertNull(result)
    }

    @Test
    fun `can call overloaded method with string`() {
        assertDoesNotThrow {
            clientProxy.overloadedMethod("hello")
        }
    }

    @Test
    fun `can call overloaded method with string and int`() {
        assertDoesNotThrow {
            clientProxy.overloadedMethod("hello", 42)
        }
    }

    @Test
    fun `throws exception when calling method that throws`() {
        val exception = assertThrows<RemoteRpcException> {
            clientProxy.methodThatThrows()
        }

        assertEquals("Hello darkness, my old friend!", exception.message)
    }

}