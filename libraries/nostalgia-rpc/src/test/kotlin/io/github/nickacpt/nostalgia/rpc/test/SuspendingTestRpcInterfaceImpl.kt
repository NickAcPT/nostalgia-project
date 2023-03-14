package io.github.nickacpt.nostalgia.rpc.test

import kotlinx.coroutines.delay

class SuspendingTestRpcInterfaceImpl : SuspendingTestRpcInterface {
    override suspend fun suspendingVoidMethod() {
        delay(1500)
        // No-Op
    }

    override suspend fun suspendingReturnNullMethod(): Any? {
        return null
    }

    override suspend fun suspendingOverloadedMethod(arg1: String) {
        println("Overloaded Method got just $arg1")
    }

    override suspend fun suspendingOverloadedMethod(arg1: String, arg2: Int) {
        println("Overloaded Method got $arg1; $arg2")
    }

    override suspend fun suspendingMethodThatThrows(): Nothing {
        delay(2000)
        throw Exception("Hello darkness, my old friend!")
    }
}