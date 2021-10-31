package com.faithl.bukkit.faithllevel.internal.conf

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.internal.level.LevelData

object CommandLoader {
    fun getCommand(value:Int,event:String,levelData: LevelData):MutableList<String>{
        val result:MutableList<String> = mutableListOf()
        if (FaithlLevel.command.getConfigurationSection(levelData.key)==null)
            return mutableListOf("null")
        val keys = FaithlLevel.command.getConfigurationSection(levelData.key).getKeys(false) ?: return mutableListOf("null")
        for (key in keys){
            if (key.contains("Every-Level")){
                result += FaithlLevel.command.getStringList("${levelData.key}.${key}.${event}") ?: mutableListOf("null")
                continue
            }
            if (key.contains("-")){
                val min = key.split("-").toTypedArray()[0].toInt()
                val max = key.split("-").toTypedArray()[1].toInt()
                if (value in min..max){
                    result += FaithlLevel.command.getStringList("${levelData.key}.${min}-${max}.${event}") ?: mutableListOf("null")
                    return result
                }
            }else if (value == key.toInt()){
                result += FaithlLevel.command.getStringList("${levelData.key}.${key}.${event}") ?: mutableListOf("null")
                return result
            }
        }
        return mutableListOf("null")
    }
}