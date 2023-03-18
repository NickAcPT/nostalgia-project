package io.github.nickacpt.nostalgia.rpc

import java.util.concurrent.ConcurrentHashMap

private typealias AnyRpcArgumentMapper = RpcArgumentMapper<Any, Any>

class NostalgiaRPC {
    private val argumentMap: MutableMap<Class<*>, RpcArgumentMapper<*, *>> = ConcurrentHashMap()
    private val reverseArgumentMap: MutableMap<Class<*>, RpcArgumentMapper<*, *>> = ConcurrentHashMap()

    fun <S, T> registerArgumentMapper(mapper: RpcArgumentMapper<S, T>, sourceClazz: Class<S>, targetClazz: Class<T>) {
        argumentMap[sourceClazz] = mapper
        reverseArgumentMap[targetClazz] = mapper
    }

    inline fun <reified S, reified T> registerArgumentMapper(mapper: RpcArgumentMapper<S, T>) {
        registerArgumentMapper(mapper, S::class.java, T::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun mapOutgoingArgument(arg: Any?): Any? {
        return arg?.let { argumentMap[it.javaClass] as? AnyRpcArgumentMapper }?.mapOutgoing(arg) ?: arg
    }

    @Suppress("UNCHECKED_CAST")
    fun mapIncomingArgument(arg: Any?): Any? {
        return arg?.let { reverseArgumentMap[it.javaClass] as? AnyRpcArgumentMapper }?.mapIncoming(arg) ?: arg
    }

}