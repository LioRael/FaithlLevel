package com.faithl.bukkit.faithllevel.internal.compat.attribute

import org.serverct.ersha.AttributePlus
import org.serverct.ersha.api.component.SubAttribute
import org.serverct.ersha.attribute.enums.AttributeType

class Attribute(private val name:String, private val holder:String):SubAttribute(12, 0.0,name, AttributeType.OTHER,holder){
    init {
        AttributePlus.attributeManager.registerAttribute(this)
    }
    override fun onLoad(): SubAttribute {
        return this
    }
}