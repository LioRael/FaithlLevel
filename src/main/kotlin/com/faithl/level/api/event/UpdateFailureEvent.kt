package com.faithl.level.api.event

import com.faithl.level.internal.core.Level
import taboolib.platform.type.BukkitProxyEvent

/**
 * @author Leosouthey
 * @since 2022/1/30-19:32
 **/
class UpdateFailureEvent(val level: Level, val message: String) : BukkitProxyEvent()