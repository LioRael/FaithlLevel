package com.faithl.level.internal.command

import com.faithl.level.FaithlLevel
import com.faithl.level.internal.command.impl.CommandExp
import com.faithl.level.internal.command.impl.CommandLevel
import com.faithl.level.internal.command.impl.CommandReload
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.module.chat.TellrawJson
import taboolib.module.lang.asLangText

@CommandHeader(name = "faithllevel", aliases = ["flevel", "fl"])
object CommandHandler {

    @CommandBody(permission = "faithllevel.level")
    val level = CommandLevel.command

    @CommandBody(permission = "faithllevel.exp")
    val exp = CommandExp.command

    @CommandBody(permission = "faithllevel.reload")
    val reload = CommandReload.command

    @CommandBody
    val main = mainCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    @CommandBody
    val help = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    private fun generateMainHelper(sender: ProxyCommandSender) {
        sender.sendMessage("")
        TellrawJson()
            .append("  ").append("§bFaithlLevel")
            .hoverText("§7FaithlLevel is modern and advanced Minecraft level-plugin")
            .append(" ").append("§f${FaithlLevel.plugin.description.version}")
            .hoverText(
                """
                §7Plugin version: §b${FaithlLevel.plugin.description.version}
            """.trimIndent()
            ).sendTo(sender)
        sender.sendMessage("")
        TellrawJson()
            .append("  §7${sender.asLangText("command-help-type")}: ").append("§f/FaithlLevel §8[...]")
            .hoverText("§f/FaithlLevel §8[...]")
            .suggestCommand("/FaithlLevel ")
            .sendTo(sender)
        sender.sendMessage("  §7${sender.asLangText("command-help-args")}:")
        fun displayArg(name: String, desc: String) {
            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/FaithlLevel $name §8- §7$desc")
                .suggestCommand("/FaithlLevel $name ")
                .sendTo(sender)
            sender.sendMessage("      §7$desc")
        }
        displayArg("level", sender.asLangText("command-level-description"))
        displayArg("exp", sender.asLangText("command-exp-description"))
        displayArg("reload", sender.asLangText("command-reload-description"))
        sender.sendMessage("")
    }

}