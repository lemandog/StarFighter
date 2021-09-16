package org.lemandog.enums;

public enum miskData {
    ONE(500000),
    TWO(300000),
    THREE(480000),
    FOUR(500000),
    FIVE(600000),
    SIX(800000),
    SEVEN(200000);
    public int levelLength;
    miskData(int length) {
        levelLength = length;
    }
}

