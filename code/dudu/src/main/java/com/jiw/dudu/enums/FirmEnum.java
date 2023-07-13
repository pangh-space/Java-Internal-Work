package com.jiw.dudu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 厂商枚举类
 */
@Getter
@AllArgsConstructor
public enum FirmEnum {

    ZONGHE_KEFU("zhkf","综合客服"),
//    XYK_GZH("xyk_gzh","信用卡公众号"),
    CRM("crm","CRM系统");

    private String code;
    private String desc;

    /**
     * 枚举遍历和查找
     *
     * @param code
     * @return
     */
    public static FirmEnum getFirmEnum(String code){
        return Arrays.stream(FirmEnum.values()).filter(x -> x.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }

}
