package com.faithl.faithllevel.internal.command.impl

import com.faithl.faithllevel.api.FaithlLevelAPI
import com.faithl.faithllevel.api.event.ChangeType
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.common5.Coerce

object CommandLevel {

    /**
     * 玩家等级处理指令
     *
     * Usage: /faithllevel level {level} {ADD/TAKE/SET/NONE} {player} {value}
     */
    val command = subCommand {
        dynamic("level") {
            suggestion<ProxyCommandSender> { _, _ -> FaithlLevelAPI.registeredLevels.keys.map { it } }
            dynamic("type") {
                suggestion<ProxyCommandSender> { _, _ -> ChangeType.values().map { it.name.lowercase() } }
                dynamic("player") {
                    suggestion<ProxyCommandSender> { _, _ -> onlinePlayers().map { it.name } }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        val level = context.argument(-2)
                        val type = context.argument(-1)
                        val target = getProxyPlayer(argument) ?: return@execute
                        val data = FaithlLevelAPI.getLevel(level)
                        when (type){
                            "none" -> {
                                sender.sendMessage(data.getLevel(target.uniqueId.toString()).toString())
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
                            val target = getProxyPlayer(context.argument(-1)) ?: return@execute
                            val data = FaithlLevelAPI.getLevel(level)
                            when (type){
                                "add" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.addLevel(target.uniqueId.toString(), value)
                                    sender.sendMessage(data.getLevel(target.uniqueId.toString()).toString())
                                    sender.sendMessage(data.getExp(target.uniqueId.toString()).toString())
                                }
                                "take" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.takeLevel(target.uniqueId.toString(), value)
                                    sender.sendMessage(data.getLevel(target.uniqueId.toString()).toString())
                                    sender.sendMessage(data.getExp(target.uniqueId.toString()).toString())
                                }
                                "set" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.setLevel(target.uniqueId.toString(), value)
                                    sender.sendMessage(data.getLevel(target.uniqueId.toString()).toString())
                                    sender.sendMessage(data.getExp(target.uniqueId.toString()).toString())
                                }
                                "none" -> {
                                    sender.sendMessage(data.getLevel(target.uniqueId.toString()).toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}