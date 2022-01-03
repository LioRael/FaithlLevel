package com.faithl.faithllevel.internal.trait

import com.faithl.faithllevel.api.event.ExpChangeEvent
import com.faithl.faithllevel.api.event.LevelChangeEvent
import com.faithl.faithllevel.internal.core.Level
import com.faithl.faithllevel.internal.util.run
import taboolib.common.platform.event.SubscribeEvent

internal object Event {

    @SubscribeEvent
    fun e(e: LevelChangeEvent) {
        if (e.type == LevelChangeEvent.ChangeType.ADD) {
            Level.getLevelFunc(e.level).event?.getConfigurationSection("player_level_up")?.run(e.player, e.newLevel)
        } else if (e.type == LevelChangeEvent.ChangeType.TAKE) {
            Level.getLevelFunc(e.level).event?.getConfigurationSection("player_level_down")?.run(e.player, e.newLevel)
        }
    }

    @SubscribeEvent
    fun e(e: ExpChangeEvent) {
        if (e.type == ExpChangeEvent.ChangeType.ADD) {
            Level.getLevelFunc(e.level).event?.getConfigurationSection("player_exp_up")
                ?.run(e.player, Level.getPlayerData(e.level, e.player).playerLevel)
        } else if (e.type == ExpChangeEvent.ChangeType.TAKE) {
            Level.getLevelFunc(e.level).event?.getConfigurationSection("player_exp_down")
                ?.run(e.player, Level.getPlayerData(e.level, e.player).playerLevel)
        }
    }
}