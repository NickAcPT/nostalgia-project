package io.github.nickacpt.nostalgia.rpc.test

import io.github.nickacpt.nostalgia.rpc.NostalgiaRPC
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.client.RemoteRpcException
import io.github.nickacpt.nostalgia.rpc.connection.inMemoryTransport
import io.github.nickacpt.nostalgia.rpc.server.NostalgiaRpcServer
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

object RpcTest {
    private val rpc = NostalgiaRPC()
    private val server = NostalgiaRpcServer(rpc)
    private val client = NostalgiaRpcClient(rpc).also {
        server.inMemoryTransport(it)
    }

    private val blockingClientProxy by lazy {
        client.proxy<TestRpcInterface>()
    }

    private val suspendingClientProxy by lazy {
        client.proxy<SuspendingTestRpcInterface>()
    }

    @JvmStatic
    @BeforeAll
    fun init() {
        server.expose<TestRpcInterface>(TestRpcInterfaceImpl())
        server.expose<SuspendingTestRpcInterface>(SuspendingTestRpcInterfaceImpl())
    }

    @Test
    fun `can call Void method`() {
        assertDoesNotThrow {
            blockingClientProxy.voidMethod()
        }
    }

    @Test
    fun `can call method that returns null`() {
        val result = blockingClientProxy.returnNullMethod()
        assertNull(result)
    }

    @Test
    fun `can call overloaded method with string`() {
        assertDoesNotThrow {
            blockingClientProxy.overloadedMethod("hello")
        }
    }

    @Test
    fun `can call overloaded method with string and int`() {
        assertDoesNotThrow {
            blockingClientProxy.overloadedMethod("hello", 42)
        }
    }

    @Test
    fun `throws exception when calling method that throws`() {
        val exception = assertThrows<RemoteRpcException> {
            blockingClientProxy.methodThatThrows()
        }

        assertEquals("Hello darkness, my old friend!", exception.message)
    }

    @Test
    fun `can call suspending void method`() {
        assertDoesNotThrow {
            runBlocking {
                suspendingClientProxy.suspendingVoidMethod()
            }
        }
    }

    @Test
    fun `can call suspending method that returns null`() {
        runBlocking {
            val result = suspendingClientProxy.suspendingReturnNullMethod()
            assertNull(result)
        }
    }

    @Test
    fun `can call suspending overloaded method with string`() {
        assertDoesNotThrow {
            runBlocking {
                suspendingClientProxy.suspendingOverloadedMethod("hello")
            }
        }
    }

    @Test
    fun `can call suspending overloaded method with string and int`() {
        assertDoesNotThrow {
            runBlocking {
                suspendingClientProxy.suspendingOverloadedMethod("hello", 42)
            }
        }
    }

    @Test
    fun `throws exception when calling suspending method that throws`() {

        val exception = assertThrows<RemoteRpcException> {
            runBlocking { suspendingClientProxy.suspendingMethodThatThrows() }
        }

        assertEquals("Hello darkness, my old friend!", exception.message)
    }

}