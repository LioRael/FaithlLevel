package com.faithl.level.internal.core.impl

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

}