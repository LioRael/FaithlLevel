package com.faithl.bukkit.faithllevel.internal.level

import taboolib.library.configuration.ConfigurationSection

class LevelData(private val configurationSection:ConfigurationSection) {
    val key:String = configurationSection.name
    val name:String = configurationSection.getString("Name")
    val expGrow: MutableList<String> = configurationSection.getStringList("Exp-Grow")
    val expBottle: ConfigurationSection = configurationSection.getConfigurationSection("Exp-Bottle")
    val levelDisplay: MutableList<String> = configurationSection.getStringList("Level-Display") ?: mutableListOf("null")
}