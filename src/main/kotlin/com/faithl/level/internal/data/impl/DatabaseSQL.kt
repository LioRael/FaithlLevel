package com.faithl.level.internal.data.impl

import com.faithl.level.FaithlLevel
import com.faithl.level.internal.data.Database
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Leosouthey
 * @since 2022/1/14-18:34
 **/
class DatabaseSQL : Database() {

    val host = FaithlLevel.setting.getHost("storage.source.mysql")
    val dataSource = host.createDataSource()

    val tableTarget = Table("${name}_target", host) {
        add { id() }
        add("target") {
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.UNIQUE_KEY)
            }
        }
        add("time") {
            type(ColumnTypeSQL.DATE)
        }
    }

    val tableLevel = Table("${name}_level", host) {
        add { id() }
        add("target") {
            type(ColumnTypeSQL.INT, 16) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("level") {
            type(ColumnTypeSQL.VARCHAR, 128) {
                options(ColumnOptionSQL.KEY)
            }
        }
        add("data_level") {
            type(ColumnTypeSQL.INT)
        }
        add("data_exp") {
            type(ColumnTypeSQL.INT)
        }
    }

    init {
        tableTarget.createTable(dataSource)
        tableLevel.createTable(dataSource)
    }

    fun getTargetId(target: String): Long {
        if (cacheUserId.containsKey(target)) {
            return cacheUserId[target]!!
        }
        val targetId = tableTarget.select(dataSource) {
            where("target" eq target)
            limit(1)
            rows("id")
        }.firstOrNull { getLong("id") } ?: return -1L
        cacheUserId[target] = targetId
        return targetId
    }

    fun updateTargetTime(targetId: Long) {
        tableTarget.update(dataSource) {
            where("id" eq targetId)
            set("time", Date())
        }
    }

    fun createTarget(target: String): CompletableFuture<Long> {
        val targetId = CompletableFuture<Long>()
        CompletableFuture<Void>().also { future ->
            tableTarget.insert(dataSource, "target", "time") {
                value(target, Date())
                onFinally {
                    future.thenApply {
                        targetId.complete(generatedKeys.getLong("id"))
                    }
                }
            }
        }.complete(null)
        return targetId
    }

    override fun getLevel(target: String, level: String): Int {
        val targetId = getTargetId(target)
        if (targetId == -1L) {
            return 0
        }
        submit(async = true) { updateTargetTime(targetId) }
        return getPlayerLevel(targetId, level)
    }

    override fun getExp(target: String, level: String): Int {
        val targetId = getTargetId(target)
        if (targetId == -1L) {
            return 0
        }
        submit(async = true) { updateTargetTime(targetId) }
        return getPlayerExp(targetId, level)
    }

    override fun setLevel(target: String, level: String, value: Int) {
        val targetId = getTargetId(target)
        if (targetId == -1L) {
            createTarget(target).thenApply {
                tableLevel.insert(dataSource, "target", "level", "data_level", "data_exp") {
                    value(it, level, value, 0)
                }
            }
        } else {
            if (tableLevel.find(dataSource) { where("target" eq targetId and ("level" eq level)) }) {
                tableLevel.update(dataSource) {
                    where("target" eq targetId and ("level" eq level))
                    set("data_level", value)
                }
            } else {
                tableLevel.insert(dataSource, "target", "level", "data_level", "data_exp") {
                    value(targetId, level, value, 0)
                }
            }
        }
    }

    override fun setExp(target: String, level: String, value: Int) {
        val targetId = getTargetId(target)
        if (targetId == -1L) {
            createTarget(target).thenApply {
                tableLevel.insert(dataSource, "target", "level", "data_level", "data_exp") {
                    value(it, level, 0, value)
                }
            }
        } else {
            if (tableLevel.find(dataSource) { where("target" eq target and ("level" eq level)) }) {
                tableLevel.update(dataSource) {
                    where("target" eq targetId and ("level" eq level))
                    set("data_exp", value)
                }
            } else {
                tableLevel.insert(dataSource, "target", "level", "data_level", "data_exp") {
                    value(targetId, level, 0, value)
                }
            }
        }
    }

    override fun setObtainExp(target: String, level: String, type: String, obtain: Int) {
        TODO("Not yet implemented")
    }

    override fun setObtainLevel(target: String, level: String, type: String, obtain: Int) {
        TODO("Not yet implemented")
    }

    override fun getObtainExp(target: String, level: String, type: String): Int {
        TODO("Not yet implemented")
    }

    override fun getObtainLevel(target: String, level: String, type: String): Int {
        TODO("Not yet implemented")
    }

    fun getPlayerLevel(targetId: Long, level: String): Int {
        return tableLevel.select(dataSource) {
            where("target" eq targetId and ("level" eq level))
            rows("data_level")
        }.firstOrNull {
            getInt("data_level")
        } ?: 0
    }

    fun getPlayerExp(targetId: Long, level: String): Int {
        return tableLevel.select(dataSource) {
            where("target" eq targetId and ("level" eq level))
            rows("data_exp")
        }.firstOrNull {
            getInt("data_exp")
        } ?: 0
    }

    companion object {

        private val cacheUserId = ConcurrentHashMap<String, Long>()

        @SubscribeEvent
        fun e(e: PlayerQuitEvent) {
            cacheUserId.remove(e.player.name)
        }

    }

}