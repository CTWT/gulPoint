package app.config;

/*
 * 생성자 : 신인철
 * 생성일 : 25.04.24
 * 파일명 : ScreeType.java
 * 수정자 : 
 * 수정일 :
 * 설명 : swing 상단 팀 정보 이벤트 동적 추가 삭제를 위한 클래스
 */

// 화면 description("화면명", "클래스")
// enum값을 추가만 하면 문제없이 팀을 증감 할 수 있다.
public enum ScreenType {
    TEAM1("1팀", "app.form.TeamForm"),
    TEAM2("2팀", "app.form.TeamForm"),
    TEAM3("3팀", "app.form.TeamForm"),
    TEAM4("4팀", "app.form.TeamForm"),
    TEAM9("9팀", "app.form.TeamForm"),
    TEAM5("5팀", "app.form.TeamForm"),;

    private final String name;
    private final String className;

    ScreenType(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}