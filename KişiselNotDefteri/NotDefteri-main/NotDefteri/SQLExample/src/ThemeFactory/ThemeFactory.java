package ThemeFactory;

public class ThemeFactory {
    public static AbstractTheme getTheme(String themeType) {
        switch (themeType.toLowerCase()) {
            case "dark":
                return new DarkTheme();
            case "light":
                return new LightTheme();
            default:
                return null;
        }
    }
}
