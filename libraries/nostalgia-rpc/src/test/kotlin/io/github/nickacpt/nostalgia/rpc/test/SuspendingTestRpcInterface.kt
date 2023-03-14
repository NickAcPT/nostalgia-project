package io.github.nickacpt.nostalgia.rpc.test

interface SuspendingTestRpcInterface {

    // Normal void method
    suspend fun suspendingVoidMethod()

    // Method that returns null
    suspend fun suspendingReturnNullMethod(): Any?

    // Overloaded Method (with String)
    suspend fun suspendingOverloadedMethod(arg1: String)

    // Overloaded Method (with String and an Int)
    suspend fun suspendingOverloadedMethod(arg1: String, arg2: Int)

    // Method that throws exception
    suspend fun suspendingMethodThatThrows(): Nothing

}