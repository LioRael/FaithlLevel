package com.faithl.level.internal.core

import com.faithl.level.internal.core.impl.Basic
import com.faithl.level.internal.core.impl.Temp
import com.faithl.level.internal.data.PlayerIndex
import com.faithl.level.internal.util.count
import taboolib.common.util.asList
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection
import taboolib.platform.compat.replacePlaceholder

/**
 * 等级处理工具
 *
 * @author Leosouthey
 * @since 2022/1/8-22:57
 **/
object LevelHandler {

    fun getValueList(value: Int, conf: ConfigurationSection?, func: String): MutableList<String>? {
        val result: MutableList<String> = mutableListOf()
        val keys = conf?.getConfigurationSection(func)?.getKeys(false) ?: return null
        for (key in keys) {
            if (key.contains("e") || key.contains("every_level")) {
                result += conf[key]?.asList() ?: mutableListOf()
                continue
            }
            if (key.contains("-")) {
                val min = key.split("-").toTypedArray()[0].toInt()
                val max = key.split("-").toTypedArray()[1].toInt()
                if (value in min..max) {
                    result += conf["${min}-${max}"]?.asList() ?: mutableListOf()
                    continue
                }
            } else if (value == key.toInt()) {
                result += conf[key]?.asList() ?: mutableListOf()
                continue
            }
        }
        return result
    }

    fun getNextExp(target: String, level: Int, expIncrease: Any, count: Boolean = false): Int {
        val player = PlayerIndex.getPlayer(target)
        return if (player != null) {
            if (count) {
                Coerce.toInteger(
                    Coerce.toString(getValue(level, expIncrease) ?: "0").replacePlaceholder(player.cast()).count()
                )
            } else {
                Coerce.toInteger(Coerce.toString(getValue(level, expIncrease) ?: "0").replacePlaceholder(player.cast()))
            }
        } else {
            if (count) {
                Coerce.toInteger(Coerce.toString(getValue(level, expIncrease) ?: "0").count())
            } else {
                Coerce.toInteger(Coerce.toString(getValue(level, expIncrease) ?: "0"))
            }
        }
    }

    fun getValue(level: Int, data: Any): Any? {
        if (data is Int || data is String) {
            return data
        }
        if (data is ConfigurationSection) {
            var keyTemp = 0
            for (key in data.getKeys(false)) {
                if (level in keyTemp until Coerce.toInteger(key)) {
                    return data[key]
                }
                keyTemp = Coerce.toInteger(key)
            }
            return data["fixed"]
        }
        if (data is org.bukkit.configuration.ConfigurationSection) {
            var keyTemp = 0
            for (key in data.getKeys(false)) {
                if (level in keyTemp until Coerce.toInteger(key)) {
                    return data[key]
                }
                keyTemp = Coerce.toInteger(key)
            }
            return data["fixed"]
        }
        return null
    }

    fun isLevelUp(level: Temp, target: String, value: Int): Boolean {
        val expNeeded = getNextExp(target, level.getLevel(target), level.expIncrease!!,level is Basic)
        val exp = level.getExp(target)
        return exp + value >= expNeeded
    }

}