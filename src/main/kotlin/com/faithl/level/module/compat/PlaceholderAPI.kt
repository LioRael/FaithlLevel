package com.faithl.level.module.compat

import com.faithl.level.api.FaithlLevelAPI
import com.faithl.level.internal.core.LevelHandler
import com.faithl.level.internal.core.impl.Basic
import com.faithl.level.internal.core.impl.Temp
import com.faithl.level.internal.data.PlayerIndex
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.common5.Coerce
import taboolib.platform.compat.PlaceholderExpansion

/**
 * PlaceholderAPI兼容
 */
object PlaceholderAPI : PlaceholderExpansion {
    override val identifier: String
        get() = "flevel"

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        val level = FaithlLevelAPI.getLevel(args.split("_")[0])
        var target: String = if (player != null) {
            PlayerIndex.getTargetInformation(adaptPlayer(player))
        } else {
            args
        }
        when (val type = args.split("_")[1]) {
            "level" -> return Coerce.toString(level.getLevel(target))
            "exp" -> return Coerce.toString(level.getExp(target))
            "limit" -> {}
            "display" -> when (args.split("_")[2]) {
                "level" -> return if (level is Basic) {
                    Coerce.toString(level.getDisplay(target, "level", level.getLevel(target)))
                } else {
                    Coerce.toString(level.getLevel(target))
                }
                "exp" -> return if (level is Basic) {
                    Coerce.toString(level.getDisplay(target, "exp", level.getExp(target)))
                } else {
                    Coerce.toString(level.getExp(target))
                }
            }
            "next" -> when (args.split("_")[2]) {
                "level" -> return Coerce.toString(level.getLevel(target) + 1)
                "exp" -> {
                    val data = (level as Temp).expIncrease
                    return Coerce.toString(
                        LevelHandler.getNextExp(
                            target,
                            level.getLevel(target),
                            data!!,
                            level is Basic
                        )
                    )
                }
                "display" -> when (args.split("_")[3]) {
                    "level" -> return if (level is Basic) {
                        Coerce.toString(
                            level.getDisplay(
                                target,
                                "level",
                                level.getLevel(target) + 1,
                                level.getLevel(target) + 1
                            )
                        )
                    } else {
                        Coerce.toString(level.getLevel(target))
                    }
                    "exp" -> return if (level is Basic) {
                        Coerce.toString(
                            level.getDisplay(
                                target,
                                "exp",
                                level.getExp(target) + 1,
                                level.getExp(target) + 1
                            )
                        )
                    } else {
                        Coerce.toString(level.getExp(target))
                    }
                }
            }
            else -> {
                target = type
                when (args.split("_")[2]) {
                    "level" -> return Coerce.toString(level.getLevel(target))
                    "exp" -> return Coerce.toString(level.getExp(target))
                    "limit" -> {}
                    "display" -> when (args.split("_")[3]) {
                        "level" -> return if (level is Basic) {
                            Coerce.toString(level.getDisplay(target, "level", level.getLevel(target)))
                        } else {
                            Coerce.toString(level.getLevel(target))
                        }
                        "exp" -> return if (level is Basic) {
                            Coerce.toString(level.getDisplay(target, "exp", level.getExp(target)))
                        } else {
                            Coerce.toString(level.getExp(target))
                        }
                    }
                    "next" -> when (args.split("_")[3]) {
                        "level" -> return Coerce.toString(level.getLevel(target) + 1)
                        "exp" -> {
                            val data = (level as Temp).expIncrease
                            return Coerce.toString(
                                LevelHandler.getNextExp(
                                    target,
                                    level.getLevel(target),
                                    data!!,
                                    level is Basic
                                )
                            )
                        }
                        "display" -> when (args.split("_")[4]) {
                            "level" -> return if (level is Basic) {
                                Coerce.toString(
                                    level.getDisplay(
                                        target,
                                        "level",
                                        level.getLevel(target) + 1,
                                        level.getLevel(target) + 1
                                    )
                                )
                            } else {
                                Coerce.toString(level.getLevel(target))
                            }
                            "exp" -> return if (level is Basic) {
                                Coerce.toString(
                                    level.getDisplay(
                                        target,
                                        "exp",
                                        level.getExp(target) + 1,
                                        level.getExp(target) + 1
                                    )
                                )
                            } else {
                                Coerce.toString(level.getExp(target))
                            }
                        }
                    }
                }
            }
        }
        return "未知参数"
    }

}