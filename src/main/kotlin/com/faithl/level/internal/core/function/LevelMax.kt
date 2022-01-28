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
    fun e(e: ExpUpdateEvent) {
        if (e.changeType == ChangeType.ADD) {
            if (e.level is Temp) {
                if (LevelHandler.getNeedExp(e.target, e.level.getLevel(e.target) + 1, e.level.config, true) == null) {
                    getProxyPlayer(e.target)?.let {
                        LevelHandler.getValue(
                            e.level.getLevel(e.target),
                            e.level.config,
                            "event.player-level-max.message"
                        ).value?.let { message ->
                            if (message is ConfigurationSection) {
                                message.sendMessage(it)
                            }
                            e.isCancelled = (LevelHandler.getNodeValue(e.level.config, "overflow") ?: false) as Boolean
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    fun e(e: LevelUpdateEvent) {
        if (e.newLevel - e.oldLevel > 0) {
            if (e.level is Temp) {
                if (LevelHandler.getNeedExp(e.target, e.newLevel, e.level.config, true) == null) {
                    getProxyPlayer(e.target)?.let {
                        LevelHandler.getValue(
                            e.newLevel,
                            e.level.config,
                            "event.player-level-max.message"
                        ).value?.let { message ->
                            if (message is ConfigurationSection) {
                                message.sendMessage(it)
                            }
                            e.isCancelled = true
                        }
                    }
                }
            }
        }
    }

}