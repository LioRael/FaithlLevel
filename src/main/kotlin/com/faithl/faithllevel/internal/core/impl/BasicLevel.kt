package com.faithl.faithllevel.internal.core.impl

import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.LivingEntity
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
data class BasicLevel(val conf: Configuration) : Level() {

    val name = conf.getString("name")!!
    val trait = conf.getConfigurationSection("Trait")

    val levelData = mutableMapOf<LivingEntity, Int>()
    val expData = mutableMapOf<LivingEntity, Int>()

    override fun addExp(livingEntity: LivingEntity, value: Int): Boolean {
        expData[livingEntity]!!.plus(value)
        return true
    }

    override fun addLevel(livingEntity: LivingEntity, value: Int): Boolean {
        levelData[livingEntity]!!.plus(value)
        return true
    }

    override fun takeExp(livingEntity: LivingEntity, value: Int): Boolean {
        expData[livingEntity]!!.minus(value)
        return true
    }

    override fun takeLevel(livingEntity: LivingEntity, value: Int): Boolean {
        levelData[livingEntity]!!.minus(value)
        return true
    }

}