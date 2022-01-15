package com.faithl.faithllevel.internal.util

import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.asList
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XSound
import taboolib.module.chat.colored
import taboolib.module.lang.sendLang
import taboolib.platform.compat.replacePlaceholder

/**
 * @author Leosouthey
 * @since 2022/1/15 12:02
 **/

fun Player.hasPermission(config: ConfigurationSection, key: String): Boolean {
    if (config[key] != null) {
        config[key]!!.asList().forEach { it ->
            if (!hasPermission(it)) {
                return false
            }
        }
    }
    return true
}

fun Player.hasPermission(config: org.bukkit.configuration.ConfigurationSection, key: String): Boolean {
    if (config[key] != null) {
        config[key]!!.asList().forEach { it ->
            if (!hasPermission(it)) {
                return false
            }
        }
    }
    return true
}

//fun ConfigurationSection.run(player: ProxyPlayer, level: Int) {
//    val command = getConfigurationSection("command")
//    if (command != null) {
//        doCommand(player, level, command)
//    }
//    val script = getConfigurationSection("script")
//    if (script != null) {
//        doScript(player, level, script)
//    }
//    getConfigurationSection("message")?.sendMessage(player)
//}

fun ConfigurationSection.sendMessage(player: ProxyPlayer, list: List<String> = listOf()) {
    get("actionbar")?.asList()?.colored()?.forEach {
        adaptPlayer(player).sendActionBar(it.replacePlaceholder(player.cast()))
    }
    get("text")?.asList()?.colored()?.forEach {
        player.sendMessage(it.replacePlaceholder(player.cast()))
    }
    get("lang")?.asList()?.colored()?.forEach {
        if (it.contains(" ")) {
            player.sendLang(it, it.split(" "), list)
        }
    }
    get("sound")?.asList()?.colored()?.forEach {
        XSound.parse(it)?.forPlayer(player.cast())?.play()
    }
    get("raw")?.asList()?.colored()?.forEach {
        player.sendRawMessage(it)
    }
}

//fun org.bukkit.configuration.ConfigurationSection.run(player: Player, level: Int) {
//    val command = getConfigurationSection("command")
//    if (command != null) {
//        doCommand(player, level, command)
//    }
//    val script = getConfigurationSection("script")
//    if (script != null) {
//        doScript(player, level, script)
//    }
//    getConfigurationSection("message")?.sendMessage(player)
//}

fun org.bukkit.configuration.ConfigurationSection.sendMessage(player: ProxyPlayer, list: List<String> = listOf()) {
    get("actionbar")?.asList()?.colored()?.forEach {
        adaptPlayer(player).sendActionBar(it.replacePlaceholder(player.cast()))
    }
    get("text")?.asList()?.colored()?.forEach {
        player.sendMessage(it.replacePlaceholder(player.cast()))
    }
    get("lang")?.asList()?.colored()?.forEach {
        if (it.contains(" ")) {
            player.sendLang(it, it.split(" "), list)
        }
    }
    get("sound")?.asList()?.colored()?.forEach {
        XSound.parse(it)?.forPlayer(player.cast())?.play()
    }
    get("raw")?.asList()?.colored()?.forEach {
        player.sendRawMessage(it)
    }
}