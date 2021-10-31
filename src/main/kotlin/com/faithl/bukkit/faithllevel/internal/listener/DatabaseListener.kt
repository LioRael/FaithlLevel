package com.faithl.bukkit.faithllevel.internal.listener

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

object DatabaseListener{
    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        e.player.setupDataContainer()
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        FaithlLevelAPI.getLevelDataMap().forEach{
            FaithlLevelAPI.getPlayerData(e.player, it.value).save()
        }
        e.player.releaseDataContainer()
    }
}