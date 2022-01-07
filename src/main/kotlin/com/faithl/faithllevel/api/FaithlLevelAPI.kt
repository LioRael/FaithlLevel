package com.faithl.faithllevel.api

import com.faithl.faithllevel.internal.core.impl.data.BasicDataManager
import com.faithl.faithllevel.internal.core.impl.BasicLevel
import org.bukkit.entity.Player
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.common.util.asList
import taboolib.common5.FileWatcher
import taboolib.module.configuration.Configuration
import java.io.File

@Suppress("UNCHECKED_CAST")
object FaithlLevelAPI {

    /**
     * 已注册的等级系统Map
     */
    val registeredLevels = HashMap<String, BasicLevel>()

    /**
     * 已注册的脚本Map
     */
    val registeredScript = HashMap<String, List<String>>()

    /**
     * 主等级系统
     */
//    val mainLevel =

    /**
     * 文件夹
     */
    val folderLevel = newFile(getDataFolder(), "levels", folder = true)
    val folderScript = newFile(getDataFolder(), "scripts", folder = true)
    val folderTrait = newFile(getDataFolder(), "themes", folder = true)

    /**
     * 注册一个等级系统
     *
     * @param name 名称
     * @param basicLevel 等级系统
     */
    fun registerLevel(name: String, basicLevel: BasicLevel) {
        registeredLevels[name] = basicLevel
    }

    /**
     * 注册一个脚本
     *
     * @param name 名称
     * @param list 脚本
     */
    fun registerScript(name: String, list: List<String>) {
        registeredScript[name] = list
    }

    /**
     * 注销一个等级系统
     *
     * @param name 名称
     */
    fun unRegisterLevel(name: String) {
        registeredLevels.remove(name)
    }

    /**
     * 注销一个脚本
     *
     * @param name 名称
     */
    fun unRegisterScript(name: String) {
        registeredScript.remove(name)
    }

    /**
     * 通过名称获取等级系统
     *
     * @param name 名称
     * @return 等级系统
     */
    fun getLevel(name: String): BasicLevel {
        return registeredLevels[name]!!
    }

    /**
     * 获取玩家数据管理器
     *
     * @param name 名称
     * @param player 玩家
     * @return 数据管理器
     */
    fun getPlayerData(name: String, player: Player): BasicDataManager {
        val level = getLevel(name)
        return BasicLevel.getPlayerData(level, player)
    }

    /**
     * 获取玩家数据管理器
     *
     * @param basicLevel 等级系统
     * @param player 玩家
     * @return 数据管理器
     */
    fun getPlayerData(basicLevel: BasicLevel, player: Player): BasicDataManager {
        return BasicLevel.getPlayerData(basicLevel, player)
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
                    registerLevel(conf.getString("Name")!!, BasicLevel(conf))
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
                registerScript(key, conf[key]?.asList() ?: mutableListOf())
            }
        }
    }

}