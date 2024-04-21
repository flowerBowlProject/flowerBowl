package com.flowerbowl.web.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Category {
    DESSERT("디저트"),
    RICE("밥"),
    MEAT("고기류"),
    SOUP("국/찌개"),
    MISCELLANEOUS("기타류"),
    FRUITS("과일류"),
    SOYBEAN_NUTS("콩/견과류"),
    NOODLE("면류"),
    KIMCHI_JEOTGAL("김치/젓갈"),
    FUSION("퓨전"),
    FRIED_FOOD("튀김류"),
    BEVERAGES_ALCOHOL("음료/주류");

    private final String koreanName;

    Category(String koreanName) {
        this.koreanName = koreanName;
    }

    @JsonValue
    public String getKoreanName() {
        return koreanName;
    }

    @JsonCreator
    public static Category parsing(String inputValue) {
        return Arrays.stream(Category.values()).filter(type
                -> type.getKoreanName().equals(inputValue)).findFirst().orElse(null);
    }

}
