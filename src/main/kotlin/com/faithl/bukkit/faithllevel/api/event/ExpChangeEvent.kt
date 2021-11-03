package com.faithl.bukkit.faithllevel.api.event

import com.faithl.bukkit.faithllevel.internal.level.Level
import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent


class ExpChangeEvent(val player: Player, val expDataManager:ExpDataManager, val level: Level, val amount:Int, val type:ChangeType): BukkitProxyEvent() {
    override val allowCancelled: Boolean
        get() = false
}