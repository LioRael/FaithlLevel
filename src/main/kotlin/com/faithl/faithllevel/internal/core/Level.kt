package com.faithl.faithllevel.internal.core

import org.bukkit.entity.LivingEntity

/**
 * 等级抽象类
 *
 * @author Leosouthey
 * @since 2022/1/8-0:45
 **/
abstract class Level {

    abstract fun getLevel(livingEntity: LivingEntity): Int
    abstract fun getExp(livingEntity: LivingEntity): Int
    abstract fun setLevel(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun setExp(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun addExp(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun addLevel(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun takeExp(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun takeLevel(livingEntity: LivingEntity, value: Int): Boolean

}