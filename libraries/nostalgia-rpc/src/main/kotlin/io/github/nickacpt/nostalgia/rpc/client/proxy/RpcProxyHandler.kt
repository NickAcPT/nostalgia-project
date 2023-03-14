package io.github.nickacpt.nostalgia.rpc.client.proxy

import com.google.common.reflect.AbstractInvocationHandler
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
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
        return resultingMessage.get().asReturnResult()
    }
}