package com.faithl.faithllevel

import com.faithl.faithllevel.api.FaithlLevelAPI
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.*
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics

object FaithlLevel:Plugin() {

    @Config("settings.yml", migrate = true,autoReload = true)
    lateinit var setting: Configuration
        private set

    var placeHolderApi = false

    override fun onLoad() {
        Metrics(13122, pluginVersion, runningPlatform)
    }

    override fun onEnable() {
        reload()
        pluginHooker("AttributePlus")
        pluginHooker("PlaceholderAPI")
        console().sendLang("Plugin-Enabled", pluginVersion,KotlinVersion.CURRENT.toString())
    }

    private fun pluginHooker(plugin: String): Boolean{
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            console().sendLang("Plugin-Hooked",plugin)
            return true
        }
        return false
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