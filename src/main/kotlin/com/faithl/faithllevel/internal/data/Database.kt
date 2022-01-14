package com.faithl.faithllevel.internal.data

import com.faithl.faithllevel.FaithlLevel
import com.faithl.faithllevel.api.FaithlLevelAPI
import com.faithl.faithllevel.api.event.ExpUpdateEvent
import com.faithl.faithllevel.api.event.LevelUpdateEvent
import com.faithl.faithllevel.internal.core.Level
import com.faithl.faithllevel.internal.core.impl.PureLevel
import com.faithl.faithllevel.internal.data.impl.DatabaseError
import com.faithl.faithllevel.internal.data.impl.DatabaseSQL
import com.faithl.faithllevel.internal.data.impl.DatabaseSQLite
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerLoginEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
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