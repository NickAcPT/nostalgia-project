package io.github.nickacpt.nostalgia.rpc.client.proxy

import com.google.common.reflect.AbstractInvocationHandler
import io.github.nickacpt.nostalgia.rpc.client.NostalgiaRpcClient
import io.github.nickacpt.nostalgia.rpc.client.RemoteRpcException
import io.github.nickacpt.nostalgia.rpc.model.MethodCallReplyRpcMessage
import io.github.nickacpt.nostalgia.rpc.model.MethodCallRequestRpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import java.lang.reflect.Method
import kotlin.coroutines.Continuation

class RpcProxyHandler(private val client: NostalgiaRpcClient, private val clazzName: String) :
    AbstractInvocationHandler() {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
        val argsList = args.toMutableList()

        @Suppress("UNCHECKED_CAST")
        val continuation = argsList.lastOrNull() as? Continuation<Any?>

        val isCoroutine = continuation != null

        if (isCoroutine) {
            // Since we're handling a coroutine, we can't just send our continuation to the server
            argsList.remove(continuation)
        }

        // The request itself
        val requestModel = MethodCallRequestRpcMessage(
            clazzName,
            RpcUtils.getMethodNameForService(method),
            argsList.map(client.rpc::mapOutgoingArgument)
        )

        // Send the message, we have to handle this after
        val resultingMessage = client.sendMessage(requestModel)

        var future = resultingMessage.thenApply { result ->
            val exception = if (result !is MethodCallReplyRpcMessage) {
                "We got an invalid reply for our call"
            } else if (result.result == null) {
                "We didn't get a result for our call"
            } else if (result.result.isFailure) {
                result.result.exceptionOrNull()?.message ?: "An error occurred but we didn't get a message for it.."
            } else null

            runCatching {
                exception?.let { throw RemoteRpcException(exception) }

                (result as MethodCallReplyRpcMessage).result!!.map(client.rpc::mapIncomingArgument).getOrNull()
            }
        }

        if (isCoroutine) {
            future = future.thenApply {
                continuation?.resumeWith(it)
                it
            }

            ioScope.launch {
                future.await()
            }

            return kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
        }

        return future.get().getOrThrow()
    }
}