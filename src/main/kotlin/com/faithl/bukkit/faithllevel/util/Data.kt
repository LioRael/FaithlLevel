package com.faithl.bukkit.faithllevel.util

import com.faithl.bukkit.faithllevel.internal.level.LevelData
import org.bukkit.entity.Player
import taboolib.common5.Coerce
import taboolib.expansion.getDataContainer
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

fun Player.getFExp(levelData:LevelData): Int {
    releaseDataContainer()
    setupDataContainer()
    return Coerce.toInteger(getDataContainer()["${levelData.key}_exp"])
}

fun Player.getFLevel(levelData:LevelData): Int {
    releaseDataContainer()
    setupDataContainer()
    return Coerce.toInteger(getDataContainer()["${levelData.key}_level"])
}

fun Player.setFExp(levelData:LevelData,value:Int){
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${levelData.key}_exp"]=Coerce.toInteger(value)
}

fun Player.setFLevel(levelData:LevelData,value:Int){
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${levelData.key}_level"]=Coerce.toInteger(value)
}