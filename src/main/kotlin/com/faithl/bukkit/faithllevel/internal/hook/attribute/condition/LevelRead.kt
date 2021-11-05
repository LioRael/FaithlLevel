package com.faithl.bukkit.faithllevel.internal.hook.attribute.condition

import com.faithl.bukkit.faithllevel.api.FaithlLevelAPI
import com.faithl.bukkit.faithllevel.internal.level.Level
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.serverct.ersha.api.annotations.AutoRegister
import org.serverct.ersha.api.component.DescriptionRead
import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored

@AutoRegister
class LevelRead: DescriptionRead(ReadPriority.BEFORE, AttributeReadType.BOOLEAN, true){

    override fun condition(entity: LivingEntity?, lore: String): Boolean {
        for (level in Level.levels){
            if (level.ap==null)
                continue
            if (lore.contains(level.ap.getString("Lore")) && entity is Player){
                val value = lore.split(FaithlLevelAPI.getLevelDataByApLore(level.ap.getString("Lore"))?.ap!!.getString("Lore") ?: return true)[1].replace("[^0-9]".toRegex(),"").toIntOrNull() ?: 0
                val playerLevel = FaithlLevelAPI.getPlayerData(entity, level.ap.getString("Lore")).level
                return if (playerLevel >= value)
                    true
                else{
                    entity.sendMessage(FaithlLevelAPI.getLevelDataByApLore(level.ap.getString("Lore"))?.ap?.getString("Not-Enough")?.replaceWithOrder(level.ap.getString("Lore"))
                        ?.colored() ?: "")
                    false
                }
            }
        }
        return true
    }

}