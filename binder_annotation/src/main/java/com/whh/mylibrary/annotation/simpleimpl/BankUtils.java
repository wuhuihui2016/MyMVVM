package com.whh.mylibrary.annotation.simpleimpl;

import java.lang.reflect.Method;

/**
 * author : wuhuihui
 * date : 2021-06-21
 * desc :
 */
public class BankUtils {

    /**
     * 处理转账金额是否在限额范围
     *
     * @param money
     * @return
     */
    public static String processAnnotationMoney(double money) {
        try {
            /* getDeclaredMethod()获取的是类自身声明的所有方法，包含public、protected和private方法
            getMethod()获取的是类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法 */
            Method transferMoney = BankService.class.getDeclaredMethod("transferMoney", double.class);
            boolean annotationPresent = transferMoney.isAnnotationPresent(BankTransferMoney.class);
            if (annotationPresent) {
                BankTransferMoney annotation = transferMoney.getAnnotation(BankTransferMoney.class);
                double l = annotation.maxMoney();
                if (money > l) {
                    return "转账金额大于限额，转账失败";
                } else {
                    return "转账金额为:" + money + "，转账成功";
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "转账处理失败";
    }

}
