package io.github.nickacpt.nostalgia.rpc.model

import java.util.*

data class MethodCallRequestRpcMessage(
    val clazz: String,
    val method: String,
    val args: List<Any?>,
    override val requestId: UUID = UUID.randomUUID()
) : RpcMessage
