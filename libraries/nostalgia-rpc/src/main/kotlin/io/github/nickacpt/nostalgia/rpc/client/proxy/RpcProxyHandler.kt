package io.github.nickacpt.nostalgia.rpc.client.proxy

import com.google.common.reflect.AbstractInvocationHandler
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.client.RemoteRpcException
import io.github.nickacpt.nostalgia.rpc.model.MethodCallReplyRpcMessage
import io.github.nickacpt.nostalgia.rpc.model.MethodCallRequestRpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import java.lang.reflect.Method

class RpcProxyHandler(val client: NostalgiaRpcClient, val clazzName: String) : AbstractInvocationHandler() {
    override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
        // The request itself
        val requestModel = MethodCallRequestRpcMessage(
            clazzName,
            RpcUtils.getMethodNameForService(method),
            args.toList()
        )

        // Send the message, we have to handle this after
        val resultingMessage = client.sendMessage(requestModel)

        // Block for the result
        val result = resultingMessage.get()

        val exception = if (result !is MethodCallReplyRpcMessage) {
            "We got an invalid reply for our call"
        } else if (result.result == null) {
            "We didn't get a result for our call"
        } else if (result.result.isFailure) {
            result.result.exceptionOrNull()?.message ?: "An error occurred but we didn't get a message for it.."
        } else null

        exception?.let { throw RemoteRpcException(exception) }

        return (result as MethodCallReplyRpcMessage).result!!.getOrNull()
    }
}