package com.faithl.faithllevel.api

import com.faithl.faithllevel.internal.core.DataManager
import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.Player
import taboolib.common.util.asList
import taboolib.module.configuration.Configuration
import java.io.File

@Suppress("UNCHECKED_CAST")
object FaithlLevelAPI {

    val registeredLevels = HashMap<String, Level>()
    val registeredScript = HashMap<String, List<String>>()
    val mainLevel = HashMap<String, Level>()

    fun getLevel(name: String): Level {
        return registeredLevels[name]!!
    }

    fun getPlayerData(name: String,player: Player): DataManager {
        val level = getLevel(name)
        return Level.getPlayerData(level,player)
    }

    fun getPlayerData(level: Level,player: Player): DataManager {
        return Level.getPlayerData(level,player)
    }

    fun loadScriptFile(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { loadScriptFile(it) }
        } else if (file.name.endsWith(".yml")) {
            val conf = Configuration.loadFromFile(file)
            conf.getKeys(false).forEach { key ->
                registeredScript[key.substring(0, key.length - 1)] = conf[key]?.asList() ?: mutableListOf()
            }
        }
    }

}