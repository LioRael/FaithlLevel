package com.faithl.bukkit.faithllevel.internal.conf

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import taboolib.common.platform.function.releaseResourceFile
import taboolib.library.configuration.YamlConfiguration
import taboolib.platform.util.sendLang
import java.io.File

object Loader {
    private val folder by lazy {
        Level.levels.clear()
        val folder = File(FaithlLevel.plugin.dataFolder, "levels")
        if (!folder.exists()) {
            arrayOf(
                "示例主等级.yml",
                "其它示例等级.yml",
            ).forEach { releaseResourceFile("levels/$it", true) }
        }
        folder
    }

    fun loadLevels(sender: CommandSender = Bukkit.getConsoleSender()) {
        val files = mutableListOf<File>().also {
            it.addAll(filterMenuFiles(folder))
            it.addAll(FaithlLevel.setting.getStringList("Loader.Level-Files").flatMap { filterMenuFiles(File(it)) })
        }
        val tasks = mutableListOf<File>().also { tasks ->
            files.forEach { file ->
                if (!tasks.any { it.nameWithoutExtension == file.nameWithoutExtension })
                    tasks.add(file)
            }
        }
        val serializingTime = System.currentTimeMillis()
        tasks.forEach {
            val keys = YamlConfiguration.loadConfiguration(it).getConfigurationSection("").getKeys(false)
            for (key in keys){
                if (FaithlLevelAPI.getLevelData(key) == null)
                    Level(YamlConfiguration.loadConfiguration(it).getConfigurationSection(key))
                else
                    sender.sendLang("Level-Loader-Failed","${it.name}.${key}","The same key exists")
            }
        }
        sender.sendLang("Level-Loader-Loaded", Level.levels.size, System.currentTimeMillis() - serializingTime)
    }

    private fun filterMenuFiles(file: File): List<File> {
        return mutableListOf<File>().run {
            if (file.isDirectory) {
                file.listFiles()?.forEach {
                    addAll(filterMenuFiles(it))
                }
            } else if (!file.name.startsWith("#")) {
                add(file)
            }
            this
        }
    }
}