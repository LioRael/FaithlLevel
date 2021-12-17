package com.faithl.faithllevel.api

import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.Player

@
object FaithlLevelAPI {

    val registeredLevels = HashMap<String, Level>()
    val registeredCommands = HashMap<String, Level>()

    fun getLevel(name: String): Level {
        return registeredLevels[name]!!
    }

    fun getPlayerData(name: String,player: Player): Level.DataManager {
        val level = getLevel(name)
        return Level.getPlayerData(level,player)
    }

}