package io.github.nickacpt.nostalgia.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import io.github.nickacpt.nostalgia.core.NostalgiaCore
import io.github.nickacpt.nostalgia.velocity.managers.VelocityPlayerManager

@Plugin(id = "nostalgia", name = "Nostalgia Project", authors = ["NickAcPT"])
class VelocityNostalgiaPlugin @Inject constructor(
    private val proxy: ProxyServer,
    private val eventManager: EventManager,
    private val playerManager: VelocityPlayerManager
) {

    @Subscribe
    fun onInit(e: ProxyInitializeEvent) {
        NostalgiaCore.init(playerManager, proxy)
        eventManager.register(this, playerManager)
    }

}