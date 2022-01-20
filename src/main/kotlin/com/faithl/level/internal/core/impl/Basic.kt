package com.faithl.level.internal.core.impl

import com.faithl.level.internal.command.CommandHandler.level
import com.faithl.level.internal.core.LevelHandler
import sun.jvm.hotspot.oops.CellTypeState.value
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection

/**
 * 基础等级
 * 含有大部分基础功能
 *
 * @author Leosouthey
 * @since 2021/12/12-0:58
 **/
open class Basic() : Pure() {

    var conf: Any? = null

    init {
        expIncrease = 100
    }

    constructor(conf: ConfigurationSection) : this() {
        this.conf = conf
        expIncrease = conf.getConfigurationSection("data.increase")
    }

    constructor(conf: org.bukkit.configuration.ConfigurationSection) : this() {
        this.conf = conf
        expIncrease = conf.getConfigurationSection("data.increase")
    }

    constructor(value: Int) : this() {
        expIncrease = value
    }

    fun getDisplay(target: String, type: String, value: Int, level: Int = getLevel(target)): String? {
        if (conf != null) {
            when (conf) {
                is ConfigurationSection -> {
                    val cs = (conf as ConfigurationSection).getConfigurationSection("data.${type}.display")
                    return if (cs != null) {
                        Coerce.toString(LevelHandler.getValue(level, cs))
                    } else {
                        Coerce.toString(value)
                    }
                }
                is org.bukkit.configuration.ConfigurationSection -> {
                    val cs =
                        (conf as org.bukkit.configuration.ConfigurationSection).getConfigurationSection("data.${type}.display")
                    return if (cs != null) {
                        Coerce.toString(LevelHandler.getValue(level, cs))
                    } else {
                        Coerce.toString(value)
                    }
                }
                else -> {
                    return null
                }
            }
        } else {
            return Coerce.toString(value)
        }
    }

}