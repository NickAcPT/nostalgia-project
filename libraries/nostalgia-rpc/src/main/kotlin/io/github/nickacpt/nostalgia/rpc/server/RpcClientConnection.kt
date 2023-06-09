package io.github.nickacpt.nostalgia.rpc.server

import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import java.util.*

internal data class RpcClientConnection(
    val transport: RpcTransport,
    val id: UUID = UUID.randomUUID()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RpcClientConnection

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}