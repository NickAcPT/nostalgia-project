package io.github.nickacpt.nostalgia.core.logic

import io.github.nickacpt.nostalgia.core.Logger.debug
import io.github.nickacpt.nostalgia.core.NostalgiaCore.playerManager
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingResult
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult

object RoamingLogic {

    fun roam(player: NostalgiaPlayer, newShard: NostalgiaShard): PlayerRoamingResult {
        debug("Starting roaming of player $player into shard $newShard.")

        // Basic check to make sure we're not handing off the player to the same shard
        if (player.currentShard == newShard) {
            debug("Tried to roam player $player to the same shard they're in?!")
            return PlayerRoamingResult.SameShard
        }

        // Ask the shard to acknowledge our Roaming request
        // If the acknowledgement is successful, it means that the shard has the player data loaded,
        // and the player is ready to be handed off by the proxy.
        val ack: PlayerRoamingShardAcknowledgeResult = try {
            newShard.acknowledgePlayerRoamRequest(player)
        } catch (e: Exception) {
            debug("Shard $newShard roaming acknowledgement failed with an error")
            PlayerRoamingShardAcknowledgeResult.AcknowledgementError(e)
        }

        debug("Shard $newShard's acknowledgement of player $player was $ack")
        if (!ack.success) {
            return PlayerRoamingResult.ShardAcknowledgementFailed(ack)
        }

        // Try to hand off the player to the new shard by calling our player manager
        // This is the last step where we ask the proxy to move the player to the new shard
        // As such, if this succeeds, then we're certain the player was moved in successfully
        try {
            playerManager.teleportPlayerToShard(player, newShard, ack)
        } catch (e: Exception) {
            debug("Player Manager was unable to teleport the player $player to shard $newShard.")
            debug("Error was: ${e.stackTraceToString()}")

            return PlayerRoamingResult.PlayerManagerFailedTeleport(e)
        }

        debug("Roaming of player $player to shard $newShard was successful")
        return PlayerRoamingResult.Success
    }
}