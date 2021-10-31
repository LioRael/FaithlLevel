package com.faithl.bukkit.faithllevel.internal.level.data

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.api.event.LevelUpEvent
import com.faithl.bukkit.faithllevel.internal.level.LevelData
import com.faithl.bukkit.faithllevel.util.getFExp
import com.faithl.bukkit.faithllevel.util.getFLevel
import com.faithl.bukkit.faithllevel.util.setFExp
import com.faithl.bukkit.faithllevel.util.setFLevel
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.replaceWithOrder
import taboolib.common5.Coerce
import taboolib.module.chat.colored
import taboolib.module.lang.TypeActionBar
import taboolib.platform.util.asLangText
import taboolib.platform.util.asLangTextList
import taboolib.platform.util.sendLang

class ExpDataManager(private val player: Player, val levelData: LevelData) {
    var level = 0
    var exp = 0
    init {
        level = player.getFLevel(levelData).toInt()
        exp = player.getFExp(levelData).toInt()
    }

    fun updateOriginExp() {
        val mainLevel = FaithlLevel.setting.getString("Options.Main-Level")
        if (FaithlLevel.setting.getBoolean("Options.Main-Level-To-Origin") && levelData.name == mainLevel) {
            player.level = level
            if (this.getMaxExp() != 0) {
                player.exp = exp / getMaxExp().toFloat()
            } else {
                player.exp = 0F
            }
        }
        save()
    }

    fun addExp(addExpAmount: Int) {
        var addExp = addExpAmount
        if (getMaxLevel() <= level) {
            player.sendLang("Player-Level-Max",levelData.name)
            return
        }
        val change = addExp
        var levelUp = false
        while (addExp > 0) {
            if (getMaxExp() == 0) {
                break
            }
            // 升级
            if (exp + addExp >= getMaxExp()) {
                addExp = exp + addExp - getMaxExp()
                exp = 0
                level += 1
                levelUp = true
            } else {
                exp += addExp
                break
            }
        }
        updateOriginExp()
        sendActionBar(change)
        if (!levelUp) {
            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, change / 50f, change / 20f)
        } else {
            LevelUpEvent(player, this,levelData).call()
            val title = player.asLangTextList("Player-Level-Up",levelData.name,getDisplay(), getMaxExp()).colored()
            player.sendTitle(title[0], title[1], 5, 20, 5)
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, change / 20f, change / 20f)
        }
    }

    private fun sendActionBar(change:Int){
        val message = TypeActionBar()
        message.text = player.asLangText("Player-Exp-Change",levelData.name,getDisplay(), exp, getMaxExp(), "&b$change")
        message.send(adaptPlayer(player))
    }

    fun hasExp(hasExpAmount: Int): Boolean{
        var hasExp = hasExpAmount
        var level = level
        var exp = exp
        while (hasExp > 0) {
            if (level <= 0 && hasExp > exp) {
                return false
            }
            if (hasExp > exp) {
                hasExp -= exp
                level--
                exp = getMaxExp()
            } else {
                return true
            }
        }
        return true
    }

    fun takeExp(takeExpAmount: Int) {
        var takeExp = takeExpAmount
        val change = takeExp
        while (takeExp > 0) {
            if (level <= 0 && takeExp > exp) {
                exp = 0
                break
            }
            if (takeExp > exp) {
                takeExp -= exp
                level--
                exp = getMaxExp()
            } else {
                exp -= takeExp
                break
            }
        }
        sendActionBar(change)
        updateOriginExp()
    }

    fun getMaxExp(): Int {
        val expList: List<String> = levelData.expGrow
        if (this.getMaxLevel() <= level) {
            return 0
        }
        var maxExp = 0
        var level = -1
        for (str in expList) {
            if (str.contains(":") && str.split(":").toTypedArray().size > 1) {
                level = Coerce.toInteger(str.split(":").toTypedArray()[0].replace("[^0-9]".toRegex(), ""))
                maxExp = Coerce.toInteger(str.split(":").toTypedArray()[1].replace("[^0-9]".toRegex(), ""))
            } else {
                level++
                maxExp = Coerce.toInteger(str.replace("[^0-9]".toRegex(), ""))
            }
            if (str.contains(" ") && str.split(" ").toTypedArray().size > 1) {
                if (!player.hasPermission(str.split(" ").toTypedArray()[1])) {
                    break
                }
            }
            if (this.level <= level) {
                break
            }
        }
        return maxExp
    }

    private fun getMaxLevel(): Int {
        val expList: List<String> = levelData.expGrow
        var maxLevel = -1
        for (str in expList) {
            if (str.contains(":") && str.split(":").toTypedArray().size > 1) {
                maxLevel = Coerce.toInteger(str.split(":").toTypedArray()[0].replace("[^0-9]".toRegex(), ""))
            } else {
                maxLevel++
            }
            if (str.contains(" ") && str.split(" ").toTypedArray().size > 1) {
                if (!player.hasPermission(str.split(" ").toTypedArray()[1])) {
                    break
                }
            }
        }
        return maxLevel
    }

    fun getDisplay(): String {
        val displayList = levelData.levelDisplay
        if (displayList.contains("null"))
            return this.level.toString()
        if (this.getMaxLevel() <= level) {
            return "null"
        }
        var level = -1
        var display = ""
        for(str in displayList){
            if (str.contains(":")&& str.split(":").toTypedArray().size > 1){
                level = Coerce.toInteger(str.split(":").toTypedArray()[0].replace("[^0-9]".toRegex(), ""))
                display = str.split(":").toTypedArray()[1]
            }
            if (this.level <= level) {
                break
            }
        }
        return display.replaceWithOrder(this.level).colored()
    }

    /**
     * 保存数据
     *
     * @return playerData 返回自己
     */
    fun save(): ExpDataManager{
        player.setFExp(levelData,exp)
        player.setFLevel(levelData,level)
        return this
    }
}