package com.faithl.level.internal.command.impl

import com.faithl.level.FaithlLevel
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.Language
import taboolib.module.lang.sendLang

object CommandReload {

    /**
     * 重载指令
     *
     * Usage: /faithllevel reload
     */
    val command = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            Language.reload()
            FaithlLevel.reload()
            sender.sendLang("command-reload")
        }
    }

}