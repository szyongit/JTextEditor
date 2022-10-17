package at.szyon.texteditor.gui;

import at.szyon.texteditor.file.File;
import at.szyon.texteditor.icon.Icons;
import at.szyon.texteditor.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Gui {
    public static JFrame FRAME, LOGGER_FRAME;
    public static JTextArea TEXT_AREA, LOGGER_TEXT_AREA;

    public static JMenuBar menuBar;

    private static boolean isControlPressed;

    public static final void load() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        Menu.loadMenu();
        Gui.loadTextArea();
        Gui.loadFrame(Gui.menuBar, Gui.TEXT_AREA);
    }

    private static final void loadFrame(JMenuBar menuBar, Component... components) throws IOException {
        Gui.FRAME = new JFrame("Text editor");
        Gui.FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Gui.FRAME.setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Gui.FRAME.setSize(screenSize.width / 2, screenSize.height / 2);
        Gui.FRAME.setLocation(new Point(screenSize.width / 2 - Gui.FRAME.getWidth() / 2, screenSize.height / 2 - Gui.FRAME.getHeight() / 2));

        Gui.FRAME.setIconImage(Icons.NOTEPAD);

        Gui.FRAME.setJMenuBar(menuBar);

        for (Component component : components) {
            Gui.FRAME.add(component);
        }

        if(Gui.TEXT_AREA != null) {
            JScrollPane scrollPane = new JScrollPane(Gui.TEXT_AREA);
            scrollPane.setWheelScrollingEnabled(true);

            Gui.FRAME.add(scrollPane);
        }
        Gui.FRAME.setVisible(true);
    }

    private static final void loadTextArea() {
        Gui.TEXT_AREA = new JTextArea();
        Gui.TEXT_AREA.setEditable(true);
        Gui.TEXT_AREA.setLineWrap(false);
        Gui.TEXT_AREA.setTabSize(3);
        Gui.TEXT_AREA.setFont(Gui.TEXT_AREA.getFont().deriveFont(15.0f));

        Gui.TEXT_AREA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown()) Gui.isControlPressed = true;

                if(isControlPressed && e.getKeyCode() == KeyEvent.VK_S) {
                    File.saveFile();
                    return;
                }

                if(e.isActionKey()) return;
                File.setSaved(false);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!e.isControlDown()) Gui.isControlPressed = false;
            }
        });

        Gui.TEXT_AREA.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(!isControlPressed) return;

                int modifier = (e.getWheelRotation() * e.getScrollAmount()) * -1;
                Font currentFont = Gui.TEXT_AREA.getFont();
                Gui.TEXT_AREA.setFont(currentFont.deriveFont((float) currentFont.getSize() + modifier));
            }
        });
    }

    public static void loadLogger() {
        if(Gui.LOGGER_FRAME != null) {
            return;
        }

        Gui.LOGGER_FRAME = new JFrame("Logger");
        Gui.LOGGER_FRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Gui.LOGGER_FRAME.setIconImage(Icons.NOTEPAD);

        Gui.LOGGER_FRAME.setSize(300, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Gui.LOGGER_FRAME.setLocation(new Point(screenSize.width / 2 - Gui.LOGGER_FRAME.getWidth() / 2, screenSize.height / 2 - Gui.LOGGER_FRAME.getHeight() / 2));

        Gui.LOGGER_TEXT_AREA = new JTextArea(Logger.getString());
        Gui.LOGGER_TEXT_AREA.setEditable(false);
        Gui.LOGGER_TEXT_AREA.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        Gui.LOGGER_FRAME.add(Gui.LOGGER_TEXT_AREA);

        JScrollPane scrollPane = new JScrollPane(Gui.LOGGER_TEXT_AREA);
        scrollPane.setWheelScrollingEnabled(true);
        Gui.LOGGER_FRAME.add(scrollPane);

        Gui.LOGGER_FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Gui.unloadLogger();
                Logger.log("Logger closed!");
            }
        });

        Gui.LOGGER_FRAME.setVisible(true);
        Logger.log("Logger opened!");
    }

    public static void unloadLogger() {
        if(Gui.LOGGER_FRAME != null) {
            Gui.LOGGER_FRAME.dispose();
        }

        Gui.LOGGER_FRAME = null;
        Gui.LOGGER_TEXT_AREA = null;
    }

    public static final String[] getFontNames() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return graphicsEnvironment.getAvailableFontFamilyNames();
    }
}
