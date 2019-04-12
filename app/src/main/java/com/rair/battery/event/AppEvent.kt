package com.rair.battery.event

/**
 * @author Rair
 * @date 2019/4/12
 *
 * desc:
 */
class AppEvent {

    var type: Int = 0
    var value: Int = 0

    constructor(type: Int) {
        this.type = type
    }

    constructor(type: Int, value: Int) {
        this.type = type
        this.value = value
    }

}