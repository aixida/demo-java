package util;

import javax.swing.*;

public abstract class BaseFrame extends JFrame {

    public BaseFrame(){}
    public BaseFrame(String title){
        super(title);
    }

    protected void init(){
        setup();
        addElement();
        addListener();
        setFrameSelf();
    }

    //设置 字体  颜色  背景  布局
    protected abstract void setup();
    //将组件添加到窗体中
    protected abstract void addElement();
    //添加事件监听
    protected abstract void addListener();
    //设置窗体自身
    protected abstract void setFrameSelf();
}
