package com.example.test001.event

import org.greenrobot.eventbus.EventBus


class EventBusUtils {
    companion object {
        /**
         * 发送消息
         */
        fun post(code: Int, message: String) {
            val event = CommonEvent(code, message)
            EventBus.getDefault().post(event)
        }

        /**
         * 发送粘性消息
         */
        fun postSticky(code: Int, message: String) {
            val event = CommonEvent(code, message)
            EventBus.getDefault().postSticky(event)
        }
    }
}