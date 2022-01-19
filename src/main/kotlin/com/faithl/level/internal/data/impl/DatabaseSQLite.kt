package com.faithl.level.internal.data.impl

import com.faithl.level.internal.data.Database
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost

/**
 * @author Leosouthey
 * @since 2022/1/14-20:41
 **/
class DatabaseSQLite : Database() {

    val host = newFile(getDataFolder(), "storage.db").getHost()
    val dataSource = host.createDataSource()

    val tableLevel = Table("${name}_level", host) {
        add("target") {
            type(ColumnTypeSQLite.TEXT, 36)
        }
        add("level") {
            type(ColumnTypeSQLite.TEXT, 128)
        }
        add("data_level") {
            type(ColumnTypeSQLite.INTEGER)
        }
        add("data_exp") {
            type(ColumnTypeSQLite.INTEGER)
        }
    }

    init {
        tableLevel.createTable(dataSource)
    }

    override fun getLevel(target: String, level: String): Int {
        return getPlayerLevel(target, level)
    }

    override fun getExp(target: String, level: String): Int {
        return getPlayerExp(target, level)
    }

    override fun setLevel(target: String, level: String, value: Int) {
        if (tableLevel.find(dataSource) { where("target" eq target and ("level" eq level)) }) {
            tableLevel.update(dataSource) {
                where("target" eq target and ("level" eq level))
                set("data_level", value)
            }
        } else {
            tableLevel.insert(dataSource, "target", "level", "data_level", "data_exp") {
                value(target, level, value, 0)
            }
        }
    }

    override fun setExp(target: String, level: String, value: Int) {
        if (tableLevel.find(dataSource) { where("target" eq target and ("level" eq level)) }) {
            tableLevel.update(dataSource) {
                where("target" eq target and ("level" eq level))
                set("data_exp", value)
            }
        } else {
            tableLevel.insert(dataSource, "target", "level", "data_level", "data_exp") {
                value(target, level, 0, value)
            }
        }
    }

    fun getPlayerLevel(target: String, level: String): Int {
        return tableLevel.select(dataSource) {
            where("target" eq target and ("level" eq level))
            rows("data_level")
        }.firstOrNull {
            getInt("level")
        } ?: 0
    }

    fun getPlayerExp(target: String, level: String): Int {
        return tableLevel.select(dataSource) {
            where("target" eq target and ("level" eq level))
            rows("data_exp")
        }.firstOrNull {
            getInt("exp")
        } ?: 0
    }

}