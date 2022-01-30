package com.faithl.level.internal.util

import com.faithl.level.api.FaithlLevelAPI
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.asList
import taboolib.common5.Coerce
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XSound
import taboolib.module.chat.colored
import taboolib.module.kether.KetherShell
import taboolib.module.lang.sendLang
import taboolib.platform.compat.replacePlaceholder

/**
 * @author Leosouthey
 * @since 2022/1/15 12:02
 **/
class Result(val state: Boolean, val message: Any? = null)

fun ProxyPlayer.condition(map: Map<*, *>): Result {
    map["permission"]?.let { permissions ->
        permissions.asList().forEach { permission ->
            if (!hasPermission(permission)) {
                return Result(false, map["message"])
            }
        }
    }
    map["level"]?.let { levels ->
        (levels as List<*>).forEach { level ->
            val data = FaithlLevelAPI.getLevel((level as Map<*, *>)["key"] as String)
                .getLevel(this.name)
            val mode = level["mode"] as String
            val value = level["value"] as Int
            when (mode) {
                ">" -> {
                    if (data <= value) {
                        return Result(false, map["message"])
                    }
                }
                ">=" -> {
                    if (data < value) {
                        return Result(false, map["message"])
                    }
                }
                "=", "==" -> {
                    if (data != value) {
                        return Result(false, map["message"])
                    }
                }
                "!=" -> {
                    if (data == value) {
                        return Result(false, map["message"])
                    }
                }
                "<" -> {
                    if (data >= value) {
                        return Result(false, map["message"])
                    }
                }
                "<=" -> {
                    if (data > value) {
                        return Result(false, map["message"])
                    }
                }
            }
        }
    }
    map["kether"]?.let { kether ->
        try {
            val result =
                KetherShell.eval(source = kether.asList(), namespace = listOf("faithllevel"), sender = this)
            if (!Coerce.toBoolean(result)) {
                return Result(false, map["message"])
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            return Result(false, map["message"])
        }
    }
    map["placeholder"]?.let { placeholders ->
        (placeholders as List<*>).forEach { placeholder ->
            val data = ((placeholder as Map<*, *>)["key"] as String).replacePlaceholder(this.cast())
            val mode = placeholder["mode"] as String
            val value = placeholder["value"] as String
            when (mode) {
                ">" -> {
                    if (Coerce.toBoolean(data) <= Coerce.toBoolean(value)) {
                        return Result(false, map["message"])
                    }
                }
                ">=" -> {
                    if (data < value) {
                        if (Coerce.toBoolean(data) < Coerce.toBoolean(value)) {
                            return Result(false, map["message"])
                        }
                    }
                }
                "=", "==" -> {
                    if (data != value) {
                        return Result(false, map["message"])
                    }
                }
                "!=" -> {
                    if (data == value) {
                        return Result(false, map["message"])
                    }
                }
                "<" -> {
                    if (Coerce.toBoolean(data) >= Coerce.toBoolean(value)) {
                        return Result(false, map["message"])
                    }
                }
                "<=" -> {
                    if (Coerce.toBoolean(data) > Coerce.toBoolean(value)) {
                        return Result(false, map["message"])
                    }
                }
            }
        }
    }
    return Result(true)
}

fun ProxyPlayer.condition(config: List<Map<*, *>>): Boolean {
    if (config.isEmpty()) {
        return true
    }
    config.forEach {
        val result = condition(it)
        if (result.state) {
            return true
        } else {
            val message = result.message
            if (message is ConfigurationSection) {
                message.sendMessage(this)
                return false
            }
            if (message is org.bukkit.configuration.ConfigurationSection) {
                message.sendMessage(this)
                return false
            }
        }
    }
    return false
}

fun ProxyPlayer.limit(map: Map<*, *>): Boolean {
    if (map["permission"] != null) {

    }
    return false
}

//fun ProxyPlayer.limit(config: List<Map<*, *>>): Boolean {
//    if (config.isEmpty()) {
//        return true
//    }
//    config.forEach {
//        if (condition(it)) {
//            return true
//        }
//    }
//    return false
//}

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
        player.sendLang(it.replacePlaceholder(player.cast()), it.split(" "), list)
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