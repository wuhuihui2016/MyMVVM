package com.whh.others.genericity;

public class Sub2 extends Base<String> {

    @Override
    public void setValue(String value) {
        System.out.println("called Sub2.setValue");
        super.setValue(value);
    }
}
