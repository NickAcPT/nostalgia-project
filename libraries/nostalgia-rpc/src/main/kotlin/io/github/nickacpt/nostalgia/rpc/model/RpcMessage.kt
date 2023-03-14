package io.github.nickacpt.nostalgia.rpc.model

import java.util.UUID

interface RpcMessage {
    val requestId: UUID

    fun asReturnResult(): Any? = null
}