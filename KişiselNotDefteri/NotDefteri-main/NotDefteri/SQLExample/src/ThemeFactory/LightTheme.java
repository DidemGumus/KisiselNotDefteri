package ThemeFactory;

import javax.swing.*;
import java.awt.*;

public class LightTheme extends AbstractTheme {
    @Override
    public void applyTheme(JFrame frame) {
        Color backgroundColor = Color.WHITE;
        Color foregroundColor = Color.BLACK;

        frame.getContentPane().setBackground(backgroundColor);
        applyToAllComponents(frame.getContentPane(), backgroundColor, foregroundColor);
    }
}
