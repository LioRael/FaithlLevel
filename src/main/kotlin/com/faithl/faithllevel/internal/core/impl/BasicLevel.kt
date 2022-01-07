package com.faithl.faithllevel.internal.core.impl

import com.faithl.faithllevel.internal.core.Function
import com.faithl.faithllevel.internal.core.Level
import com.faithl.faithllevel.internal.core.impl.data.BasicDataManager
import org.bukkit.entity.Player
import taboolib.module.configuration.Configuration

/**
 * @author Leosouthey
 * @time 2021/12/12-0:58
 *
 * 等级
 *
 * @property conf 等级配置
 * @constructor 构建一个等级
 **/
data class BasicLevel(val conf: Configuration) : Level() {

    val name = conf.getString("Name")!!
    val basis = conf.getConfigurationSection("Basis")
    val trait = conf.getConfigurationSection("Trait")

    companion object {

        val playerData = mutableMapOf<BasicLevel, MutableList<BasicDataManager>>()
        val levelFunc = mutableListOf<Function>()

        fun getPlayerData(basicLevel: BasicLevel, player: Player): BasicDataManager {
            return playerData[basicLevel]?.find {
                it.player == player
            } ?: BasicDataManager(basicLevel, player)
        }

        fun getLevelFunc(basicLevel: BasicLevel): Function {
            return levelFunc.find {
                it.basicLevel == basicLevel
            } ?: Function(basicLevel)
        }

    }

}