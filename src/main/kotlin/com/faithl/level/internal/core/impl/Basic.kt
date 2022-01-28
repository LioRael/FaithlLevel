package com.faithl.level.internal.core.impl

import com.faithl.level.internal.core.LevelHandler
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

    init {
        config = 100
    }

    constructor(conf: Int) : this() {
        config = conf
    }

    constructor(conf: ConfigurationSection) : this() {
        config = conf
    }

    constructor(conf: org.bukkit.configuration.ConfigurationSection) : this() {
        config = conf
    }

    fun getDisplay(target: String, type: String, value: Int, level: Int = getLevel(target)): String? {
        return when (config) {
            is Int -> value.toString()
            is org.bukkit.configuration.ConfigurationSection,
            is ConfigurationSection -> Coerce.toString(LevelHandler.getValue(level, config, "data.${type}.display"))
            else -> Coerce.toString(value)
        }
    }

}