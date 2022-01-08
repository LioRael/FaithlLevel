package com.faithl.faithllevel.internal.core.impl

import com.faithl.faithllevel.api.event.ChangeType
import com.faithl.faithllevel.api.event.ExpUpdateEvent
import com.faithl.faithllevel.api.event.LevelUpdateEvent
import com.faithl.faithllevel.internal.core.Level
import com.faithl.faithllevel.internal.core.LevelHandler
import org.bukkit.entity.LivingEntity
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection

/**
 * @author Leosouthey
 * @since 2022/1/8-0:44
 **/
open class TempLevel() : Level() {

    val levelData = mutableMapOf<LivingEntity, Int>()
    val expData = mutableMapOf<LivingEntity, Int>()
    var expIncrease: Any? = null

    init {
        expIncrease = 100
    }

    constructor(conf: ConfigurationSection) : this() {
        expIncrease = conf
    }

    constructor(value: Int) : this() {
        expIncrease = value
    }

    override fun getLevel(livingEntity: LivingEntity): Int {
        return levelData.getOrPut(livingEntity) { 0 }
    }

    override fun getExp(livingEntity: LivingEntity): Int {
        return expData.getOrPut(livingEntity) { 0 }
    }

    override fun setLevel(livingEntity: LivingEntity, value: Int): Boolean {
        val event = LevelUpdateEvent(this, livingEntity, getLevel(livingEntity), value)
        if (event.call()) {
            levelData[livingEntity] = event.newLevel
            return true
        }
        return false
    }

    override fun setExp(livingEntity: LivingEntity, value: Int): Boolean {
        val event = ExpUpdateEvent(this, livingEntity, ChangeType.SET,value)
        if (event.call()
        ) {
            fun task(livingEntity: LivingEntity, value: Int): Boolean {
                if (LevelHandler.isLevelUp(this, livingEntity, value)) {
                    if (!addLevel(livingEntity, 1)) {
                        return false
                    }
                    return task(
                        livingEntity,
                        value - Coerce.toInteger(LevelHandler.getValue(getLevel(livingEntity), expIncrease!!))
                    )
                } else {
                    expData[livingEntity] = event.value
                    return true
                }
            }
            return task(livingEntity, value)
        }
        return false
    }

    override fun addExp(livingEntity: LivingEntity, value: Int): Boolean {
        val event = ExpUpdateEvent(
            this,
            livingEntity,
            ChangeType.ADD,
            getExp(livingEntity).plus(value)
        )
        if (event.call()
        ) {
            fun task(livingEntity: LivingEntity, value: Int): Boolean {
                if (LevelHandler.isLevelUp(this, livingEntity, value)) {
                    if (!addLevel(livingEntity, 1)) {
                        return false
                    }
                    return task(
                        livingEntity,
                        value - Coerce.toInteger(LevelHandler.getValue(getLevel(livingEntity), expIncrease!!))
                    )
                } else {
                    expData[livingEntity] = event.value
                    return true
                }
            }
            return task(livingEntity, value)
        }
        return false
    }

    override fun takeExp(livingEntity: LivingEntity, value: Int): Boolean {
        val event = ExpUpdateEvent(
            this,
            livingEntity,
            ChangeType.TAKE,
            getExp(livingEntity) - value
        )
        if (event.call()
        ) {
            fun task(livingEntity: LivingEntity, value: Int): Boolean {
                if (event.value < 0) {
                    if (!takeLevel(livingEntity, 1)) {
                        return false
                    }
                    return task(
                        livingEntity,
                        Coerce.toInteger(LevelHandler.getValue(getLevel(livingEntity), expIncrease!!)) + value
                    )
                } else {
                    expData[livingEntity] = event.value
                    return true
                }
            }
            return task(livingEntity, value)
        }
        return false
    }

    override fun addLevel(livingEntity: LivingEntity, value: Int): Boolean {
        val event = LevelUpdateEvent(this, livingEntity, getLevel(livingEntity), getLevel(livingEntity).plus(value))
        if (event.call()) {
            levelData[livingEntity] = event.newLevel
            return true
        }
        return false
    }

    override fun takeLevel(livingEntity: LivingEntity, value: Int): Boolean {
        val event = LevelUpdateEvent(this, livingEntity, getLevel(livingEntity), getLevel(livingEntity).minus(value))
        if (event.call()) {
            if (event.newLevel < 0){
                return false
            }
            levelData[livingEntity] = event.newLevel
            return true
        }
        return false
    }

}