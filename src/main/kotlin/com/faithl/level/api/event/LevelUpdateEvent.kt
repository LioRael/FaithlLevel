package com.faithl.level.api.event

import com.faithl.level.internal.core.Level
import taboolib.platform.type.BukkitProxyEvent

/**
 * @author Leosouthey
 * @since 2021/12/18-19:02
 **/
data class LevelUpdateEvent(
    val level: Level,
    val target: String,
    var oldLevel: Int,
    var newLevel: Int,
) : BukkitProxyEvent() {

    val type: ChangeType
        get() {
            return if (newLevel - oldLevel > 0) {
                ChangeType.ADD
            } else if (newLevel == oldLevel) {
                ChangeType.NONE
            } else {
                ChangeType.TAKE
            }
        }

}