package com.faithl.bukkit.faithllevel.internal.listener

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI.getPlayerData
import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import com.faithl.bukkit.faithllevel.api.event.ChangeType
import com.faithl.bukkit.faithllevel.api.event.ExpChangeEvent
import com.faithl.bukkit.faithllevel.api.event.LevelUpEvent
import com.faithl.bukkit.faithllevel.internal.conf.CommandLoader.getCommand
import com.faithl.bukkit.faithllevel.internal.level.LevelData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerExpChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.replaceWithOrder

object EventListener {
    @SubscribeEvent
    fun e(e: PlayerJoinEvent){
        if (FaithlLevel.isOutDate && e.player.isOp)
            FaithlLevel.checkUpdate(e.player)
    }

    @SubscribeEvent
    fun e(e: PlayerExpChangeEvent) {
        for (levelData in FaithlLevelAPI.getLevelDataMap()){
            if (levelData.value.expBottle.getBoolean("Enable")){
                val addExp = levelData.value.expBottle.getInt("Time")
                val playerData: ExpDataManager = getPlayerData(e.player,levelData.value)
                val event = ExpChangeEvent(e.player,playerData,levelData.value,addExp, ChangeType.ADD)
                event.call()
                if (!event.isCancelled) {
                    playerData.addExp(event.amount);
                }
            }
        }
        if(FaithlLevel.setting.getBoolean("Options.Disable-Origin-ExpChange"))
            e.amount = 0
    }

    @SubscribeEvent
    fun e(e:LevelUpEvent){
        doCommand("Level-Up-Event",e.player,e.levelData)
    }

    @SubscribeEvent
    fun e(e:PlayerDeathEvent){
        doAllCommand("Death-Event",e.entity)
    }

    @SubscribeEvent
    fun e(e:ExpChangeEvent){
        doCommand("Exp-Change-Event",e.player,e.levelData)
    }

    private fun doCommand(event:String,player:Player,levelData:LevelData){
        val playerData: ExpDataManager = getPlayerData(player,levelData)
        for (command in getCommand(playerData.level, event,levelData)){
            if (command != "null")
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceWithOrder(player.name,playerData.level,player.world.name))
        }
    }

    private fun doAllCommand(event:String,player:Player){
        FaithlLevelAPI.getLevelDataMap().forEach{
            val playerData: ExpDataManager = getPlayerData(player,it.value)
            for (command in getCommand(playerData.level, event,it.value)){
                if (command != "null")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceWithOrder(player.name,playerData.level,player.world.name))
            }
        }
    }
}