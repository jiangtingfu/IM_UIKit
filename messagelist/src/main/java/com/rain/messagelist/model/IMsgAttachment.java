package com.rain.messagelist.model;

import java.io.Serializable;

/**
 * @Author: Rain
 * @Org: www.yudu233.com
 * @Email: yudu233@gmail.com
 * @ClassName: IMsgAttachment
 * @CreateDate: 2020/6/30 0:09
 * @Describe: 消息附件基础接口
 */

public interface IMsgAttachment extends Serializable {

    /**
     * 将消息附件序列化为字符串，存储到消息数据库或发送到服务器。<br>
     *
     * @param send 如果你的附件在本地需要存储额外数据，而这些数据不需要发送到服务器，可依据该参数值区别对待。
     * @return
     */
    String toJson(boolean send);
}