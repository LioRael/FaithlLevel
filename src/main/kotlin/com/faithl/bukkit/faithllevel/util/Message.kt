package com.faithl.bukkit.faithllevel.util

import com.faithl.bukkit.faithllevel.internal.level.data.ExpDataManager
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.util.replaceWithOrder
import taboolib.module.chat.colored
import taboolib.module.lang.TypeActionBar
import taboolib.platform.util.asLangTextList
import java.util.regex.Pattern

fun Player.send(msgs:MutableList<String>,expDataManager:ExpDataManager,vararg value:Int){
    for (str in msgs){
        val chain = str.split("]".toRegex(),2).toMutableList()
        val type = chain[0]
        val msg:String = if (value.isEmpty())
            chain[1].replaceWithOrder(expDataManager.levelData.name!!,expDataManager.getDisplay(),expDataManager.exp,expDataManager.getMaxExp()).colored()
        else
            chain[1].replaceWithOrder(expDataManager.levelData.name!!,expDataManager.getDisplay(),expDataManager.exp,expDataManager.getMaxExp(),value[0]).colored()
        if (type.contains("ActionBar") || type.contains("A")){
            sendActionBar(msg,this)
        }
        if (type.contains("Title") || type.contains("T")){
            val title = msg.split("||")[0]
            val subtitle = msg.split("||")[1]
            this.sendTitle(title,subtitle,5,20,5)
        }
        if (type.contains("Sound") || type.contains("S")){
            if (msg == "LevelUp")
                this.playSound(this.location, Sound.ENTITY_PLAYER_LEVELUP, value[0] / 20f, value[0] / 20f)
            if (msg == "ExpChange")
                this.playSound(this.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, value[0] / 50f, value[0] / 20f)
        }
        if (type.contains("Chat") || type.contains("C")){
            sendMessage(msg)
        }
    }
}

private fun sendActionBar(msg:String,player:Player){
    val message = TypeActionBar()
    message.text = msg
    message.send(adaptPlayer(player))
}