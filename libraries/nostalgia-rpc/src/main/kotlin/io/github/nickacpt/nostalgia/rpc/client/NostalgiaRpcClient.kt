package io.github.nickacpt.nostalgia.rpc.client

import com.google.common.reflect.Reflection
import io.github.nickacpt.nostalgia.rpc.client.proxy.RpcProxyHandler
import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.MessageCupid
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import java.util.concurrent.CompletableFuture

class NostalgiaRpcClient: NostalgiaRpcEndpoint {
    private val cupid = MessageCupid()
    var transport: RpcTransport? = null

    inline fun <reified T : Any> proxy(): T {
        return Reflection.newProxy(T::class.java, RpcProxyHandler(this, RpcUtils.getClazzNameForService(T::class.java)))
    }

    override fun handleReceivedMessage(message: RpcMessage, transport: RpcTransport) {
        cupid.weFoundTheOne(message.requestId, message)
    }

    internal fun sendMessage(message: RpcMessage): CompletableFuture<RpcMessage> {
        checkNotNull(transport) { "A transport is required to be set in order for us to be able to send messages" }

        // Try to find the one.
        val theOne = cupid.findTheOne(message.requestId)

        // Send a message through our transport
        transport!!.sendMessage(message)

        return theOne
    }
}