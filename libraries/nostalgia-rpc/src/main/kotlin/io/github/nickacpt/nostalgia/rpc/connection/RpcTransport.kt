package io.github.nickacpt.nostalgia.rpc.connection

import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import java.io.Closeable

/**
 * Transport for the RPC messages
 */
interface RpcTransport : Closeable {

    /**
     * Initialize this transport for the first time.
     */
    fun init()

    fun sendMessage(message: RpcMessage)
}