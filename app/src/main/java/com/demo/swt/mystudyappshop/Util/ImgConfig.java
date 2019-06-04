package com.demo.swt.mystudyappshop.Util;


/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/3/28
 */
public class ImgConfig{
    private static State state = State.AUTO;
    public enum State{

        BIG(1),  //正常模式（对应设置中的大图模式）
        AUTO(2),    //自动切换模式（对应设置中的自适应）
        SMALL(3);    //省流量模式（对应设置中的小图模式）

        private int level;
        State(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public State getState(int level){
            switch (level){
                case 1:
                    return BIG;
                case 2:
                    return AUTO;
                case 3:
                    return SMALL;
            }
            return BIG;
        }
    }

    /**
     * 初始化
     */
    public static void init() {
        state = getState();
    }

    /**
     * 获取模式状态
     * @return
     */
    private static State getState(){
//        int value = SharedPreferencesUtils.getPreference(IMG_READ, IMG_READ_KEY, 1);
        return null;
    }

    public static State getImConfigState() {
        return state;
    }


}
