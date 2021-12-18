package com.faithl.faithllevel

import com.faithl.faithllevel.api.FaithlLevelAPI
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.Schedule
import taboolib.common.platform.function.*
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics
import java.io.File

object FaithlLevel:Plugin() {

    @Config("settings.yml", migrate = true,autoReload = true)
    lateinit var setting: Configuration

    var placeHolderApi = false

    override fun onLoad() {
        Metrics(13122, pluginVersion, runningPlatform)
    }

    override fun onEnable() {
        reload()
        pluginHooker()
        console().sendLang("Plugin-Enabled", pluginVersion,KotlinVersion.CURRENT.toString())
    }

    fun pluginHooker(){
        if (Bukkit.getPluginManager().isPluginEnabled("AttributePlus")) {
            console().sendLang("Plugin-Hooked","AttributePlus")
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            console().sendLang("Plugin-Hooked","PlaceholderAPI")
        }
    }

    fun reload() {
        if (!FaithlLevelAPI.folderLevel.exists()) {
            releaseResourceFile("levels/example.yml")
        }
        if (!FaithlLevelAPI.folderScript.exists()) {
            releaseResourceFile("scripts/example.yml")
        }
        if (!FaithlLevelAPI.folderTrait.exists()) {
            releaseResourceFile("scripts/")
        }
        FaithlLevelAPI.reloadLevel()
        FaithlLevelAPI.reloadScript()
    }

}