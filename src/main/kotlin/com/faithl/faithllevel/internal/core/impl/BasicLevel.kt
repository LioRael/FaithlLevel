package com.faithl.faithllevel.internal.core.impl

import taboolib.module.configuration.Configuration

/**
 * @author Leosouthey
 * @time 2021/12/12-0:58
 *
 * 等级
 *
 * @property conf 等级配置
 * @constructor 构建一个等级
 **/
data class BasicLevel(val conf: Configuration) : TempLevel() {

    val name = conf.getString("name")!!
    val trait = conf.getConfigurationSection("Trait")

}