package com.faithl.bukkit.faithllevel.internal.listener

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
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
        Level.levels.forEach{
            FaithlLevelAPI.getPlayerData(e.player, it).save()
        }
        e.player.releaseDataContainer()
    }

    @SubscribeEvent
    fun e(e: PlayerKickEvent) {
        Level.levels.forEach{
            FaithlLevelAPI.getPlayerData(e.player, it).save()
        }
        e.player.releaseDataContainer()
    }
}