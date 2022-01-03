package com.faithl.faithllevel.internal.core

/**
 * @author Leosouthey
 * @time 2021/12/18-18:15
 **/
class Function(val level: Level) {

    val data = level.basis?.getConfigurationSection("data")
    val permission = level.basis?.getConfigurationSection("permission")
    val event = level.basis?.getConfigurationSection("event")
    val display = level.trait?.getConfigurationSection("display")
    val booster = level.trait?.getConfigurationSection("booster")
    val attribute = level.trait?.getConfigurationSection("attribute")
    val item = level.trait?.getConfigurationSection("item")

    init {
        Level.levelFunc.add(this)
    }
}