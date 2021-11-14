package com.faithl.bukkit.faithllevel.internal.level

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.internal.compat.attribute.Attribute
import org.serverct.ersha.AttributePlus
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
    val ap: ConfigurationSection? = configurationSection.getConfigurationSection("AttributePlus")
    val permissionBooster: MutableList<String>? = configurationSection.getStringList("Exp-Permission-Booster")
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
        if (levels.contains(this) && FaithlLevel.ap && ap!=null){
            AttributePlus.attributeManager.increaseKey(ap.getString("Lore"))
            Attribute(ap.getString("Exp-Booster-Attribute"),ap.getString("Exp-Booster-Placeholder"))
        }
    }

    companion object{
        var levels:MutableList<Level> = mutableListOf()
    }
}