package com.faithl.faithllevel.internal.trait

import com.faithl.faithllevel.api.FaithlLevelAPI
import com.faithl.faithllevel.api.event.ExpChangeEvent
import com.faithl.faithllevel.api.event.LevelChangeEvent
import com.faithl.faithllevel.internal.core.Level
import taboolib.common.platform.event.SubscribeEvent

internal object Permission {

    @SubscribeEvent
    fun e(e: LevelChangeEvent) {
        if (e.type == LevelChangeEvent.ChangeType.TAKE) {
            return
        }
        val list = Level.getLevelFunc(e.level).permission?.getStringList("player_level_up") ?: return
        val permission = Level.getPlayerData(e.level, e.player).getLevelValue(e.newLevel, list)
        if (permission != null) {
            if (permission == "null") {
                return
            }
            if (!e.player.hasPermission(permission)) {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: ExpChangeEvent) {
        if (e.type == ExpChangeEvent.ChangeType.TAKE) {
            return
        }
        val list = Level.getLevelFunc(e.level).permission?.getStringList("player_exp_up") ?: return
        val permission = Level.getPlayerData(e.level, e.player)
            .getLevelValue(FaithlLevelAPI.getPlayerData(e.level, e.player).playerLevel, list)
        if (permission != null) {
            if (permission == "null") {
                return
            }
            if (!e.player.hasPermission(permission)) {
                e.isCancelled = true
            }
        }
    }

}