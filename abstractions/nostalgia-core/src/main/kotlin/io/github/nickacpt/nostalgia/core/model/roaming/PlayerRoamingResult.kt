package io.github.nickacpt.nostalgia.core.model.roaming

sealed class PlayerRoamingResult(val description: String, val success: Boolean = false) {
    object Success : PlayerRoamingResult("Played was roamed successfully", true)
    object SameShard : PlayerRoamingResult("Player tried roaming into the same shard they're currently in")
    data class ShardAcknowledgementFailed(val result: PlayerRoamingShardAcknowledgeResult) :
        PlayerRoamingResult("Shard's acknowledgement of the player failed with result: $result")
    data class PlayerManagerFailedTeleport(val error: Exception) :
        PlayerRoamingResult("The Player Manager was unable to teleport to new shard and errored")
}