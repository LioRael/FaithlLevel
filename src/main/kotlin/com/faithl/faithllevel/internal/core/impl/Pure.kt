package com.faithl.faithllevel.internal.core.impl

import com.faithl.faithllevel.api.FaithlLevelAPI
import com.faithl.faithllevel.api.event.ExpUpdateEvent
import com.faithl.faithllevel.api.event.LevelUpdateEvent
import com.faithl.faithllevel.internal.data.Database
import com.faithl.faithllevel.internal.data.PlayerIndex
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.info
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
        expIncrease = 100
    }

    constructor(conf: ConfigurationSection) : this() {
        expIncrease = conf
    }

    constructor(conf: org.bukkit.configuration.ConfigurationSection) : this() {
        expIncrease = conf
    }

    constructor(value: Int) : this() {
        expIncrease = value
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
            info(1)
            submit(async = true) {
                val name = FaithlLevelAPI.getName(level)
                level.levelData[target]?.let { Database.INSTANCE.setLevel(target, name, it) }
                level.expData[target]?.let { Database.INSTANCE.setExp(target, name, it) }
            }
        }

        @SubscribeEvent
        fun e(e: ExpUpdateEvent) {
            if (e.level is Pure) {
                save(e.target, e.level)
            }
        }

        @SubscribeEvent
        fun e(e: LevelUpdateEvent) {
            if (e.level is Pure) {
                save(e.target, e.level)
            }
        }

        @SubscribeEvent
        fun e(e: PlayerQuitEvent) {
            FaithlLevelAPI.registeredLevels.values.forEach {
                if (it is Pure) {
                    it.levelData.remove(PlayerIndex.getTargetInformation(adaptPlayer(e.player)))
                }
            }
        }

    }

}