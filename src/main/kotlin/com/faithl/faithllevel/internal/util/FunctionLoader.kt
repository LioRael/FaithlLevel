package com.faithl.faithllevel.internal.util

import taboolib.common.util.asList
import taboolib.library.configuration.ConfigurationSection

object FunctionLoader {

    fun getFunc(value: Int, conf: ConfigurationSection?, func: String): MutableList<String>? {
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