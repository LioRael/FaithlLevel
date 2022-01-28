package com.faithl.level.internal.command.impl

import com.faithl.level.api.FaithlLevelAPI
import com.faithl.level.api.event.ChangeType
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.common5.Coerce

object CommandLevel {

    /**
     * 目标等级处理指令
     *
     * Usage: /faithllevel level {level} {add/take/set/none} {player/string} {target} {value}
     */
    val command = subCommand {
        dynamic("level") {
            suggestion<ProxyCommandSender> { _, _ -> FaithlLevelAPI.registeredLevels.keys.map { it } }
            dynamic("type") {
                suggestion<ProxyCommandSender> { _, _ -> ChangeType.values().map { it.name.lowercase() } }
                dynamic("target") {
                    suggestion<ProxyCommandSender>(uncheck = true) { _, _ -> onlinePlayers().map { it.name } }
                    execute<ProxyCommandSender> { sender, context, argument ->
                        val level = context.argument(-2)
                        val type = context.argument(-1)
                        val data = FaithlLevelAPI.getLevel(level)
                        when (type) {
                            "none" -> {
                                sender.sendMessage(data.getLevel(argument).toString())
                            }
                        }
                    }
                    dynamic("value", optional = true) {
                        restrict<ProxyCommandSender> { _, _, argument ->
                            Coerce.asInteger(argument).isPresent
                        }
                        execute<ProxyCommandSender> { sender, context, argument ->
                            val level = context.argument(-3)
                            val type = context.argument(-2)
                            val target = context.argument(-1)
                            val data = FaithlLevelAPI.getLevel(level)
                            when (type) {
                                "add" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.addLevel(target, value)
                                    sender.sendMessage(data.getLevel(target).toString())
                                    sender.sendMessage(data.getExp(target).toString())
                                }
                                "take" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.takeLevel(target, value)
                                    sender.sendMessage(data.getLevel(target).toString())
                                    sender.sendMessage(data.getExp(target).toString())
                                }
                                "set" -> {
                                    val value = Coerce.toInteger(argument)
                                    data.setLevel(target, value)
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