package com.faithl.bukkit.faithllevel.util

import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player
import taboolib.common5.Coerce
import taboolib.expansion.getDataContainer
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

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

fun Player.setFExp(level:Level, value:Int){
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${level.key}_exp"]=Coerce.toInteger(value)
}

fun Player.setFLevel(level:Level, value:Int){
    releaseDataContainer()
    setupDataContainer()
    getDataContainer()["${level.key}_level"]=Coerce.toInteger(value)
}