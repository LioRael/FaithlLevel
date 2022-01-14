package com.faithl.faithllevel.internal.data

import com.faithl.faithllevel.FaithlLevel
import taboolib.common.platform.ProxyPlayer

/**
 * @author Leosouthey
 * @since 2022/1/14-18:48
 **/
enum class PlayerIndex {

    NAME, UUID;

    companion object {

        val INSTANCE: PlayerIndex by lazy {
            try {
                valueOf(FaithlLevel.setting.getString("storage.player-index", "")!!.uppercase())
            } catch (ignored: Throwable) {
                UUID
            }
        }

        fun getTargetInformation(player: ProxyPlayer): String {
            return when (PlayerIndex.INSTANCE) {
                NAME -> player.name
                UUID -> player.uniqueId.toString()
            }
        }
    }

}