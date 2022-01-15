package com.faithl.faithllevel.internal.util

import com.faithl.faithllevel.api.FaithlLevelAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.asList
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XSound
import taboolib.module.chat.colored
import taboolib.module.kether.KetherShell
import taboolib.platform.compat.replacePlaceholder
import taboolib.platform.util.sendLang


//
//private fun doCommand(player: Player, level: Int, conf: ConfigurationSection?) {
//    val commands = FunctionLoader.getFunc(level, conf, "command")
//    if (commands != null && commands.isNotEmpty()) {
//        for (command in commands) {
//            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replacePlaceholder(player))
//        }
//    }
//}
//
//private fun doScript(player: Player, level: Int, conf: ConfigurationSection?) {
//    val scripts = FunctionLoader.getFunc(level, conf, "script")
//    if (scripts != null && scripts.isNotEmpty()) {
//        for (script in scripts) {
//            KetherShell.eval(FaithlLevelAPI.registeredScript[script]!!, sender = adaptPlayer(player))
//        }
//    }
//}