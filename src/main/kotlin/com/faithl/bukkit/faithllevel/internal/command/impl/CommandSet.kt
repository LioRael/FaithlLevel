package com.faithl.bukkit.faithllevel.internal.command.impl

import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.internal.level.Level
import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.common5.Coerce
import taboolib.module.lang.sendLang

object CommandSet {
    val command = subCommand {
        execute<ProxyCommandSender>{ sender, _, _ ->
            sender.sendLang("Command-Set-Error")
        }
        dynamic {
            suggestion<ProxyCommandSender> { _, _ ->
                Level.levels.map { it.key!! }
            }
            dynamic {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ ->
                    onlinePlayers().map { it.name }
                }
                execute<ProxyPlayer>{ sender, context, argument ->
                    val value = Coerce.toInteger(argument)
                    val playerData: ExpDataManager = FaithlLevelAPI.getPlayerData(sender.cast(),
                        FaithlLevelAPI.getLevelData(context.argument(-1))?:return@execute)
                    playerData.level = value
                    playerData.exp = 0
                    playerData.updateOriginExp()
                    sender.sendLang("Command-Set-Info", playerData.levelData.name!!,sender.name,value)
                }
                dynamic {
                    restrict<ProxyCommandSender> { _, _, argument ->
                        Coerce.asInteger(argument).isPresent
                    }
                    execute<ProxyCommandSender>{ sender, context, argument ->
                        if (Coerce.asInteger(argument)==null){
                            sender.sendLang("Command-Set-Error")
                            return@execute
                        }
                        val target = Bukkit.getPlayerExact(context.argument(-1)) ?: return@execute
                        val value = Coerce.toInteger(argument)
                        val playerData: ExpDataManager = FaithlLevelAPI.getPlayerData(target,
                            FaithlLevelAPI.getLevelData(context.argument(-2))?:return@execute)
                        playerData.level = value
                        playerData.exp = 0
                        sender.sendLang("Command-Set-Info", playerData.levelData.name!!,target.name,value)
                        playerData.updateOriginExp()
                    }
                }
            }
        }
    }
}