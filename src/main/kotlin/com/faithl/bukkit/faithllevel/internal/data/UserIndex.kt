package com.faithl.bukkit.faithllevel.internal.data

import com.faithl.bukkit.faithllevel.FaithlLevel

enum class UserIndex {
    NAME, UUID;
    companion object {
        val INSTANCE: UserIndex by lazy {
            try {
                valueOf(FaithlLevel.setting.getString("Database.user-index", "")!!.uppercase())
            } catch (ignored: Throwable) {
                UUID
            }
        }
    }
}