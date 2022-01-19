package com.faithl.level.internal.core.function

import com.faithl.level.api.event.ExpUpdateEvent
import com.faithl.level.api.event.LevelUpdateEvent
import com.faithl.level.internal.core.impl.Basic
import com.faithl.level.internal.data.PlayerIndex
import com.faithl.level.internal.util.condition
import com.faithl.level.internal.util.sendLang
import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.util.getMap

internal object Condition {

    @SubscribeEvent
    fun e(e: LevelUpdateEvent) {
        if (e.level is Basic) {
            e.level.conf?.let { conf ->
                PlayerIndex.getPlayer(e.target)?.let {
                    if (conf is ConfigurationSection) {
                        if (!it.condition(conf.getMapList("Condition")) || it.condition(conf.getMapList("data.level.condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                    if (conf is org.bukkit.configuration.ConfigurationSection) {
                        if (!it.condition(conf.getMapList("Condition")) || it.condition(conf.getMapList("data.level.condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    fun e(e: ExpUpdateEvent) {
        if (e.level is Basic) {
            e.level.conf?.let { conf ->
                PlayerIndex.getPlayer(e.target)?.let {
                    if (conf is ConfigurationSection) {
                        if (!it.condition(conf.getMapList("Condition")) || it.condition(conf.getMapList("data.exp.condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                    if (conf is org.bukkit.configuration.ConfigurationSection) {
                        if (!it.condition(conf.getMapList("Condition")) || it.condition(conf.getMapList("data.exp.condition"))) {
                            it.sendLang(e.level, "not-condition")
                            e.isCancelled = true
                        }
                    }
                }
            }
        }
    }

}
