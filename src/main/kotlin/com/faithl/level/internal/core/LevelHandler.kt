package com.faithl.level.internal.core

import com.faithl.level.internal.core.impl.Temp
import taboolib.common.util.asList
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection

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

    fun getNextExp(level: Int, data: Any): Int {
        return Coerce.toInteger(getValue(level, data) ?: 0)
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

    fun isLevelUp(level: Temp, String: String, value: Int): Boolean {
        val expNeeded = Coerce.toInteger(LevelHandler.getNextExp(level.getLevel(String), level.expIncrease!!))
        val exp = level.getExp(String)
        return exp + value >= expNeeded
    }

}