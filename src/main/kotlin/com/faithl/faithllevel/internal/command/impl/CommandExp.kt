package com.faithl.faithllevel.internal.command.impl

import com.faithl.faithllevel.api.FaithlLevelAPI
import com.faithl.faithllevel.api.event.ChangeType
import org.bukkit.Bukkit
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.common5.Coerce

/**
 * @author Leosouthey
 * @since 2022/1/9-0:29
 **/
object CommandExp {

    val command = subCommand {
        dynamic("exp") {
            suggestion<ProxyCommandSender> { _, _ -> FaithlLevelAPI.registeredLevels.keys.map { it } }
            dynamic("type") {
                suggestion<ProxyCommandSender> { _, _ -> ChangeType.values().map { it.name.lowercase() } }
                dynamic("player") {
                    suggestion<ProxyCommandSender> { _, _ -> onlinePlayers().map { it.name } }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        val level = context.argument(-2)
                        val type = context.argument(-1)
                        val target = Bukkit.getPlayerExact(argument) ?: return@execute
                        val data = FaithlLevelAPI.getLevel(level)
                        when (type){
                            "none" -> {
                                sender.sendMessage(data.getExp(target).toString())
                            }
                        }
                    }
                    dynamic("value") {
                        restrict<ProxyCommandSender> { _, _, argument ->
                            Coerce.asInteger(argument).isPresent
                        }
                        execute<ProxyCommandSender> { sender, context, argument ->
                            val level = context.argument(-3)
                            val type = context.argument(-2)
                            val target = Bukkit.getPlayerExact(context.argument(-1)) ?: return@execute
                            val data = FaithlLevelAPI.getLevel(level)
                            when (type){
                                "add" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.addExp(target, value)
                                    sender.sendMessage(data.getLevel(target).toString())
                                    sender.sendMessage(data.getExp(target).toString())
                                }
                                "take" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.takeExp(target, value)
                                    sender.sendMessage(data.getLevel(target).toString())
                                    sender.sendMessage(data.getExp(target).toString())
                                }
                                "set" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.setExp(target, value)
                                    sender.sendMessage(data.getLevel(target).toString())
                                    sender.sendMessage(data.getExp(target).toString())
                                }
                                "none" -> {
                                    sender.sendMessage(data.getLevel(target).toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}