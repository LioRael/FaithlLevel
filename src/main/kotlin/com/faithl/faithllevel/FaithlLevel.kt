package com.faithl.faithllevel

import com.faithl.faithllevel.api.FaithlLevelAPI
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.*
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendLang
import taboolib.module.metrics.Metrics
import taboolib.platform.BukkitPlugin

object FaithlLevel : Plugin() {

    @Config("settings.yml", migrate = true, autoReload = true)
    lateinit var setting: Configuration
        private set

    val plugin by lazy { BukkitPlugin.getInstance() }

    override fun onLoad() {
        Metrics(13122, pluginVersion, runningPlatform)
    }

    override fun onEnable() {
        console().sendLang("plugin-enabled", pluginVersion, KotlinVersion.CURRENT.toString())
    }

    override fun onDisable() {
        console().sendLang("plugin-disabled")
    }

    @Awake(LifeCycle.LOAD)
    fun reload() {
        if (!FaithlLevelAPI.folderLevel.exists()) {
            releaseResourceFile("levels/example.yml")
            releaseResourceFile("levels/pure-example.yml")
            releaseResourceFile("levels/temp-example.yml")
        }
        if (!FaithlLevelAPI.folderScript.exists()) {
            releaseResourceFile("scripts/example.yml")
        }
        FaithlLevelAPI.reloadLevel()
        FaithlLevelAPI.reloadScript()
    }

}