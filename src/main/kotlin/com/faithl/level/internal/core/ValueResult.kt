package com.faithl.level.internal.core

/**
 * @author Leosouthey
 * @since 2022/1/21-19:59
 **/
data class ValueResult(val state: State = State.SUCCESS, val value: Any? = null, val reason: Reason? = null) {

    enum class State {
        SUCCESS, FAILURE
    }

    enum class Reason {
        LEVEL_MAX, INVALID_TYPE;
    }

}