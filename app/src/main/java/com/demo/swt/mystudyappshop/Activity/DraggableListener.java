package com.demo.swt.mystudyappshop.Activity;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@priemdu.cn
 * 时间: 2018/1/2
 */
public interface  DraggableListener {
    /**
     * Called when the view is minimized.
     */
    void onClosedToBottom();

    /**
     * Called when the view is closed to the left.
     */
    void onClosedToLeft();

    /**
     * Called when the view is closed to the right.
     */
    void onClosedToRight();

    /**
     * Called when the child view location changed
     * @param top
     */
    void onBackgroundChanged(int top);

    boolean isForbidden();
}
