package com.faithl.bukkit.faithllevel.internal.command.impl

import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.internal.level.Level
import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import com.faithl.bukkit.faithllevel.util.getBoot
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.lang.sendLang

object CommandStatus {
    val command = subCommand {
        execute<ProxyPlayer>{ sender, _, _ ->
            for (levelSystem in Level.levels){
                val playerData: ExpDataManager = FaithlLevelAPI.getPlayerData(sender.cast(),levelSystem)
                sender.sendLang("Command-Status-Info",
                    levelSystem.name!!,sender.name,playerData.getDisplay(),playerData.exp,playerData.getMaxExp(),playerData.getTotalBoot())
            }
        }
        dynamic {
            suggestion<ProxyCommandSender> { _, _ ->
                onlinePlayers().map { it.name}
            }
            execute<ProxyCommandSender>{ sender, _, argument ->
                val target = Bukkit.getPlayerExact(argument) ?: return@execute
                for (levelSystem in Level.levels){
                    val playerData: ExpDataManager = FaithlLevelAPI.getPlayerData(target,levelSystem)
                    sender.sendLang("Command-Status-Info",
                        levelSystem.name!!,target.name,playerData.getDisplay(),playerData.exp,playerData.getMaxExp(),playerData.getTotalBoot())
                }
            }
        }
    }
}