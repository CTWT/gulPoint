package app.config;

// 화면 description("화면명", "클래스")
public enum ScreenType {
    TEAM1("1팀", "app.form.TeamForm"),
    TEAM2("2팀", "app.form.TeamForm"),
    TEAM3("3팀", "app.form.TeamForm"),
    TEAM4("4팀", "app.form.TeamForm"),
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