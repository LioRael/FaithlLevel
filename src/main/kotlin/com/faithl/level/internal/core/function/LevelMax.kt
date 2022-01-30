package com.faithl.level.internal.core.function

import com.faithl.level.api.event.ChangeType
import com.faithl.level.api.event.ExpUpdateEvent
import com.faithl.level.api.event.LevelUpdateEvent
import com.faithl.level.internal.core.LevelHandler
import com.faithl.level.internal.core.impl.Temp
import com.faithl.level.internal.util.sendMessage
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.getProxyPlayer
import taboolib.library.configuration.ConfigurationSection

object LevelMax {

    @SubscribeEvent
    fun e(e: ExpUpdateEvent.Before) {
        if (e.changeType == ChangeType.ADD) {
            if (e.level is Temp) {
                if (LevelHandler.getNeedExp(e.target, e.level.getLevel(e.target), e.level.config) == null) {
                    getProxyPlayer(e.target)?.let {
                        val message = LevelHandler.getNodeValue(e.level.config, "event.player-level-max.message")
                        if (message != null) {
                            if (message is ConfigurationSection) {
                                message.sendMessage(it)
                            }
                            if (message is org.bukkit.configuration.ConfigurationSection) {
                                message.sendMessage(it)
                            }
                        }
                    }
                    e.isCancelled = (LevelHandler.getNodeValue(e.level.config, "overflow") ?: false) as Boolean
                }
            }
        }
    }

    @SubscribeEvent
    fun e(e: LevelUpdateEvent.Before) {
        if (e.newLevel - e.oldLevel > 0) {
            if (e.level is Temp) {
                if (LevelHandler.getNeedExp(e.target, e.oldLevel, e.level.config) == null) {
                    getProxyPlayer(e.target)?.let {
                        val message = LevelHandler.getNodeValue(e.level.config, "event.player-level-max.message")
                        if (message != null) {
                            if (message is ConfigurationSection) {
                                message.sendMessage(it)
                            }
                            if (message is org.bukkit.configuration.ConfigurationSection) {
                                message.sendMessage(it)
                            }
                        }
                    }
                    e.isCancelled = true
                }
            }
        }
    }

}