package io.github.nickacpt.nostalgia.rpc.utils

import io.github.nickacpt.nostalgia.rpc.model.RpcMessage
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Future


/**
 * Ya know, messages can feel lonely too sometimes.How do we fix it though? That is simple!
 *
 * We take each message and ~~ship~~ join it with another one.
 * However, we need to be careful, since we don't want to match the wrong messages.
 *
 * That is the task of the MessageCupid, to match the messages sent and received.
 */
class MessageCupid {
    private val waitingQueue = ConcurrentHashMap<UUID, CompletableFuture<RpcMessage>>()

    /**
     * As the name implies, we were asked to find The One™ for this message.
     *
     * Our task is then to return a Future where the message has found The One.
     *
     * @param originatingMessageId The originating message ID
     * @return Future for once we have a reply for the originating message
     */
    fun findTheOne(originatingMessageId: UUID): Future<RpcMessage> {
        return waitingQueue.getOrPut(originatingMessageId) { CompletableFuture() }
    }

    /**
     * Find the message that is lonely and talk with them about the future.
     *
     * @param originatingMessageId The originating message ID
     * @return A CompletableFuture if we're waiting for a reply message, or null otherwise.
     */
    private fun whereIsTheWaitingGuy(originatingMessageId: UUID): CompletableFuture<RpcMessage>? {
        return waitingQueue[originatingMessageId]
    }

    /**
     * OMG GREAT NEWS!!!! We've found the one.
     * Now we can start planning the Future with them.
     * Imagine all the possibilities!
     *
     * (me and who!?)
     *
     * @param originatingMessageId The originating message ID
     * @param theOne The message reply to the original request
     */
    fun weFoundTheOne(originatingMessageId: UUID, theOne: RpcMessage) {
        // Find myself.
        val me = whereIsTheWaitingGuy(originatingMessageId)

        // Complete me with The One.
        me?.complete(theOne)
    }
}