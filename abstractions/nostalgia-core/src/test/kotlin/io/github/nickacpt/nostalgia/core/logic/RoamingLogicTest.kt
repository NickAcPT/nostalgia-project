package io.github.nickacpt.nostalgia.core.logic

import io.github.nickacpt.nostalgia.core.Logger
import io.github.nickacpt.nostalgia.core.NostalgiaCore
import io.github.nickacpt.nostalgia.core.logic.RoamingLogic.roam
import io.github.nickacpt.nostalgia.core.managers.PlayerManager
import io.github.nickacpt.nostalgia.core.model.NostalgiaPlayer
import io.github.nickacpt.nostalgia.core.model.NostalgiaShard
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingResult
import io.github.nickacpt.nostalgia.core.model.roaming.PlayerRoamingShardAcknowledgeResult
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class RoamingLogicTest : WordSpec({

    val playerManager: PlayerManager = mockk()

    val shard1: NostalgiaShard = mockk()
    val shard2: NostalgiaShard = mockk()
    val player1: NostalgiaPlayer = mockk()

    mockkObject(NostalgiaCore)
    every { NostalgiaCore.playerManager } returns playerManager

    mockkObject(Logger)

    beforeAny {
        clearMocks(shard1, shard2, player1, playerManager)

        val pSlot = slot<NostalgiaPlayer>()
        val sSlot = slot<NostalgiaShard>()

        every { player1.currentShard } returns shard1

        every { playerManager.teleportPlayerToShard(capture(pSlot), capture(sSlot), any()) } answers {
            every { pSlot.captured.currentShard } answers {
                sSlot.captured
            }
        }
    }

    "RoamingLogic" When {

        "the shards are the same" should {
            every { shard1 == shard2 } returns true

            roam(player1, shard2) shouldBe PlayerRoamingResult.SameShard

            player1.currentShard shouldBe shard1
        }

        "the new shard is full" should {
            every { shard2.acknowledgePlayerRoamRequest(any()) } returns PlayerRoamingShardAcknowledgeResult.FullShard

            roam(player1, shard2) shouldBe PlayerRoamingResult.ShardAcknowledgementFailed(
                PlayerRoamingShardAcknowledgeResult.FullShard
            )

            player1.currentShard shouldBe shard1
        }

        "the new shard accepts the player" should {
            every { shard2.acknowledgePlayerRoamRequest(any()) } returns PlayerRoamingShardAcknowledgeResult.Okay

            roam(player1, shard2) shouldBe PlayerRoamingResult.Success

            verify(exactly = 1) {
                playerManager.teleportPlayerToShard(player1, shard2, any())
            }

            player1.currentShard shouldBe shard2
        }

        "the new shard only accepts 1 player" should {
            val player2: NostalgiaPlayer = mockk()

            every { player2.currentShard } returns shard1

            player1.currentShard shouldBe shard1
            player2.currentShard shouldBe shard1

            every { shard2.acknowledgePlayerRoamRequest(any()) } returns PlayerRoamingShardAcknowledgeResult.Okay andThen PlayerRoamingShardAcknowledgeResult.FullShard

            roam(player1, shard2) shouldBe PlayerRoamingResult.Success
            roam(player2, shard2) shouldBe PlayerRoamingResult.ShardAcknowledgementFailed(
                PlayerRoamingShardAcknowledgeResult.FullShard
            )

            verify(exactly = 1) {
                playerManager.teleportPlayerToShard(player1, shard2, PlayerRoamingShardAcknowledgeResult.Okay)
            }

            player1.currentShard shouldBe shard2
            player2.currentShard shouldBe shard1
        }

        "the player manager throws an exception" should {
            val ex = Exception("Mock Exception")

            every { shard2.acknowledgePlayerRoamRequest(any()) } returns PlayerRoamingShardAcknowledgeResult.Okay
            every { playerManager.teleportPlayerToShard(any(), any(), any()) } throws ex

            player1.currentShard shouldBe shard1

            roam(player1, shard2) shouldBe PlayerRoamingResult.PlayerManagerFailedTeleport(ex)

            player1.currentShard shouldBe shard1
        }
    }
})