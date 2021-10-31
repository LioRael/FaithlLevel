package com.faithl.bukkit.faithllevel.internal.level

import com.faithl.bukkit.faithllevel.FaithlLevel

object LevelDataManager {
    var levelMap:MutableMap<String, LevelData> = mutableMapOf()

    init {
        init()
    }

    fun getLevelData(key:String): LevelData? {
        return LevelDataManager.levelMap[key]!!
    }

    fun init() {
        val keys = FaithlLevel.conf.getConfigurationSection("").getKeys(false)
        for (key in keys){
            LevelDataManager.levelMap[key] = LevelData(FaithlLevel.conf.getConfigurationSection(key))
        }
    }
}