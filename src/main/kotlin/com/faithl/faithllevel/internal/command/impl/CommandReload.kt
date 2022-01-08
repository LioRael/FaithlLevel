package com.faithl.faithllevel.internal.command.impl

import com.faithl.faithllevel.api.FaithlLevelAPI
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.sendLang

object CommandReload {

    val command = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            FaithlLevelAPI.reloadLevel()
            FaithlLevelAPI.reloadScript()
            sender.sendLang("command-reload")
        }
    }

}