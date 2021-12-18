package com.faithl.faithllevel.internal.core

import com.faithl.faithllevel.api.FaithlLevelAPI
import com.faithl.faithllevel.internal.util.Count
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.asList
import taboolib.common5.Coerce
import taboolib.module.configuration.Configuration
import taboolib.module.kether.KetherLoader
import taboolib.module.kether.KetherScriptLoader
import taboolib.module.kether.KetherShell

/**
 * @author Leosouthey
 * @time 2021/12/12-0:58
 *
 * 等级
 *
 * @property conf 等级配置
 * @constructor 构建一个等级
 **/
data class Level(val conf: Configuration) {

    val name = conf.getString("Name")!!
    val basis = conf.getConfigurationSection("Basis")
    val trait = conf.getConfigurationSection("Trait")

    companion object{
        val playerData = mutableMapOf<Level,MutableList<DataManager>>()
        val levelFunc = mutableListOf<Function>()

        fun getPlayerData(level:Level, player: Player):DataManager{
            return playerData[level]?.find {
                it.player == player
            } ?: DataManager(level,player)
        }

        fun getLevelFunc(level:Level):Function{
            return levelFunc.find {
                it.level == level
            } ?: Function(level)
        }
    }

}