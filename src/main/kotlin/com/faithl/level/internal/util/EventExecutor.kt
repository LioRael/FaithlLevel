package com.faithl.level.internal.util


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