package com.faithl.bukkit.faithllevel.api

import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import com.faithl.bukkit.faithllevel.internal.level.LevelData
import com.faithl.bukkit.faithllevel.internal.level.LevelDataManager
import org.bukkit.entity.Player
import taboolib.common.platform.Awake

@Awake
object FaithlLevelAPI {
    fun getPlayerData(player: Player,levelData:LevelData): ExpDataManager {
        System.gc()
        return ExpDataManager(player, levelData)
    }

    fun getLevelData(key:String): LevelData? {
        return LevelDataManager.getLevelData(key)
    }

    fun getLevelDataMap(): MutableMap<String,LevelData> {
        return LevelDataManager.levelMap
    }
}