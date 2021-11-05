package com.faithl.bukkit.faithllevel.internal.command.impl

import com.faithl.bukkit.faithllevel.FaithlLevel
import com.faithl.bukkit.faithllevel.internal.conf.Loader
import com.faithl.bukkit.faithllevel.internal.level.Level
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.pluginVersion
import taboolib.module.lang.Language
import taboolib.module.lang.sendLang

object CommandReload {
    val command = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            Language.reload()
            FaithlLevel.setting.reload()
            Level.levels.clear()
            FaithlLevel.init()
            Loader.loadLevels(sender.cast())
            sender.sendLang("Plugin-Reloaded", pluginVersion,KotlinVersion.CURRENT.toString())
        }
    }
}