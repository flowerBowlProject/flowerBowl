package com.flowerbowl.web.util;


import java.util.Random;

public class RandomNameUtil {

    private static final String[] ADJECTIVES = {
            "기쁜", "슬픈", "화난", "행복한", "아름다운", "못생긴", "귀여운", "멋진", "졸린", "배고픈",
            "착한", "나쁜", "용감한", "겁쟁이", "똑똑한", "어리석은", "부지런한", "게으른", "친절한", "무례한",
            "재미있는", "지루한", "강한", "약한", "빠른", "느린", "뜨거운", "차가운", "비싼", "저렴한",
            "큰", "작은", "높은", "낮은", "넓은", "좁은", "긴", "짧은", "굵은", "가는",
            "붉은", "파란", "초록", "노란", "검은", "하얀", "회색", "갈색", "보라", "분홍",
            "깨끗한", "더러운", "시끄러운", "조용한", "밝은", "어두운", "달콤한", "쓴", "짠", "신",
            "매운", "부드러운", "딱딱한", "촉촉한", "건조한", "차분한", "격한", "엄한", "자유로운", "폐쇄적인"
    };

    private static final String[] NOUNS = {
            "고양이", "강아지", "토끼", "호랑이", "사자", "코끼리", "기린", "여우", "곰", "늑대",
            "다람쥐", "사슴", "펭귄", "독수리", "올빼미", "부엉이", "고래", "상어", "돌고래", "나무",
            "꽃", "풀", "바위", "산", "강", "바다", "구름", "별", "해", "달",
            "비", "눈", "바람", "천둥", "번개", "불", "물", "흙", "공기", "빛",
            "친구", "사랑", "행복", "슬픔", "기쁨", "노래", "춤", "책", "영화", "게임"
    };

    public static String generateNickname() {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        return adjective + " " + noun;
    }

}
