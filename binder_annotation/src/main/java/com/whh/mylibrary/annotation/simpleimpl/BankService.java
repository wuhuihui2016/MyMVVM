package com.whh.mylibrary.annotation.simpleimpl;

/**
 * author : wuhuihui
 * date : 2021-06-21
 * desc : 转账处理业务类
 */
public class BankService {

    public static void main(String[] args) {
        BankService.transferMoney(100000);
    }

    /**
     * @param money 转账金额
     */
    @BankTransferMoney(maxMoney = 15000)
    public static void transferMoney(double money) {
        System.out.println(BankUtils.processAnnotationMoney(money));
    }

}
