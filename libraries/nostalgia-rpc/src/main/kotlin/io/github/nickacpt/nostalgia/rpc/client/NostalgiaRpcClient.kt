package io.github.nickacpt.nostalgia.rpc.client

import com.google.common.reflect.Reflection
import io.github.nickacpt.nostalgia.rpc.client.proxy.RpcProxyHandler
import io.github.nickacpt.nostalgia.rpc.connection.RpcTransport
import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import io.github.nickacpt.nostalgia.rpc.utils.MessageCupid
import io.github.nickacpt.nostalgia.rpc.utils.RpcUtils
import java.util.concurrent.Future

class NostalgiaRpcClient(private val transport: RpcTransport): NostalgiaRpcEndpoint {
    private val cupid = MessageCupid()

    inline fun <reified T : Any> proxy(): T {
        return Reflection.newProxy(T::class.java, RpcProxyHandler(this, RpcUtils.getClazzNameForService(T::class.java)))
    }

    override fun handleReceivedMessage(message: RpcMessage, transport: RpcTransport) {
        cupid.weFoundTheOne(message.requestId, message)
    }

    internal fun sendMessage(message: RpcMessage): Future<RpcMessage> {
        // Send a message through our transport
        transport.sendMessage(message)

        // Now we wait until we find the one.
        return cupid.findTheOne(message.requestId)
    }
}