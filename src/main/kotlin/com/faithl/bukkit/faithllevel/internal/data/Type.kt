package com.faithl.bukkit.faithllevel.internal.data

import com.faithl.bukkit.faithllevel.FaithlLevel

enum class Type {
    SQLITE, MYSQL;
    companion object {

        val INSTANCE: Type by lazy {
            try {
                valueOf(FaithlLevel.setting.getString("Options.Database.type", "")!!.uppercase())
            } catch (ignored: Throwable) {
                SQLITE
            }
        }
    }
}