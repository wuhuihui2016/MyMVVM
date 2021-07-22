package com.whh.others.enumex;

/**
 * 解析枚举类
 * 枚举占用内存的大小比静态变量多得多，容易造成内存泄露，Android中建议使用
 */
public enum SexEnmu {
    MAN, WOMAN;

    public static void main(String[] args) {
        SexEnmu sexEnmu = SexEnmu.WOMAN;
        System.out.println("sexEnmu==>" + sexEnmu); //sexEnmu==>WOMAN
        switch (sexEnmu) {
            case MAN:
                System.out.println("男");
                break;
            case WOMAN:
                System.out.println("女");
                break;
        }

        sexEnmu.setName("whh");
        System.out.println(sexEnmu.getName()); //whh
    }

    /**
     * 构造方法仅能为private
     */
    SexEnmu() {
        //枚举中每一项都会调用一次此构造方法
    }

    //枚举类也可以有示例变量，方法，静态方法、抽象方法等
    private String name;

    SexEnmu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
