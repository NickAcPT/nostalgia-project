package io.github.nickacpt.nostalgia.bukkit.utils

import io.github.nickacpt.nostalgia.bukkit.managers.LocalNostalgiaPlayerManager
import org.bukkit.entity.Player

val Player.nostalgia get() = LocalNostalgiaPlayerManager.getNostalgiaPlayer(uniqueId)