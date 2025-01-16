package ThemeFactory;

import javax.swing.*;
import java.awt.*;

public class DarkTheme extends AbstractTheme {
    @Override
    public void applyTheme(JFrame frame) {
        Color backgroundColor = Color.DARK_GRAY;
        Color foregroundColor = Color.WHITE;

        frame.getContentPane().setBackground(backgroundColor);
        applyToAllComponents(frame.getContentPane(), backgroundColor, foregroundColor);
    }
}
