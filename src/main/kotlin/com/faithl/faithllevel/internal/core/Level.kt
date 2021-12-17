package com.faithl.faithllevel.internal.core

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import taboolib.common.util.asList
import taboolib.module.configuration.Configuration
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

    /**
     * 玩家数据管理器
     *
     * @property level 等级
     * @property player 玩家
     * @constructor 构建一个玩家数据管理器
     */
    data class DataManager(val level: Level,val player: Player){
        /**
         * @field playerExp 玩家经验
         * @field playerLevel 玩家等级
         */
        var playerExp:Double = 0.0
        var playerLevel:Int = 0
        init {
            playerData[level]!!.add(this)
        }

        fun addExp(exp:Double):Double{
            condition("exp")
            playerExp += exp
            return playerExp
        }

        fun addLevel(level:Int):Int{
            condition("level")
            playerLevel += level
            return playerLevel
        }

        private fun condition(type:String):Double {
            val data = getLevelFunc(level, player).data?.getConfigurationSection(type)?.apply {
                if (getBoolean("formula.enable")){
//                    KetherShell.eval()
                }
            } ?: return 0.0
        }
    }

    class Function(val level: Level){

        val data = level.basis?.getConfigurationSection("data")
        val permission = level.basis?.getConfigurationSection("permission")
        val event = level.basis?.getConfigurationSection("event")
        val display = level.trait?.getConfigurationSection("display")
        val booster = level.trait?.getConfigurationSection("booster")
        val attribute = level.trait?.getConfigurationSection("attribute")
        val item = level.trait?.getConfigurationSection("item")

        init {
            levelFunc.add(this)
        }
    }

    companion object{
        val playerData = mutableMapOf<Level,MutableList<DataManager>>()
        val levelFunc = mutableListOf<Function>()

        fun getPlayerData(level:Level, player: Player):DataManager{
            return playerData[level]?.find {
                it.player == player
            } ?: DataManager(level,player)
        }

        fun getLevelFunc(level:Level, player: Player):Function{
            return levelFunc.find {
                it.level == level
            } ?: Function(level)
        }
    }

}