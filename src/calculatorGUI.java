// Calculator for performing basic maths.
// Does NOT stack operations. Eg. 1 + 1 + 1 will equal 2! This is a KNOWN bug!
// First time I have worked on GUI.
// Using Java Swing.
// Started 01/02/2021

// TODO handle stacking operations
// TODO fix bug where 0.9 will end up as 9

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;

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

    private Font font = new Font("Lucida Console", Font.PLAIN, 22);
    private Font fontLarge = new Font("Lucida Console", Font.PLAIN, 36);

    public String setDisplayString = "0";
    public String calculation_cache = "0";
    public String operator_cache = "";

    public static final String NAME = "Calculator ";
    public static final String VERSION = "v0.0.7";

    public enum sound {
        hover,
        click
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame(NAME + VERSION);
        jFrame.setContentPane(new calculatorGUI().Calculator);
        ImageIcon icon = new ImageIcon("./assets/smiley2.gif");
        jFrame.setIconImage(icon.getImage());
        jFrame.setPreferredSize(new Dimension(287, 319));
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
        Border eb = new EmptyBorder(10, 10, 10, 10);

        calculator_display.setEditable(false);
        resetDisplay();
        calculator_display.setFont(fontLarge);
        calculator_display.setHorizontalAlignment(JTextField.RIGHT);
        calculator_display.setBorder(eb);

        for (JButton button : getButtons()) {
            button.setFont(font);
            button.setBorder(eb);
        }
    }

    private void initialiseButtons() {
        cButton.addActionListener(e -> {
            playSound(sound.click);
            resetDisplay();
        });

        cButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        CEButton.addActionListener(e -> {
            playSound(sound.click);
            full_reset();
        });

        CEButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        for (int i = 0; i < 10; i++) {
            int increment = i;
            getButtons()[i].addActionListener(e -> {
                playSound(sound.click);
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
            getButtons()[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    playSound(sound.hover);
                }
            });
        }

        button_pointButton.addActionListener(e -> {
            playSound(sound.click);
            int count = 0;
            Character point = '.';
            for (Character c : calculator_display.getText().toCharArray()) {
                if (c == point) {
                    count++;
                }
            }

            if (count < 1) {
                setDisplayString = calculator_display.getText() + ".";
                calculator_display.setText(setDisplayString);
            } else {
                debugLogger.log("Caught excessive decimal point!", debugLogger.colour.green);
            }
        });

        button_pointButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        button_plusButton.addActionListener(e -> {
            playSound(sound.click);
            calculation_cache = calculator_display.getText();
            operator_cache = "+";
            resetDisplay();
        });

        button_plusButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        xButton.addActionListener(e -> {
            playSound(sound.click);
            calculation_cache = calculator_display.getText();
            operator_cache = "*";
            resetDisplay();
        });

        xButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        button_divideButton.addActionListener(e -> {
            playSound(sound.click);
            calculation_cache = calculator_display.getText();
            operator_cache = "/";
            resetDisplay();
        });

        button_divideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        button_minusButton.addActionListener(e -> {
            playSound(sound.click);
            calculation_cache = calculator_display.getText();
            operator_cache = "-";
            resetDisplay();
        });

        button_minusButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
        });

        button_equalsButton.addActionListener(e -> {
            playSound(sound.click);
            handleEquals();
        });

        button_equalsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                playSound(sound.hover);
            }
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
            bd = bd.round(new MathContext(10));
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
            bd = bd.round(new MathContext(10));
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
            bd = bd.round(new MathContext(10));
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
            bd = bd.round(new MathContext(10));
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

    public void playSound(sound s) {
        // sounds are from quake 3 arena
        switch (s) {
            case click:
                try {
                    URL url = new URL("file:./assets/buttonclick.wav");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    double gain = 0.01;
                    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                    clip.start();
                } catch (Exception e) {
                    debugLogger.log(e.toString(), debugLogger.colour.red);
                }
                break;

            case hover:
                try {
                    URL url = new URL("file:./assets/button_mouseover.wav");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    double gain = 0.01;
                    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                    clip.start();
                } catch (Exception e) {
                    debugLogger.log(e.toString(), debugLogger.colour.red);
                }
                break;

            default:
                debugLogger.log("No sound was specified", debugLogger.colour.yellow);
                break;
        }
    }
}
