package io.github.nickacpt.nostalgia.rpc.model

import java.util.*

data class MethodCallReplyRpcMessage(
    override val requestId: UUID,
    val result: Result<Any?>?
) : RpcMessage