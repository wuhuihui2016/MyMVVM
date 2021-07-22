package com.whh.others.abstracts;

/**
 * 继承子抽象类，必须实现其抽象方法
 * author:wuhuihui 2021.07.22
 */
public abstract class Wind extends Instrument {
    @Override
    public void play() {
    }

    public abstract void playWind();

    /**
     * 继承子抽象类，必须实现其抽象方法
     * 祖父抽象方法可选实现！
     */
    class WindSon extends Wind{

        @Override
        public void playWind() {

        }

        @Override
        public void play() {
            super.play();
        }
    }
}
