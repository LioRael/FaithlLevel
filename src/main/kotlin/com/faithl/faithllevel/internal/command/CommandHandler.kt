package com.faithl.faithllevel.internal.command

import com.faithl.faithllevel.FaithlLevel
import com.faithl.faithllevel.internal.command.impl.CommandExp
import com.faithl.faithllevel.internal.command.impl.CommandLevel
import com.faithl.faithllevel.internal.command.impl.CommandReload
import org.bukkit.command.CommandSender
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.chat.TellrawJson
import taboolib.platform.util.asLangText

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
        execute<CommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    @CommandBody
    val help = subCommand {
        execute<CommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    private fun generateMainHelper(sender: CommandSender) {
        val proxySender = adaptCommandSender(sender)
        proxySender.sendMessage("")
        TellrawJson()
            .append("  ").append("§bFaithlLevel")
            .hoverText("§7FaithlLevel is modern and advanced Minecraft level-plugin")
            .append(" ").append("§f${FaithlLevel.plugin.description.version}")
            .hoverText(
                """
                §7Plugin version: §b${FaithlLevel.plugin.description.version}
            """.trimIndent()
            ).sendTo(proxySender)
        proxySender.sendMessage("")
        TellrawJson()
            .append("  §7${sender.asLangText("command-help-type")}: ").append("§f/FaithlLevel §8[...]")
            .hoverText("§f/FaithlLevel §8[...]")
            .suggestCommand("/FaithlLevel ")
            .sendTo(proxySender)
        proxySender.sendMessage("  §7${sender.asLangText("command-help-args")}:")
        fun displayArg(name: String, desc: String) {
            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/FaithlLevel $name §8- §7$desc")
                .suggestCommand("/FaithlLevel $name ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7$desc")
        }
        displayArg("level", sender.asLangText("command-level-description"))
        displayArg("exp", sender.asLangText("command-exp-description"))
        displayArg("reload", sender.asLangText("command-reload-description"))
        proxySender.sendMessage("")
    }

}