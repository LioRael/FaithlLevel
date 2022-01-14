package com.faithl.faithllevel.internal.core.impl

import taboolib.library.configuration.ConfigurationSection

/**
 * 基础等级
 * 含有大部分基础功能
 *
 * @author Leosouthey
 * @since 2021/12/12-0:58
 **/
open class BasicLevel() : PureLevel() {

    init {
        expIncrease = 100
    }

    constructor(conf: ConfigurationSection) : this() {
        expIncrease = conf
    }

    constructor(conf: org.bukkit.configuration.ConfigurationSection) : this() {
        expIncrease = conf
    }

    constructor(value: Int) : this() {
        expIncrease = value
    }

}