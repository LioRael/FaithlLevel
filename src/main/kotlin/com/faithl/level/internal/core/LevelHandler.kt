package com.faithl.level.internal.core

import com.faithl.level.internal.core.impl.Basic
import com.faithl.level.internal.core.impl.Temp
import com.faithl.level.internal.data.PlayerIndex
import eval
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

    fun getNeedExp(target: String, level: Int, expIncrease: Any, eval: Boolean = false): Int? {
        val result = getValue(level, expIncrease)
        if (result.state == ValueResult.State.FAILURE) {
            if (result.type == ValueResult.Type.LEVEL_MAX){

            }
        }
        val value = Coerce.toString(result.value ?: "0")
        if (value == "max") {
            return null
        }
        try {
            PlayerIndex.getPlayer(target)?.let {
                return if (eval) {
                    Coerce.toInteger(
                        value.replacePlaceholder(it.cast()).eval()
                    )
                } else {
                    Coerce.toInteger(value.replacePlaceholder(it.cast()))
                }
            }
        } catch (e: Throwable) {
            return if (eval) {
                Coerce.toInteger((value ?: "0").eval())
            } else {
                Coerce.toInteger(value ?: "0")
            }
        }
        return 0
    }

    fun getValue(level: Int, data: Any): ValueResult {
        if (data is Int || data is String) {
            return ValueResult(type = ValueResult.Type.FIXED, value = data)
        }
        if (data is ConfigurationSection) {
            val sortedData = data.getKeys(false).sorted()
            for (key in sortedData) {
                if (level <= Coerce.toInteger(key)) {
                    return ValueResult(type = ValueResult.Type.DYNAMIC, value = data[key])
                }
            }
            val fixed = data["fixed"]
            if (fixed != null) {
                return ValueResult(type = ValueResult.Type.FIXED, value = fixed)
            }
            if (level >= Coerce.toInteger(sortedData.last())) {
                ValueResult(state = ValueResult.State.FAILURE, type = ValueResult.Type.LEVEL_MAX)
            }
        }
        if (data is org.bukkit.configuration.ConfigurationSection) {
            val sortedData = data.getKeys(false).sorted()
            for (key in sortedData) {
                if (level <= Coerce.toInteger(key)) {
                    return ValueResult(type = ValueResult.Type.DYNAMIC, value = data[key])
                }
            }
            val fixed = data["fixed"]
            if (fixed != null) {
                return ValueResult(type = ValueResult.Type.FIXED, value = fixed)
            }
            if (level >= Coerce.toInteger(sortedData.last())) {
                ValueResult(state = ValueResult.State.FAILURE, type = ValueResult.Type.LEVEL_MAX)
            }
        }
        return ValueResult(state = ValueResult.State.FAILURE, type = ValueResult.Type.INVALID_TYPE)
    }

    fun isLevelUp(level: Temp, target: String, value: Int): Boolean {
        val expNeeded = getNeedExp(target, level.getLevel(target), level.expIncrease!!, level is Basic)
        if (expNeeded == null) {
            //max
            level
            return false
        }
        val exp = level.getExp(target)
        return exp + value >= expNeeded
    }

}