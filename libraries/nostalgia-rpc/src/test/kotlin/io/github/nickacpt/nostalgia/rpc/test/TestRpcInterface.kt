package io.github.nickacpt.nostalgia.rpc.test

interface TestRpcInterface {

    // Normal void method
    fun voidMethod()

    // Method that returns null
    fun returnNullMethod(): Any?

    // Overloaded Method (with String)
    fun overloadedMethod(arg1: String)

    // Overloaded Method (with String and an Int)
    fun overloadedMethod(arg1: String, arg2: Int)

    // Method that throws exception
    fun methodThatThrows(): Nothing

}