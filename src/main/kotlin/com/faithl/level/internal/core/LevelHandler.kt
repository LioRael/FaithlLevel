package com.faithl.level.internal.core

import com.faithl.level.internal.core.impl.Basic
import com.faithl.level.internal.core.impl.Temp
import com.faithl.level.internal.data.PlayerIndex
import com.faithl.level.internal.util.eval
import com.faithl.level.internal.util.sendMessage
import taboolib.common.util.asList
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.chat.colored
import taboolib.module.lang.sendLang
import taboolib.platform.compat.replacePlaceholder

/**
 * 等级处理工具
 *
 * @author Leosouthey
 * @since 2022/1/8-22:57
 **/
object LevelHandler {

    fun getNeedExp(target: String, levelData: Int, data: Any, eval: Boolean = false): Int? {
        val result = getValue(levelData, data, "data.increase")
        val index = result.targetIndex
        if (result.state == ValueResult.State.FAILURE) {
            if (index == TargetIndex.PLAYER) {
                val player = PlayerIndex.getPlayer(target)!!
                if (result.reason == ValueResult.Reason.LEVEL_MAX) {
                    getValue(levelData, data, "event.player-level-max.message").value?.let {
                        if (it is ConfigurationSection) {
                            it.sendMessage(player)
                        }
                    }
                } else {
                    player.sendLang("error-invalid-config-node", "data.increase")
                }
            }
            return null
        }
        return if (index == TargetIndex.PLAYER) {
            PlayerIndex.getPlayer(target)?.let {
                if (eval) {
                    Coerce.toInteger(
                        (result.value as String).replacePlaceholder(it.cast()).eval()
                    )
                } else {
                    Coerce.toInteger((result.value as String).replacePlaceholder(it.cast()))
                }
            }
        } else {
            if (eval) {
                Coerce.toInteger(
                    (result.value as String).eval()
                )
            } else {
                Coerce.toInteger(result.value as String)
            }
        }
    }

    fun getValue(level: Int, data: Any, node: String): ValueResult {
        if (data is Int || data is String) {
            return ValueResult(ValueResult.State.SUCCESS, TargetIndex.PLAYER, data)
        }
        if (data is ConfigurationSection) {
            val sortedData = data.getConfigurationSection(node)?.getKeys(false)?.sorted()
            val index = data.getString("index") ?: "player"
            if (sortedData != null) {
                for (key in sortedData) {
                    if (level <= Coerce.toInteger(key)) {
                        return ValueResult(ValueResult.State.SUCCESS, TargetIndex.valueOf(index), data[key])
                    }
                }
                val fixed = data["fixed"]
                if (fixed != null) {
                    return ValueResult(ValueResult.State.SUCCESS, TargetIndex.valueOf(index), fixed)
                }
                if (level >= Coerce.toInteger(sortedData.last())) {
                    ValueResult(
                        ValueResult.State.FAILURE,
                        TargetIndex.valueOf(index),
                        null,
                        ValueResult.Reason.LEVEL_MAX
                    )
                }
            }
        }
        if (data is org.bukkit.configuration.ConfigurationSection) {
            val sortedData = data.getConfigurationSection(node)?.getKeys(false)?.sorted()
            val index = data.getString("index") ?: "player"
            if (sortedData != null) {
                for (key in sortedData) {
                    if (level <= Coerce.toInteger(key)) {
                        return ValueResult(ValueResult.State.SUCCESS, TargetIndex.valueOf(index), data[key])
                    }
                }
                val fixed = data["fixed"]
                if (fixed != null) {
                    return ValueResult(ValueResult.State.SUCCESS, TargetIndex.valueOf(index), fixed)
                }
                if (level >= Coerce.toInteger(sortedData.last())) {
                    ValueResult(
                        ValueResult.State.FAILURE,
                        TargetIndex.valueOf(index),
                        null,
                        ValueResult.Reason.LEVEL_MAX
                    )
                }
            }
        }
        return ValueResult(ValueResult.State.FAILURE, TargetIndex.PLAYER, null, ValueResult.Reason.INVALID_TYPE)
    }

    fun isLevelUp(level: Temp, target: String, value: Int): Boolean {
        val expNeeded = getNeedExp(target, level.getLevel(target), level.config!!, level is Basic) ?: return false
        val exp = level.getExp(target)
        return exp + value >= expNeeded
    }

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

}