package com.faithl.faithllevel.api

import com.faithl.faithllevel.internal.core.DataManager
import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.Player
import taboolib.common.platform.function.getDataFolder
import taboolib.common.util.asList
import taboolib.common5.FileWatcher
import taboolib.module.configuration.Configuration
import java.io.File

@Suppress("UNCHECKED_CAST")
object FaithlLevelAPI {

    val registeredLevels = HashMap<String, Level>()

    val registeredScript = HashMap<String, List<String>>()

    val mainLevel = HashMap<String, Level>()

    val folderLevel = File(getDataFolder(), "levels")
    val folderScript = File(getDataFolder(), "scripts")
    val folderTrait = File(getDataFolder(), "traits")

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

    /**
     * 重新加载等级文件
     * 这个操作会清空缓存
     */
    fun reloadLevel() {
        registeredLevels.clear()
        loadLevelFromFile(folderLevel)
    }

    /**
     * 重新加载脚本文件
     * 这个操作会清空缓存
     */
    fun reloadScript() {
        registeredScript.clear()
        loadScriptFromFile(folderLevel)
    }

    /**
     * 从文件中加载等级
     * 这个操作不会清空缓存
     */
    fun loadLevelFromFile(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { loadLevelFromFile(it) }
        } else if (file.name.endsWith(".yml")) {
            val task = Runnable {
                val conf = Configuration.loadFromFile(file)
                try {
                    registeredLevels[conf.getString("Name").toString()] = Level(conf)
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
            task.run()
            FileWatcher.INSTANCE.addSimpleListener(file) {
                task.run()
            }
        }
    }

    /**
     * 从文件中加载脚本
     * 这个操作不会清空缓存
     */
    fun loadScriptFromFile(file: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { loadScriptFromFile(it) }
        } else if (file.name.endsWith(".yml")) {
            val conf = Configuration.loadFromFile(file)
            conf.getKeys(false).forEach { key ->
                registeredScript[key] = conf[key]?.asList() ?: mutableListOf()
            }
        }
    }



}