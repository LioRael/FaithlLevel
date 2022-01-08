package com.faithl.faithllevel.internal.trait

import com.faithl.faithllevel.api.event.ChangeType
import com.faithl.faithllevel.api.event.ExpUpdateEvent
import com.faithl.faithllevel.api.event.LevelUpdateEvent
import com.faithl.faithllevel.internal.core.impl.BasicLevel
import com.faithl.faithllevel.internal.util.run
import taboolib.common.platform.event.SubscribeEvent

internal object Event {
//
//    @SubscribeEvent
//    fun e(e: LevelUpdateEvent) {
//        if (e.type == ChangeType.ADD) {
//            BasicLevel.getLevelFunc(e.basicLevel).event?.getConfigurationSection("player_level_up")?.run(e.player, e.newLevel)
//        } else if (e.type == ChangeType.TAKE) {
//            BasicLevel.getLevelFunc(e.basicLevel).event?.getConfigurationSection("player_level_down")?.run(e.player, e.newLevel)
//        }
//    }
//
//    @SubscribeEvent
//    fun e(e: ExpUpdateEvent) {
//        if (e.type == ChangeType.ADD) {
//            BasicLevel.getLevelFunc(e.basicLevel).event?.getConfigurationSection("player_exp_up")
//                ?.run(e.player, BasicLevel.getPlayerData(e.basicLevel, e.player).playerLevel)
//        } else if (e.type == ChangeType.TAKE) {
//            BasicLevel.getLevelFunc(e.basicLevel).event?.getConfigurationSection("player_exp_down")
//                ?.run(e.player, BasicLevel.getPlayerData(e.basicLevel, e.player).playerLevel)
//        }
//    }
}