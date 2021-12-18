package com.faithl.faithllevel

import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.pluginVersion
import taboolib.common.platform.function.runningPlatform
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics

object FaithlLevel:Plugin() {

    @Config("settings.yml", migrate = true,autoReload = true)
    lateinit var setting: Configuration

    var placeHolderApi = false

    override fun onLoad() {
        Metrics(13122, pluginVersion, runningPlatform)
    }

    override fun onEnable() {
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

}