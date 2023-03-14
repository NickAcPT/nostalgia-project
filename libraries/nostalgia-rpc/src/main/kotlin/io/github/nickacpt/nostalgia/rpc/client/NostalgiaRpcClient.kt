package io.github.nickacpt.nostalgia.rpc.client

import com.google.common.reflect.Reflection
import io.github.nickacpt.nostalgia.rpc.client.proxy.RpcProxyHandler
import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.MessageCupid
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import java.util.concurrent.CompletableFuture

class NostalgiaRpcClient(val transport: RpcTransport) {

    private val cupid = MessageCupid()

    inline fun <reified T : Any> proxy(): T {
        return Reflection.newProxy(T::class.java, RpcProxyHandler(this, RpcUtils.getClazzNameForService(T::class.java)))
    }

    internal fun sendMessage(request: RpcMessage): CompletableFuture<RpcMessage> {
        TODO()
    }

}