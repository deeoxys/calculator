import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class calculatorGUI {
    private JPanel Calculator;
    private JTextField calculator_display;
    private JButton a0Button;
    private JButton a7Button;
    private JButton a4Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton button_divideButton;
    private JButton CEButton;
    private JButton a5Button;
    private JButton a6Button;
    private JButton xButton;
    private JButton cButton;
    private JButton button_equalsButton;
    private JButton a8Button;
    private JButton a9Button;
    private JButton button_minusButton;
    private JButton button_pointButton;
    private JButton button_plusButton;

    private Font impactFont = new Font("Impact", Font.BOLD, 60);
    private Font impactFontLarge = new Font("Impact", Font.BOLD, 100);

    public String setDisplayString = "0";
    public String calculation_cache = "0";
    public String operator_cache = "";

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Calculator");
        jFrame.setContentPane(new calculatorGUI().Calculator);
        jFrame.setPreferredSize(new Dimension(600, 800));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            debugLogger.log(String.valueOf(e), debugLogger.colour.red);
        }
    }

    private void update() {
        calculator_display.setText(setDisplayString);
        calculator_display.repaint();
    }

    public calculatorGUI() {
        initialiseGUI();
        initialiseButtons();
    }

    private void resetDisplay() {
        setDisplayString = "0";
        update();
    }

    private void full_reset() {
        setDisplayString = "0";
        calculation_cache = "0";
        operator_cache = "";
        update();
    }

    private void initialiseGUI() {
        calculator_display.setEditable(false);
        resetDisplay();
        calculator_display.setFont(impactFontLarge);
        calculator_display.setHorizontalAlignment(JTextField.RIGHT);

        for (JButton button : getButtons()) {
            button.setFont(impactFont);
            button.setMargin(new Insets(0, 0, 0, 5));
        }
    }

    private void initialiseButtons() {
        cButton.addActionListener(e -> resetDisplay());
        CEButton.addActionListener(e -> full_reset());

        for (int i = 0; i < 10; i++) {
            int increment = i;
            getButtons()[i].addActionListener(e -> {
                if (calculator_display.getText().length() < 10) {
                    if (calculator_display.getText().startsWith("0")) {
                        setDisplayString = getButtons()[increment].getText();
                        update();
                    } else {
                        setDisplayString = (setDisplayString + getButtons()[increment].getText());
                        update();
                    }
                } else {
                    debugLogger.log("Max digits reached!", debugLogger.colour.yellow);
                }
            });
        }

        button_pointButton.addActionListener(e -> {
            if (!calculator_display.getText().endsWith(".")) {
                calculator_display.setText(setDisplayString + ".");
            }
        });

        button_plusButton.addActionListener(e -> {
            calculation_cache = calculator_display.getText();
            operator_cache = "+";
            handleEquals();
            resetDisplay();
        });

        xButton.addActionListener(e -> {
            calculation_cache = calculator_display.getText();
            operator_cache = "*";
            handleEquals();
            resetDisplay();
        });

        button_divideButton.addActionListener(e -> {
            calculation_cache = calculator_display.getText();
            operator_cache = "/";
            handleEquals();
            resetDisplay();
        });

        button_minusButton.addActionListener(e -> {
            calculation_cache = calculator_display.getText();
            operator_cache = "-";
            handleEquals();
            resetDisplay();
        });

        button_equalsButton.addActionListener(e -> {
            handleEquals();
        });
    }

    private JButton[] getButtons() {
        JButton[] buttons = new JButton[18];

        buttons[0] = a0Button;
        buttons[1] = a1Button;
        buttons[2] = a2Button;
        buttons[3] = a3Button;
        buttons[4] = a4Button;
        buttons[5] = a5Button;
        buttons[6] = a6Button;
        buttons[7] = a7Button;
        buttons[8] = a8Button;
        buttons[9] = a9Button;
        buttons[10] = button_equalsButton;
        buttons[11] = CEButton;
        buttons[12] = cButton;
        buttons[13] = button_minusButton;
        buttons[14] = button_plusButton;
        buttons[15] = button_divideButton;
        buttons[16] = xButton;
        buttons[17] = button_pointButton;

        return buttons;
    }

    private String addition_function() {
        double number_one;
        double number_two;
        try {
            number_one = Double.parseDouble(calculation_cache);
            number_two = Double.parseDouble(calculator_display.getText());
        } catch (Exception e) {
            debugLogger.log(String.valueOf(e), debugLogger.colour.red);
            return "0";
        }

        if (String.valueOf(formatDoubleNumber(number_one + number_two)).length() < 10) {
            return String.valueOf(formatDoubleNumber(number_one + number_two));
        } else if (String.valueOf(formatDoubleNumber(number_one + number_two)).length() >= 10) {
            BigDecimal bd = new BigDecimal(formatDoubleNumber(number_one + number_two));
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();

            debugLogger.log("Calculation result breached 10 digits!", debugLogger.colour.yellow);
            return String.valueOf(rounded);
        }
        debugLogger.log("Error occurred at addition_function.", debugLogger.colour.red);
        return "0";
    }

    private String subtract_function() {
        double number_one;
        double number_two;
        try {
            number_one = Double.parseDouble(calculation_cache);
            number_two = Double.parseDouble(calculator_display.getText());
        } catch (Exception e) {
            debugLogger.log(String.valueOf(e), debugLogger.colour.red);
            return "0";
        }
        if (String.valueOf(formatDoubleNumber(number_one - number_two)).length() < 10) {
            return String.valueOf(formatDoubleNumber(number_one - number_two));
        } else if (String.valueOf(formatDoubleNumber(number_one - number_two)).length() >= 10) {
            BigDecimal bd = new BigDecimal(formatDoubleNumber(number_one - number_two));
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();

            debugLogger.log("Calculation result breached 10 digits!", debugLogger.colour.yellow);
            return String.valueOf(rounded);
        }
        debugLogger.log("Error occurred at subtraction_function.", debugLogger.colour.red);
        return "0";
    }

    private String multiplication_function() {
        double number_one;
        double number_two;
        try {
            number_one = Double.parseDouble(calculation_cache);
            number_two = Double.parseDouble(calculator_display.getText());
        } catch (Exception e) {
            debugLogger.log(String.valueOf(e), debugLogger.colour.red);
            return "0";
        }
        if (String.valueOf(formatDoubleNumber(number_one * number_two)).length() < 10) {
            return String.valueOf(formatDoubleNumber(number_one * number_two));
        } else if (String.valueOf(formatDoubleNumber(number_one * number_two)).length() >= 10) {
            BigDecimal bd = new BigDecimal(formatDoubleNumber(number_one * number_two));
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();

            debugLogger.log("Calculation result breached 10 digits!", debugLogger.colour.yellow);
            return String.valueOf(rounded);
        }
        debugLogger.log("Error occurred at multiplication_function.", debugLogger.colour.red);
        return "0";
    }

    private String division_function() {
        double number_one;
        double number_two;
        try {
            number_one = Double.parseDouble(calculation_cache);
            number_two = Double.parseDouble(calculator_display.getText());
        } catch (Exception e) {
            debugLogger.log(String.valueOf(e), debugLogger.colour.red);
            return "0";
        }
        if (String.valueOf(formatDoubleNumber(number_one / number_two)).length() < 10) {
            return String.valueOf(formatDoubleNumber(number_one / number_two));
        } else if (String.valueOf(formatDoubleNumber(number_one / number_two)).length() >= 10) {
            BigDecimal bd = new BigDecimal(formatDoubleNumber(number_one / number_two));
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();

            debugLogger.log("Calculation result breached 10 digits!", debugLogger.colour.yellow);
            return String.valueOf(rounded);
        }
        debugLogger.log("Error occurred at division_function.", debugLogger.colour.red);
        return "0";
    }

    private void handleEquals() {
        switch (operator_cache) {
            case "":
                debugLogger.log("Operator cache was null- resetting display now.", debugLogger.colour.yellow);
                resetDisplay();
                break;

            case "+":
                setDisplayString = addition_function();
                update();
                break;

            case "*":
                setDisplayString = multiplication_function();
                update();
                break;

            case "/":
                setDisplayString = division_function();
                update();
                break;

            case "-":
                setDisplayString = subtract_function();
                update();
                break;

            default:
                debugLogger.log("Something broke at handleEquals.", debugLogger.colour.red);
                break;
        }
    }

    public static String formatDoubleNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }
}
