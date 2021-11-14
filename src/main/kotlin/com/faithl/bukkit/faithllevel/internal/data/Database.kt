package com.faithl.bukkit.faithllevel.internal.data

import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerLoginEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored

abstract class Database {

    abstract fun getLevel(player: Player,level: Level): Int

    abstract fun getExp(player: Player,level: Level): Int

    abstract fun getBooster(player: Player,level: Level): Double

    abstract fun setLevel(player: Player,level: Level, value: Int)

    abstract fun setExp(player: Player,level: Level, value: Int)

    abstract fun setBooster(player: Player,level: Level, value: Double)

    companion object {
        val INSTANCE by lazy {
            try {
                when (Type.INSTANCE) {
                    Type.MYSQL -> DatabaseSQL()
                    Type.SQLITE -> DatabaseSQLite()
                }
            } catch (e: Throwable) {
                DatabaseError(e)
            }
        }

        @SubscribeEvent
        fun e(e: PlayerLoginEvent) {
            if (INSTANCE is DatabaseError) {
                e.result = PlayerLoginEvent.Result.KICK_OTHER
                e.kickMessage = "&4&loERROR! &r&oThe &4&lFaithlLevel&r&o database failed to initialize.".colored()
            }
        }
    }
}