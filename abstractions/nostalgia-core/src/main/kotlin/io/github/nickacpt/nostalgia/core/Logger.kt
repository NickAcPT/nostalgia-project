package io.github.nickacpt.nostalgia.core

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

object Logger {
    var audience: Audience = Audience.empty()

    private val components = mutableMapOf<String, Component>()

    private fun cachingText(s: String) = components.getOrPut(s) { Component.text(s) }

    private val LEFT_BACKET = cachingText("[")
    private val RIGHT_BACKET = cachingText("]:")
    private fun log(prefix: Component, message: Component) {
        // [<prefix>]: <message>
        audience.sendMessage(Component.text {
            it.append(LEFT_BACKET)
            it.append(prefix)
            it.append(RIGHT_BACKET)
            it.appendSpace()
            it.append(message)
        })
    }

    fun debug(message: Component) = log(cachingText("DEBUG"), message)
    fun debug(message: String) = debug(Component.text(message))

    fun info(message: Component) = log(cachingText("INFO"), message)
    fun info(message: String) = info(Component.text(message))

    fun warn(message: Component) = log(cachingText("WARN"), message)
    fun warn(message: String) = warn(Component.text(message))

    fun error(message: Component) = log(cachingText("ERROR"), message)
    fun error(message: String) = error(Component.text(message))
}