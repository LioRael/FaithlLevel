package com.faithl.faithllevel.internal.core.impl

import com.faithl.faithllevel.api.event.ChangeType
import com.faithl.faithllevel.api.event.ExpUpdateEvent
import com.faithl.faithllevel.api.event.LevelUpdateEvent
import com.faithl.faithllevel.internal.core.Level
import com.faithl.faithllevel.internal.core.LevelHandler
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection

/**
 * 临时等级，数据不会被保存到数据库
 * 服务器重启数据将会清除
 *
 * @author Leosouthey
 * @since 2022/1/8-0:44
 **/
open class TempLevel() : Level() {

    val levelData = mutableMapOf<String, Int>()
    val expData = mutableMapOf<String, Int>()
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

    override fun getLevel(target: String): Int {
        return levelData.getOrPut(target) { 0 }
    }

    override fun getExp(target: String): Int {
        return expData.getOrPut(target) { 0 }
    }

    override fun setLevel(target: String, value: Int): Boolean {
        val event = LevelUpdateEvent(this, target, getLevel(target), value)
        if (event.call()) {
            levelData[target] = event.newLevel
            return true
        }
        return false
    }

    override fun setExp(target: String, value: Int): Boolean {
        val event = ExpUpdateEvent(this, target, ChangeType.SET, value)
        if (event.call()
        ) {
            fun task(target: String, value: Int): Boolean {
                if (LevelHandler.isLevelUp(this, target, value)) {
                    if (!addLevel(target, 1)) {
                        return false
                    }
                    return task(
                        target,
                        value - Coerce.toInteger(LevelHandler.getValue(getLevel(target) - 1, expIncrease!!))
                    )
                } else {
                    expData[target] = value
                    return true
                }
            }
            return task(target, event.value)
        }
        return false
    }

    override fun addExp(target: String, value: Int): Boolean {
        val event = ExpUpdateEvent(
            this,
            target,
            ChangeType.ADD,
            value
        )
        if (event.call()
        ) {
            fun task(target: String, value: Int): Boolean {
                if (LevelHandler.isLevelUp(this, target, value)) {
                    if (!addLevel(target, 1)) {
                        return false
                    }
                    return task(
                        target,
                        value - Coerce.toInteger(LevelHandler.getValue(getLevel(target) - 1, expIncrease!!))
                    )
                } else {
                    expData[target] = getExp(target) + value
                    return true
                }
            }
            return task(target, event.value)
        }
        return false
    }

    override fun takeExp(target: String, value: Int): Boolean {
        val event = ExpUpdateEvent(
            this,
            target,
            ChangeType.TAKE,
            value
        )
        if (event.call()
        ) {
            fun task(target: String, value: Int): Boolean {
                if (getExp(target) - value < 0) {
                    if (!takeLevel(target, 1)) {
                        return false
                    }
                    return task(
                        target,
                        value - Coerce.toInteger(LevelHandler.getValue(value,expIncrease!!))
                    )
                } else {
                    expData[target] = getExp(target) - value
                    return true
                }
            }
            return task(target, event.value)
        }
        return false
    }

    override fun addLevel(target: String, value: Int): Boolean {
        val event = LevelUpdateEvent(this, target, getLevel(target), getLevel(target).plus(value))
        if (event.call()) {
            levelData[target] = event.newLevel
            return true
        }
        return false
    }

    override fun takeLevel(target: String, value: Int): Boolean {
        val event = LevelUpdateEvent(this, target, getLevel(target), getLevel(target).minus(value))
        if (event.call()) {
            if (event.newLevel < 0) {
                return false
            }
            levelData[target] = event.newLevel
            return true
        }
        return false
    }

}