package com.faithl.bukkit.faithllevel.internal.data

import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player

class DatabaseError(val cause: Throwable): Database(){
    init {
        cause.printStackTrace()
    }

    override fun getLevel(player: Player, level: Level): Int {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun getExp(player: Player, level: Level): Int {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun getBooster(player: Player, level: Level): Double {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun setLevel(player: Player, level: Level, value: Int) {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun setExp(player: Player, level: Level, value: Int) {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun setBooster(player: Player, level: Level, value: Double) {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }
}