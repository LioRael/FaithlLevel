package com.faithl.bukkit.faithllevel.api

import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player
import taboolib.common.platform.Awake

@Awake
object FaithlLevelAPI {
    fun getPlayerData(player: Player, level:Level): ExpDataManager {
        System.gc()
        return ExpDataManager(player, level)
    }

    fun getLevelData(key:String): Level? {
        return Level.levels.find {
            it.key == key
        }
    }
}