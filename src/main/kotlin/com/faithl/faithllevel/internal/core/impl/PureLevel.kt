package com.faithl.faithllevel.internal.core.impl

import taboolib.library.configuration.ConfigurationSection

/**
 * 纯净等级，仅含有等级/经验的增减功能
 * 数据将会保存到数据库中
 *
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