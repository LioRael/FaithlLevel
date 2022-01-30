package com.faithl.level.internal.core.function

import com.faithl.level.api.event.ChangeType
import com.faithl.level.api.event.ExpUpdateEvent
import com.faithl.level.api.event.LevelUpdateEvent
import com.faithl.level.internal.core.impl.Basic
import com.faithl.level.internal.util.condition
import com.faithl.level.internal.util.sendLang
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.getProxyPlayer
import taboolib.library.configuration.ConfigurationSection

internal object Condition {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun e(e: LevelUpdateEvent.Before) {
        if (e.level is Basic) {
            kotlin.runCatching {
                getProxyPlayer(e.target)?.let {
                    val config = e.level.config
                    if (config is ConfigurationSection) {
                        if (!it.condition(config.getMapList("Condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                    if (config is org.bukkit.configuration.ConfigurationSection) {
                        if (!it.condition(config.getMapList("Condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    fun e(e: ExpUpdateEvent.Before) {
        if (e.changeType == ChangeType.ADD && e.level is Basic) {
            runCatching {
                val config = e.level.config
                getProxyPlayer(e.target)?.let {
                    if (config is ConfigurationSection) {
                        if (!it.condition(config.getMapList("Condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                    if (config is org.bukkit.configuration.ConfigurationSection) {
                        if (!it.condition(config.getMapList("Condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                }
            }
        }
    }

}
