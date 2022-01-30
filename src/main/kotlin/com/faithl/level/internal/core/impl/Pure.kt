package com.faithl.level.internal.core.impl

import com.faithl.level.api.FaithlLevelAPI
import com.faithl.level.api.event.ExpUpdateEvent
import com.faithl.level.api.event.LevelUpdateEvent
import com.faithl.level.internal.data.Database
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.submit
import taboolib.library.configuration.ConfigurationSection

/**
 * 纯净等级，仅含有等级/经验的增减功能
 * 数据将会保存到数据库中
 *
 * @author Leosouthey
 * @since 2022/1/8-20:42
 **/
open class Pure() : Temp() {

    init {
        config = 100
    }

    constructor(conf: Int) : this() {
        config = conf
    }

    constructor(conf: ConfigurationSection) : this() {
        config = conf
    }

    constructor(conf: org.bukkit.configuration.ConfigurationSection) : this() {
        config = conf
    }

    override fun getLevel(target: String): Int {
        return levelData.getOrPut(target) { Database.INSTANCE.getLevel(target, FaithlLevelAPI.getName(this)) }
    }

    override fun getExp(target: String): Int {
        return expData.getOrPut(target) { Database.INSTANCE.getExp(target, FaithlLevelAPI.getName(this)) }
    }

    companion object {

        /**
         * 保存数据
         *
         * @param target 目标
         * @param level 纯净等级
         */
        fun save(target: String, level: Pure) {
            submit(async = true) {
                val name = FaithlLevelAPI.getName(level)
                level.levelData[target]?.let { Database.INSTANCE.setLevel(target, name, it) }
                level.expData[target]?.let { Database.INSTANCE.setExp(target, name, it) }
            }
        }

        @SubscribeEvent
        fun e(e: ExpUpdateEvent.Before) {
            if (e.level is Pure) {
                save(e.target, e.level)
            }
        }

        @SubscribeEvent
        fun e(e: LevelUpdateEvent.Before) {
            if (e.level is Pure) {
                save(e.target, e.level)
            }
        }

        @SubscribeEvent
        fun e(e: PlayerQuitEvent) {
            FaithlLevelAPI.registeredLevels.values.forEach {
                if (it is Pure) {
                    save(e.player.name, it)
                    it.levelData.remove(e.player.name)
                }
            }
        }

    }

}