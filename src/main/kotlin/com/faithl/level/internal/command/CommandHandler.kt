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

    @AppearHelper
    @CommandBody(permission = "faithllevel.level")
    val level = CommandLevel.command

    @AppearHelper
    @CommandBody(permission = "faithllevel.exp")
    val exp = CommandExp.command

    @AppearHelper
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

    fun generateMainHelper(sender: ProxyCommandSender) {
        sender.sendMessage("")
        TellrawJson()
            .append("  ").append("§bFaithlLevel")
            .hoverText("§7FaithlLevel is modern and advanced Minecraft level-plugin")
            .append(" ").append("§f${FaithlLevel.plugin.description.version}")
            .hoverText("""
                §7Plugin version: §2${FaithlLevel.plugin.description.version}
            """.trimIndent()).sendTo(sender)
        sender.sendMessage("")
        TellrawJson()
            .append("  §7${sender.asLangText("command-help-type")}: ").append("§f/faithllevel §8[...]")
            .hoverText("§f/faithllevel §8[...]")
            .suggestCommand("/faithllevel ")
            .sendTo(sender)
        sender.sendMessage("  §7${sender.asLangText("command-help-args")}:")

        javaClass.declaredFields.forEach {
            if (!it.isAnnotationPresent(AppearHelper::class.java)) return@forEach
            val name = it.name
            val desc = sender.asLangText("command-$name-description")

            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/faithllevel $name §8- §7$desc")
                .suggestCommand("/faithllevel $name ")
                .sendTo(sender)
            sender.sendMessage("      §7$desc")
        }
        sender.sendMessage("")
    }

}