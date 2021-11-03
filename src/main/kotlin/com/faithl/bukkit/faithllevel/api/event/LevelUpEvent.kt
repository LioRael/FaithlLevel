package com.faithl.bukkit.faithllevel.api.event

import com.faithl.bukkit.faithllevel.internal.level.Level
import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class LevelUpEvent(val player:Player, val expDataManager: ExpDataManager, val level: Level): BukkitProxyEvent() {
    override val allowCancelled: Boolean
        get() = false
}