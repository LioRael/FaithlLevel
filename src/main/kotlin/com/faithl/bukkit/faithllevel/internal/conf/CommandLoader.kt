package com.faithl.bukkit.faithllevel.internal.conf

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.internal.level.Level

object CommandLoader {
    fun getCommand(value:Int, event:String, level: Level):MutableList<String>?{
        val result:MutableList<String> = mutableListOf()
        val keys = level.commands?.getKeys(false) ?: return null
        for (key in keys){
            if (key.contains("Every-Level")){
                result += level.commands.getStringList("${key}.${event}") ?: mutableListOf()
                continue
            }
            if (key.contains("-")){
                val min = key.split("-").toTypedArray()[0].toInt()
                val max = key.split("-").toTypedArray()[1].toInt()
                if (value in min..max){
                    result += level.commands.getStringList("${min}-${max}.${event}") ?: mutableListOf()
                    continue
                }
            }else if (value == key.toInt()){
                result += level.commands.getStringList("${key}.${event}") ?: mutableListOf()
                continue
            }
        }
        return result
    }
}