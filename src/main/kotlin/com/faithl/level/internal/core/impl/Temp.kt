package com.faithl.level.internal.core.impl

import com.faithl.level.api.event.ChangeType
import com.faithl.level.api.event.ExpUpdateEvent
import com.faithl.level.api.event.LevelUpdateEvent
import com.faithl.level.internal.core.Level
import com.faithl.level.internal.core.LevelHandler
import com.faithl.level.internal.core.TargetIndex
import taboolib.library.configuration.ConfigurationSection

/**
 * 临时等级，数据不会被保存到数据库
 * 将会在服务器关闭时销毁数据
 *
 * @author Leosouthey
 * @since 2022/1/8-0:44
 **/
open class Temp() : Level() {

    var levelData = mutableMapOf<String, Int>()
    var expData = mutableMapOf<String, Int>()
    var config: Any

    init {
        config = 100
    }

    constructor(conf: Int) : this() {
        config = conf
    }

    constructor(conf: ConfigurationSection) : this() {
        config = conf
    }

    constructor(conf: org.bukkit.configuration.ConfigurationSection) : this() {
        config = conf
    }

    override fun getLevel(target: String): Int {
        val level = levelData.getOrPut(target) { 0 }
        updateLevel(target)
        return level
    }

    override fun getExp(target: String): Int {
        val exp = expData.getOrPut(target) { 0 }
        updateLevel(target)
        return exp
    }

    override fun setLevel(target: String, value: Int): Boolean {
        val event = LevelUpdateEvent(this, target, getLevel(target), value)
        if (event.call()) {
            levelData[target] = event.newLevel
            updateLevel(target)
            return true
        }
        return false
    }

    override fun setExp(target: String, value: Int): Boolean {
        val event = ExpUpdateEvent(this, target, ChangeType.SET, value)
        if (event.call()
        ) {
            expData[target] = value
            updateLevel(target)
            return true
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
            expData[target] = getExp(target) + event.value
            updateLevel(target)
            return true
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
            expData[target] = getExp(target) - event.value
            updateLevel(target)
            return true
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

    fun updateLevel(target: String) {
        while (getExp(target) < 0) {
            takeLevel(target, 1)
            setExp(target, LevelHandler.getNeedExp(target, getLevel(target), config)?.plus(getExp(target)) ?: break)
        }
        while (getExp(target) >= (LevelHandler.getNeedExp(target, getLevel(target), config) ?: return)) {
            addLevel(target, 1)
            setExp(target, getExp(target) - LevelHandler.getNeedExp(target, getLevel(target), config)!!)
        }
    }

}