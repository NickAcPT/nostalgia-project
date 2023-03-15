package io.github.nickacpt.nostalgia.core.model.roaming

sealed class PlayerRoamingShardAcknowledgeResult(val description: String, val success: Boolean = false) {

    override fun toString(): String {
        return "$description (${javaClass.simpleName})"
    }

    object Okay : PlayerRoamingShardAcknowledgeResult("Okay", true)
    object FullShard : PlayerRoamingShardAcknowledgeResult("Shard is full and cannot accept more players")
    object UnresponsiveShard : PlayerRoamingShardAcknowledgeResult("Shard didn't reply to the Acknowledgement in time")
    data class AcknowledgementError(val e: Exception) :
        PlayerRoamingShardAcknowledgeResult("Shard Acknowledment threw an error: ${e.stackTraceToString()}")
}