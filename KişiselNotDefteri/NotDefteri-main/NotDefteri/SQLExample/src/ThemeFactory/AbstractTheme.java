package ThemeFactory;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractTheme {
    // Tema uygulama metodu: Alt sınıflar bunu implement eder.
    public abstract void applyTheme(JFrame frame);

    protected void applyToAllComponents(Container container, Color backgroundColor, Color foregroundColor) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel || component instanceof JScrollPane) {
                component.setBackground(backgroundColor);
            } else if (component instanceof JLabel || component instanceof JButton || component instanceof JTextField || component instanceof JTextArea) {
                component.setBackground(backgroundColor);
                component.setForeground(foregroundColor);
            }

            if (component instanceof Container) {
                applyToAllComponents((Container) component, backgroundColor, foregroundColor);
            }
        }
    }
}
