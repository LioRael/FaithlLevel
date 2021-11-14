package com.faithl.bukkit.faithllevel.internal.data

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.database.*
import taboolib.platform.compat.VaultService.permission
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

class DatabaseSQL: Database() {
    val host = FaithlLevel.setting.getHost("Options.Database")
    val name = "faithllevel"

    val tableLevel = Table("${name}_data", host) {
        add { id() }
        add("player") {
            type(ColumnTypeSQL.VARCHAR, 36)
        }
        add("tag") {
            type(ColumnTypeSQL.VARCHAR, 128)
        }
        add("level") {
            type(ColumnTypeSQL.INT)
        }
        add("exp") {
            type(ColumnTypeSQL.INT)
        }
        add("booster") {
            type(ColumnTypeSQL.DOUBLE)
        }
    }

    val dataSource = host.createDataSource()

    init {
        tableLevel.createTable(dataSource)
    }

    override fun getLevel(player: Player, level: Level): Int {
        return tableLevel.select(dataSource) {
            rows("level")
            where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
        }.firstOrNull {
            getInt("level")
        } ?: 0
    }

    override fun getExp(player: Player, level: Level): Int {
        return tableLevel.select(dataSource) {
            where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
            rows("exp")
        }.firstOrNull {
            getInt("exp")
        } ?: 0
    }

    override fun getBooster(player: Player, level: Level): Double {
        return tableLevel.select(dataSource) {
            where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
            rows("booster")
        }.firstOrNull {
            getDouble("booster")
        } ?: 0.0
    }

    override fun setLevel(player: Player, level: Level, value: Int) {
        if (tableLevel.find(dataSource) { where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!)) }) {
            tableLevel.update(dataSource) {
                where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
                set("level", value)
            }
        } else {
            tableLevel.insert(dataSource, "player", "tag","level") {
                value(player.uniqueId.toString(), level.key!!, value)
            }
        }
    }

    override fun setExp(player: Player, level: Level, value: Int) {
        if (tableLevel.find(dataSource) { where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!)) }) {
            tableLevel.update(dataSource) {
                where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
                set("exp", value)
            }
        } else {
            tableLevel.insert(dataSource, "player", "tag","level") {
                value(player.uniqueId.toString(), level.key!!, value)
            }
        }
    }

    override fun setBooster(player: Player, level: Level, value: Double) {
        if (tableLevel.find(dataSource) { where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!)) }) {
            tableLevel.update(dataSource) {
                where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
                set("booster", value)
            }
        } else {
            tableLevel.insert(dataSource, "player", "tag","level") {
                value(player.uniqueId.toString(), level.key!!, value)
            }
        }
    }
}