package com.faithl.faithllevel.internal.core.impl

import taboolib.library.configuration.ConfigurationSection

/**
 * @author Leosouthey
 * @since 2022/1/8-20:42
 **/
class PureLevel() : TempLevel() {

    init {
        expIncrease = 100
    }

    constructor(conf: ConfigurationSection) : this() {
        expIncrease = conf
    }

    constructor(value: Int) : this() {
        expIncrease = value
    }

}