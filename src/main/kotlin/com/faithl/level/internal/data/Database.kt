package com.faithl.level.internal.data

import com.faithl.level.FaithlLevel
import com.faithl.level.internal.data.impl.DatabaseError
import com.faithl.level.internal.data.impl.DatabaseSQL
import com.faithl.level.internal.data.impl.DatabaseSQLite
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored

/**
 * @author Leosouthey
 * @since 2022/1/9-6:19
 **/
abstract class Database {

    val name: String
        get() = FaithlLevel.setting.getString("storage.prefix", "faithllevel")!!

    abstract fun getLevel(target: String, level: String): Int
    abstract fun getExp(target: String, level: String): Int
    abstract fun setLevel(target: String, level: String, value: Int)
    abstract fun setExp(target: String, level: String, value: Int)

    abstract fun getObtainExp(target: String, level: String, type: String): Int
    abstract fun getObtainLevel(target: String, level: String, type: String): Int
    abstract fun setObtainExp(target: String, level: String, type: String, obtain: Int)
    abstract fun setObtainLevel(target: String, level: String, type: String, obtain: Int)

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
                e.kickMessage = "&cERROR! The FaithlLevel database failed to initialize.".colored()
            }
        }

    }

}