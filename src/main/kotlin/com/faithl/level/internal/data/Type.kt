package com.faithl.level.internal.data

import com.faithl.level.FaithlLevel

/**
 * 数据存储方式
 *
 * @author Leosouthey
 * @since 2022/1/14-20:37
 **/
enum class Type {

    SQLITE, MYSQL;

    companion object {

        val INSTANCE: Type by lazy {
            try {
                valueOf(FaithlLevel.setting.getString("storage.type", "")!!.uppercase())
            } catch (ignored: Throwable) {
                SQLITE
            }
        }
    }

}