package com.faithl.faithllevel.internal.core

import org.bukkit.entity.LivingEntity

/**
 * @author Leosouthey
 * @since 2022/1/8-0:45
 **/
abstract class Level {

    abstract fun addExp(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun addLevel(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun takeExp(livingEntity: LivingEntity, value: Int): Boolean
    abstract fun takeLevel(livingEntity: LivingEntity, value: Int): Boolean

}