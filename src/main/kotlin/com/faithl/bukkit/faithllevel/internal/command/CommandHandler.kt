package com.faithl.bukkit.faithllevel.internal.command

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.internal.command.impl.*
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.chat.TellrawJson
import taboolib.platform.util.asLangText


@CommandHeader(name = "faithllevel", aliases = ["fl","flevel","level"], permission = "FaithlLevel.access")
object CommandHandler {
    @CommandBody
    val booster = CommandBooster.command

    @CommandBody
    val status = CommandStatus.command

    @CommandBody
    val take = CommandTake.command

    @CommandBody
    val set = CommandSet.command

    @CommandBody
    val add = CommandAdd.command

    @CommandBody
    val reload = CommandReload.command

    @CommandBody
    val main = mainCommand{
        execute<CommandSender> { sender, _, argument ->
            if (argument.isEmpty()) {
                generateMainHelper(sender)
                return@execute
            }
            incorrectCommand { _, _, _, _ ->
                sender.sendMessage("§8[§3FaithlLevel§8] §8[§cERROR§8] Args §6$argument §3not found.")
                TellrawJson()
                    .append("§8[§3FaithlLevel§8] §8[§bINFO§8] Type ").append("§f/FaithlLevel help")
                    .hoverText("§f/FaithlLevel help §8- §7more help...")
                    .suggestCommand("/FaithlLevel help")
                    .append("§3 for help.")
                    .sendTo(adaptCommandSender(sender))
            }
        }
    }

    @CommandBody
    val help = subCommand{
        execute<CommandSender> { sender, _, _ ->
            generateMainHelper(sender)
        }
    }

    private fun generateMainHelper(sender: CommandSender){
        val proxySender = adaptCommandSender(sender)
        proxySender.sendMessage("")
        TellrawJson()
            .append("  ").append("§3FaithlLevel")
            .hoverText("§7FaithlLevel is modern and advanced Minecraft level-plugin")
            .append(" ").append("§f${FaithlLevel.plugin.description.version}")
            .hoverText("""
                §7Plugin version: §2${FaithlLevel.plugin.description.version}
            """.trimIndent()).sendTo(proxySender)
        proxySender.sendMessage("")
        TellrawJson()
            .append("  §7${sender.asLangText("Command-Help-Type")}: ").append("§f/FaithlLevel §8[...]")
            .hoverText("§f/FaithlLevel §8[...]")
            .suggestCommand("/FaithlLevel ")
            .sendTo(proxySender)
        proxySender.sendMessage("  §7${sender.asLangText("Command-Help-Args")}:")
        fun displayArg(name: String, desc: String) {
            TellrawJson()
                .append("    §8- ").append("§f$name")
                .hoverText("§f/FaithlLevel $name §8- §7$desc")
                .suggestCommand("/FaithlLevel $name ")
                .sendTo(proxySender)
            proxySender.sendMessage("      §7$desc")
        }
        displayArg("add", sender.asLangText("Command-Add-Description"))
        displayArg("set", sender.asLangText("Command-Set-Description"))
        displayArg("take", sender.asLangText("Command-Take-Description"))
        if (FaithlLevel.ap)
            displayArg("booster", sender.asLangText("Command-Booster-Description"))
        displayArg("status", sender.asLangText("Command-Status-Description"))
        displayArg("reload", sender.asLangText("Command-Reload-Description"))
        proxySender.sendMessage("")
    }
}