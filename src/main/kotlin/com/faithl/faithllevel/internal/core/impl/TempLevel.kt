package com.faithl.faithllevel.internal.core.impl

import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.LivingEntity

/**
 * @author Leosouthey
 * @since 2022/1/8-20:42
 **/
class TempLevel : Level() {

    val levelData = mutableMapOf<LivingEntity, Int>()
    val expData = mutableMapOf<LivingEntity, Int>()

    override fun addExp(livingEntity: LivingEntity, value: Int): Boolean {
        expData[livingEntity]?.plus(value)
        return true
    }

    override fun takeExp(livingEntity: LivingEntity, value: Int): Boolean {
        expData[livingEntity]?.minus(value)
        return true
    }

    override fun addLevel(livingEntity: LivingEntity, value: Int): Boolean {
        levelData[livingEntity]?.plus(value)
        return true
    }

    override fun takeLevel(livingEntity: LivingEntity, value: Int): Boolean {
        levelData[livingEntity]?.minus(value)
        return true
    }

}