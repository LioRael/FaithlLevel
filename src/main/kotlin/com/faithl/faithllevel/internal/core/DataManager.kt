package com.faithl.faithllevel.internal.core

import com.faithl.faithllevel.api.event.ExpChangeEvent
import com.faithl.faithllevel.api.event.LevelChangeEvent
import com.faithl.faithllevel.internal.util.Count
import com.faithl.faithllevel.internal.util.run
import com.faithl.faithllevel.internal.util.sendMessage
import org.bukkit.entity.Player
import taboolib.common5.Coerce

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
    var playerExp:Int = 0
    var playerLevel:Int = 0
    init {
        Level.playerData[level]!!.add(this)
    }

    fun addExp(exp:Int):Boolean{
        var addExp = exp
        if (playerLevel >= getMaxLevel()) {
            Level.getLevelFunc(level).event?.getConfigurationSection("player_level_max_warning.message")?.sendMessage(player)
            return false
        }
        val event = ExpChangeEvent(this.level,player,addExp,ExpChangeEvent.ChangeType.ADD)
        event.call()
        while (addExp > 0) {
            if (getMaxExp() == 0) {
                return false
            }
            if (event.isCancelled){
                return false
            }
            if (playerExp + addExp >= getMaxExp()) {
                if (addLevel(1)){
                    return false
                }
                addExp = playerExp + addExp - getMaxExp()
                playerExp = 0
            } else {
                playerExp += addExp
                return true
            }
        }
        return true
    }

    fun addLevel(level:Int):Boolean{
        if (playerLevel >= getMaxLevel()) {
            Level.getLevelFunc(this.level).event?.getConfigurationSection("player_level_max_warning.message")?.sendMessage(player)
            return false
        }
        val event = LevelChangeEvent(this.level,player,playerLevel,playerLevel + level,LevelChangeEvent.ChangeType.ADD)
        event.call()
        if (event.isCancelled){
            return false
        }
        playerLevel += level
        if (playerLevel >= getMaxLevel()){
            Level.getLevelFunc(this.level).event?.getConfigurationSection("player_level_max")?.run(player,playerLevel)
        }
        return true
    }

    fun getMaxExp(level: Int = 0):Int{
        if (playerLevel >= getMaxLevel()) {
            return 0
        }
        val data = Level.getLevelFunc(this.level).data?.getConfigurationSection("level") ?: return 0
        val exp = getLevelValue(playerLevel + level,data.getStringList("grow")) ?: return 0
        if (data.getBoolean("formula")) {
            return Coerce.toInteger(Count.count(exp))
        }
        return Coerce.toInteger(exp)
    }

    fun getMaxLevel(): Int {
        val data = Level.getLevelFunc(level).data?.getStringList("level.grow") ?: return 0
        var maxLevel = -1
        for (str in data) {
            if (str.contains(" ") && str.split(" ").size > 1) {
                maxLevel = Coerce.toInteger(str.split(" ")[0].replace("[^0-9]".toRegex(), ""))
            } else {
                maxLevel++
            }
        }
        return maxLevel
    }

    fun getLevelValue(level: Int = playerLevel,list:List<String>): String? {
        var maxLevel = -1
        var value:String? = null
        for (str in list) {
            if (str.contains(" ") && str.split(" ").size > 1) {
                maxLevel = Coerce.toInteger(str.split(" ")[0].replace("[^0-9]".toRegex(), ""))
                value = str.split(" ")[1]
            } else {
                maxLevel++
                value = str
            }
            if (level <= maxLevel) {
                break
            }
        }
        return value
    }

}