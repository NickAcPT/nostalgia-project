package io.github.nickacpt.nostalgia.rpc.test

class TestRpcInterfaceImpl : TestRpcInterface {
    override fun voidMethod() {
        // No-Op
    }

    override fun returnNullMethod(): Any? {
        return null
    }

    override fun overloadedMethod(arg1: String) {
        println("Overloaded Method got just $arg1")
    }

    override fun overloadedMethod(arg1: String, arg2: Int) {
        println("Overloaded Method got $arg1; $arg2")
    }

    override fun methodThatThrows(): Nothing {
        throw Exception("Hello darkness, my old friend!")
    }
}