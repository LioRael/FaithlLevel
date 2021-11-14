package com.faithl.bukkit.faithllevel.internal.data

import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.Player
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.*

class DatabaseSQLite: Database() {
    val host = newFile(getDataFolder(), "data.db").getHost()

    val tableLevel = Table("faithllevel_data", host) {
        add("player") {
            type(ColumnTypeSQLite.TEXT, 36)
        }
        add("tag") {
            type(ColumnTypeSQLite.TEXT, 128)
        }
        add("level") {
            type(ColumnTypeSQLite.INTEGER)
        }
        add("exp") {
            type(ColumnTypeSQLite.INTEGER)
        }
        add("booster") {
            type(ColumnTypeSQLite.NUMERIC)
        }
    }

    val dataSource = host.createDataSource()

    init {
        tableLevel.createTable(dataSource)
    }


    override fun getLevel(player: Player, level: Level): Int {
        return tableLevel.select(dataSource) {
            where("player" eq player.uniqueId.toString() and ("tag" eq level.key!!))
            rows("level")
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
            tableLevel.insert(dataSource, "player", "tag", "level", "exp") {
                value(player.uniqueId.toString(), level.key!!,0, 0)
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
            tableLevel.insert(dataSource, "player", "tag", "level", "exp") {
                value(player.uniqueId.toString(), level.key!!,0, 0)
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
            tableLevel.insert(dataSource, "player", "tag", "level", "exp") {
                value(player.uniqueId.toString(), level.key!!,0, 0)
            }
        }
    }
}