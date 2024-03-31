package com.flowerbowl.web.domain;

public enum Category {
    DESSERT("디저트"),
    RICE("밥"),
    MEAT("고기류"),
    SOUP("국"),
    STEW("찌개"),
    MISCELLANEOUS("기타"),
    FRUITS("과일"),
    SOYBEAN_NUTS("콩견과류"),
    NOODLE("면류"),
    KIMCHI_JEOTGAL("김치/젓갈"),
    FUSION("퓨전"),
    FRIED_FOOD("튀김"),
    BEVERAGES_ALCOHOL("음료/주류");

    private final String koreanName;

    Category(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }


}
