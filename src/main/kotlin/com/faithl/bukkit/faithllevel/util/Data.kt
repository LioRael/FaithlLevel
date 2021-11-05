package com.faithl.bukkit.faithllevel.util

import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player
import taboolib.common5.Coerce
import taboolib.expansion.getDataContainer
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

fun Player.getBooster(level:Level):Double {
    releaseDataContainer()
    setupDataContainer()
    return getDataContainer()["${level.key}_boot"]?.toDoubleOrNull() ?: return 0.0
}

fun Player.getFExp(level:Level): Int {
    releaseDataContainer()
    setupDataContainer()
    return Coerce.toInteger(getDataContainer()["${level.key}_exp"])
}

fun Player.getFLevel(level:Level): Int {
    releaseDataContainer()
    setupDataContainer()
    return Coerce.toInteger(getDataContainer()["${level.key}_level"])
}

fun Player.setBooster(level:Level,value:Double) {
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${level.key}_boot"]=value
}

fun Player.setFExp(level:Level, value:Int){
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${level.key}_exp"]=value
}

fun Player.setFLevel(level:Level, value:Int){
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${level.key}_level"]=value
}