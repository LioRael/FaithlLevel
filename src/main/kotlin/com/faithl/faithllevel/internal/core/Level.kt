package com.faithl.faithllevel.internal.core

/**
 * 等级抽象类
 *
 * @author Leosouthey
 * @since 2022/1/8-0:45
 **/
abstract class Level {

    abstract fun getLevel(target: String): Int
    abstract fun getExp(target: String): Int
    abstract fun setLevel(target: String, value: Int): Boolean
    abstract fun setExp(target: String, value: Int): Boolean
    abstract fun addExp(target: String, value: Int): Boolean
    abstract fun addLevel(target: String, value: Int): Boolean
    abstract fun takeExp(target: String, value: Int): Boolean
    abstract fun takeLevel(target: String, value: Int): Boolean

}