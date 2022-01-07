package com.faithl.faithllevel.api.event

import com.faithl.faithllevel.internal.core.impl.BasicLevel
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

/**
 * @author Leosouthey
 * @since 2021/12/18-19:07
 **/
data class ExpUpdateEvent(
    val basicLevel: BasicLevel,
    val player: Player,
    val oldLevel: Int,
    val newLevel: Int,
    val oldExp: Int,
    val newExp: Int,
) : BukkitProxyEvent() {

    val type: ChangeType
        get() {
            return if (newLevel - oldLevel > 0) {
                ChangeType.ADD
            } else {
                ChangeType.TAKE
            }
        }

}