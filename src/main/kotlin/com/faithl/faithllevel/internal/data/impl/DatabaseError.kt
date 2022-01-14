package com.faithl.faithllevel.internal.data.impl

import com.faithl.faithllevel.internal.data.Database
import org.bukkit.entity.Player

/**
 * @author Leosouthey
 * @since 2022/1/14-20:40
 **/
class DatabaseError(private val cause: Throwable) : Database() {

    init {
        cause.printStackTrace()
    }

    override fun getLevel(target: String, level: String): Int {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun getExp(target: String, level: String): Int {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun setLevel(target: String, level: String, value: Int) {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

    override fun setExp(target: String, level: String, value: Int) {
        throw IllegalAccessError("Database initialization failed: ${cause.localizedMessage}")
    }

}