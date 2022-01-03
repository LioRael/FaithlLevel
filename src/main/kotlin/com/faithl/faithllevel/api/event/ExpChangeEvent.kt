package com.faithl.faithllevel.api.event

import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * @author Leosouthey
 * @time 2021/12/18-19:07
 **/
class ExpChangeEvent(val level: Level, val player: Player, val amount: Int, val type: ChangeType) : BukkitProxyEvent() {

    enum class ChangeType {
        ADD, TAKE
    }

    override val allowCancelled: Boolean
        get() = true
}