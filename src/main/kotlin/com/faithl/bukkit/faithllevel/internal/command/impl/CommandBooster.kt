package com.faithl.bukkit.faithllevel.internal.command.impl

import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.internal.data.Database
import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.common5.Coerce
import taboolib.module.lang.sendLang

object CommandBooster {
    val command = subCommand {
        execute<ProxyCommandSender>{ sender, _, _ ->
            sender.sendLang("Command-Boot-Error")
        }
        //level
        dynamic {
            suggestion<ProxyCommandSender> { _, _ ->
                Level.levels.map { it.key!! }
            }
            //player || value
            dynamic {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ ->
                    onlinePlayers().map { it.name }
                }
                execute<ProxyPlayer> { sender, context, argument ->
                    val level = FaithlLevelAPI.getLevelData(context.argument(-1)) ?: return@execute
                    val value = Coerce.toDouble(argument)/100
                    val player = Bukkit.getPlayerExact(sender.name) ?: return@execute
                    Database.INSTANCE.setBooster(player,level,value)
                    sender.sendLang("Command-Boot-Info", level.name!!,sender.name,"${argument}%")
                }
                //value
                dynamic {
                    restrict<ProxyCommandSender> { _, _, argument ->
                        Coerce.asInteger(argument).isPresent
                    }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        if (Coerce.asInteger(argument) == null) {
                            sender.sendLang("Command-Boot-Error")
                            return@execute
                        }
                        val target = Bukkit.getPlayerExact(context.argument(-1)) ?: return@execute
                        val level = FaithlLevelAPI.getLevelData(context.argument(-2)) ?: return@execute
                        val value = Coerce.toDouble(argument)/100
                        Database.INSTANCE.setBooster(target,level,value)
                        sender.sendLang("Command-Boot-Info", level.name!!,target.name,"${argument}%")
                    }
                }
            }
        }
    }
}