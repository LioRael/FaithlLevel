package com.faithl.faithllevel.internal.core.impl

import taboolib.module.configuration.Configuration

/**
 * 基础等级
 * 含有大部分基础功能
 *
 * @author Leosouthey
 * @time 2021/12/12-0:58
 **/
data class BasicLevel(val conf: Configuration) : TempLevel() {

    val name = conf.getString("name")!!
    val trait = conf.getConfigurationSection("Trait")

}