package io.github.nickacpt.nostalgia.rpc

interface RpcArgumentMapper<S, T> {
    fun mapOutgoing(source: S): T

    fun mapIncoming(target: T): S
}