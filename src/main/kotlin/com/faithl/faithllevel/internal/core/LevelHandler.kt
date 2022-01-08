package com.faithl.faithllevel.internal.core

import com.faithl.faithllevel.internal.core.impl.TempLevel
import org.bukkit.entity.LivingEntity
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection

/**
 * 等级处理工具
 *
 * @author Leosouthey
 * @since 2022/1/8-22:57
 **/
object LevelHandler {

    fun getValue(level: Int, data: Any): Any? {
        if (data is Int) {
            return data
        }
        if (data is ConfigurationSection) {
            var keyTemp = 0
            for (key in data.getKeys(false)) {
                if (level in keyTemp..Coerce.toInteger(key)) {
                    return data[key]
                }
                keyTemp = Coerce.toInteger(key)
            }
            return data["fixed"]
        }
        return null
    }

    fun isLevelUp(level: TempLevel, livingEntity: LivingEntity, value: Int): Boolean {
        val expNeeded = Coerce.toInteger(LevelHandler.getValue(level.getLevel(livingEntity), level.expIncrease!!))
        val exp = level.getExp(livingEntity)
        return exp + value >= expNeeded
    }

}