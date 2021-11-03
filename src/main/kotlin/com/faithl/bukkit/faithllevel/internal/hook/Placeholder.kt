package com.faithl.bukkit.faithllevel.internal.hook

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import org.bukkit.Bukkit.getPlayerExact
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object Placeholder: PlaceholderExpansion {
    override val identifier: String
        get() = "faithl"

    override fun onPlaceholderRequest(player: Player, args: String): String {
        val arr = args.split("_").toMutableList()
        when(arr.size){
            //faithl_value
            1 -> {
                val data = FaithlLevelAPI.getPlayerData(player,
                    FaithlLevelAPI.getLevelData(FaithlLevel.setting.getString("Options.Main-Level"))?:return "Null level"
                )
                when(arr[0]){
                    "exp" -> return data.exp.toString()
                    "level" -> return data.level.toString()
                    "displayLevel" -> return data.getDisplay()
                    "maxExp" -> return data.getMaxExp().toString()
                    "nowExp" -> return "${data.exp}/${data.getMaxExp()}"
                }
            }
            //faithl_level_value
            2 -> {
                val data = FaithlLevelAPI.getPlayerData(player,
                    FaithlLevelAPI.getLevelData(arr[0])!!)
                when(arr[1]){
                    "exp" -> return data.exp.toString()
                    "level" -> return data.level.toString()
                    "displayLevel" -> return data.getDisplay()
                    "maxExp" -> return data.getMaxExp().toString()
                    "nowExp" -> return "${data.exp}/${data.getMaxExp()}"
                }
            }
        }
        //faithl_level_player_value
        if (arr.size >= 3){
            val argument = arr.last()
            arr.remove(argument)
            val level = arr.first()
            arr.remove(level)
            var str = ""
            for (a in arr)
                str = "${str}_${a}"
            val target = getPlayerExact(str.replaceFirst("_",""))?:return "Not online!"
            val data = FaithlLevelAPI.getPlayerData(target, FaithlLevelAPI.getLevelData(level)?:return "Null level")
            when(argument){
                "exp" -> return data.exp.toString()
                "level" -> return data.level.toString()
                "displayLevel" -> return data.getDisplay()
                "maxExp" -> return data.getMaxExp().toString()
                "nowExp" -> return "${data.exp}/${data.getMaxExp()}"
            }
        }
        return "null"
    }
}