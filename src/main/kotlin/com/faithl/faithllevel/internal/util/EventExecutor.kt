package com.faithl.faithllevel.internal.util

import com.faithl.faithllevel.FaithlLevel
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

fun ConfigurationSection.run(player: Player, level:Int){
    val command = getConfigurationSection("command")
    if (command != null){
        doCommand(player,level,command)
    }
    val script = getConfigurationSection("script")
    if (script != null){
        doScript(player,level,script)
    }
    getConfigurationSection("message")?.sendMessage(player)
}

fun ConfigurationSection.sendMessage(player: Player){
    get("actionbar")?.asList()?.colored()?.forEach {
        var value = it
        if (FaithlLevel.placeHolderApi){
            value = value.replacePlaceholder(player)
        }
        adaptPlayer(player).sendActionBar(value)
    }
    get("text")?.asList()?.colored()?.forEach {
        var value = it
        if (FaithlLevel.placeHolderApi){
            value = value.replacePlaceholder(player)
        }
        player.sendMessage(value)
    }
    get("lang")?.asList()?.colored()?.forEach {
        if (it.contains(" ")){
            player.sendLang(it,it.split(" "))
        }
    }
    get("sound")?.asList()?.colored()?.forEach {
        XSound.parse(it)?.forPlayer(player)?.play()
    }
    get("raw")?.asList()?.colored()?.forEach {
        player.sendRawMessage(it)
    }
}

private fun doCommand(player: Player, level: Int, conf: ConfigurationSection?){
    val commands = FuncLoader.getFunc(level, conf,"command")
    if (commands != null && commands.isNotEmpty()){
        for (command in commands){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replacePlaceholder(player))
        }
    }
}

private fun doScript(player: Player, level: Int, conf: ConfigurationSection?){
    val scripts = FuncLoader.getFunc(level, conf,"script")
    if (scripts != null && scripts.isNotEmpty()){
        for (script in scripts){
            KetherShell.eval(FaithlLevelAPI.registeredScript[script]!!,sender = adaptPlayer(player))
        }
    }
}