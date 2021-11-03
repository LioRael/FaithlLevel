package com.faithl.bukkit.faithllevel.internal.level

import taboolib.common.platform.function.console
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.lang.sendLang

class Level(private val configurationSection:ConfigurationSection) {
    val key:String? = configurationSection.name
    val name:String? = configurationSection.getString("Name") ?:key
    val expGrow: MutableList<String>? = configurationSection.getStringList("Exp-Grow")
    val expBottle: ConfigurationSection? = configurationSection.getConfigurationSection("Exp-Bottle")
    val levelDisplay: MutableList<String>? = configurationSection.getStringList("Level-Display")
    val commands: ConfigurationSection? = configurationSection.getConfigurationSection("Commands")
    val messages: ConfigurationSection? = configurationSection.getConfigurationSection("Messages")
    val permission: MutableList<String>? = configurationSection.getStringList("Permissions")
    init {
        levels.add(this)
        requireNotNull(key) {
            levels.remove(this)
            //这段简直大聪明
            console().sendLang("Level-Loader-Failed", key!!,"The key is null")
        }
        requireNotNull(expGrow) {
            levels.remove(this)
            console().sendLang("Level-Loader-Failed", key,"The Exp-Grow is null")
        }
    }

    companion object{
        var levels:MutableList<Level> = mutableListOf()
    }
}