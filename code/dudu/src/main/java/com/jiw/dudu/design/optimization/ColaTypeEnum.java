package com.jiw.dudu.design.optimization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColaTypeEnum {

    PEPSI_COLA("PEPSI_COLA","百事可乐"),
    COCA_COLA("COCA_COLA","可口可乐");

    String code;
    String desc;

}
