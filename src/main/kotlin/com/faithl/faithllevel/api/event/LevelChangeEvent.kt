package com.faithl.faithllevel.api.event

import com.faithl.faithllevel.internal.core.Level
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * @author Leosouthey
 * @time 2021/12/18-19:02
 **/
class LevelChangeEvent(
    val level: Level,
    val player: Player,
    val oldLevel: Int,
    val newLevel: Int,
    val type: ChangeType
) : BukkitProxyEvent() {
    enum class ChangeType {
        ADD, TAKE
    }

    override val allowCancelled: Boolean
        get() = true
}