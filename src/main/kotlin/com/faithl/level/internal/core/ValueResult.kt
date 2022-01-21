package com.faithl.level.internal.core

/**
 * @author Leosouthey
 * @since 2022/1/21-19:59
 **/
class ValueResult(val state: State = State.SUCCESS, val type: Type, val value: Any? = null) {

    enum class State {
        SUCCESS, FAILURE
    }

    enum class Type {
        DYNAMIC, FIXED, LEVEL_MAX, INVALID_TYPE;
    }

}