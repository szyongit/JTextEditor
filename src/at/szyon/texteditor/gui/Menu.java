package at.szyon.texteditor.gui;

import at.szyon.texteditor.file.File;
import at.szyon.texteditor.icon.Icons;
import at.szyon.texteditor.logger.Logger;
import at.szyon.texteditor.main.Application;

import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.plaf.ColorChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class Menu {
    public static boolean isEncryptionEnabled;
    public static final void loadMenu() {
        Gui.menuBar = new JMenuBar();
        Gui.menuBar.setBorderPainted(false);

        Menu.fileMenu();
        Menu.editMenu();
        Menu.optionsMenu();
        Menu.aboutMenu();
    }

    private static void fileMenu() {
        //FILE
        JMenu fileMenu = new JMenu("File");
        fileMenu.getAccessibleContext().setAccessibleDescription("The menu for saving or loading files.");

        JMenuItem fileMenuNew = new JMenuItem(new AbstractAction("New") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!File.isSaved()) {
                    int overwriteOption = JOptionPane.showConfirmDialog(Gui.FRAME, "Do you want to overwrite it?", "Unsaved changes!", JOptionPane.YES_NO_OPTION);
                    if(overwriteOption == 1) {
                        return;
                    }
                }

                Gui.TEXT_AREA.setText("");
                File.setSaved(true);
                Logger.log("Created new document!");
            }
        });
        fileMenuNew.setIcon(new ImageIcon(Icons.NEW_DOCUMENT));
        //fileMenuNew.setMargin(new Insets(2, -20, 2, 2));
        fileMenu.add(fileMenuNew);

        JMenuItem fileMenuSave = new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                File.saveFile();
            }
        });
        fileMenuSave.setIcon(new ImageIcon(Icons.SAVE));
        //fileMenuSave.setMargin(new Insets(2, -20, 2, 2));
        fileMenu.add(fileMenuSave);

        JMenuItem fileMenuLoad = new JMenuItem(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {
                File.loadFile();
            }
        });
        fileMenuLoad.setIcon(new ImageIcon(Icons.LOAD));
        //fileMenuLoad.setMargin(new Insets(2, -20, 2, 2));
        fileMenu.add(fileMenuLoad);
        fileMenu.add(new JSeparator());

        JMenuItem fileMenuExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.exit();
            }
        });
        fileMenuExit.setIcon(new ImageIcon(Icons.EXIT));
        //fileMenuExit.setMargin(new Insets(2, -20, 2, 2));
        fileMenu.add(fileMenuExit);

        Gui.menuBar.add(fileMenu);
    }

    private static void editMenu() {
        //EDIT
        JMenu editMenu = new JMenu("Edit");
        editMenu.getAccessibleContext().setAccessibleDescription("Shows some editing options.");

        JMenuItem editMenuReplace = new JMenuItem(new AbstractAction("Replace") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea inputArea1 = new JTextArea();
                JTextArea inputArea2 = new JTextArea();

                Object[] message = new Object[] {
                        "Find", inputArea1,
                        "\n",
                        "Replace", inputArea2,
                };

                int option = JOptionPane.showConfirmDialog(
                        Gui.FRAME,
                        message,
                        "Replace",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null
                );

                if(option == 1) {
                    Logger.log("Replace action canceled!");
                    return;
                }
                Gui.TEXT_AREA.setText(Gui.TEXT_AREA.getText().replaceAll(inputArea1.getText(), inputArea2.getText()));
                Logger.log("Replace action invoked!");
            }
        });
        editMenuReplace.setIcon(null);
        editMenuReplace.setMargin(new Insets(2, -20, 2, 2));
        editMenu.add(editMenuReplace);

        JMenuItem editMenuWordCount = new JMenuItem(new AbstractAction("Count words") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea textOutputArea = new JTextArea();
                textOutputArea.setEditable(false);

                String guiText = Gui.TEXT_AREA.getText();
                if(guiText.isEmpty() || guiText.isBlank()) {
                    textOutputArea.setText(0 + "");
                } else {
                    textOutputArea.setText(Gui.TEXT_AREA.getText().split(" ").length + "");
                }


                int option = JOptionPane.showConfirmDialog(
                        Gui.FRAME,
                        new Object[] {"Amount:", textOutputArea},
                        "Words",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null
                );

                Logger.log("Counted words!");
            }
        });
        editMenuWordCount.setIcon(null);
        editMenuWordCount.setMargin(new Insets(2, -20, 2, 2));
        editMenu.add(editMenuWordCount);

        JMenuItem editMenuLorem = new JMenuItem(new AbstractAction("Lorem ipsum") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loremIpsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, " +
                        "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
                        "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
                        "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. " +
                        "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, " +
                        "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
                        "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
                        "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. " +
                        "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
                        "sed diam voluptua. " +
                        "At vero eos et accusam et justo duo dolores et ea rebum. " +
                        "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. " +
                        "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
                        "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
                        "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. " +
                        "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
                        "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
                        "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                        "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, " +
                        "vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. " +
                        "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat."
                ;

                JTextArea textInputArea = new JTextArea("0");
                textInputArea.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                            e.consume();
                        }
                    }
                });
                textInputArea.setEditable(true);
                JTextArea textOutputArea = new JTextArea();
                textOutputArea.setEditable(false);
                textOutputArea.setLineWrap(true);
                textOutputArea.setWrapStyleWord(true);


                int option1 = JOptionPane.showConfirmDialog(
                        Gui.FRAME,
                        new Object[] {"Amount of words:", textInputArea},
                        "Lorem ipsum",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null
                );

                if(option1 == 1) return;
                int amountOfWords;
                try {
                    amountOfWords = Integer.parseInt(textInputArea.getText());
                } catch (Exception exc) {
                    return;
                }

                if(amountOfWords <= 0) return;

                String[] loremIpsumWords = loremIpsum.split(" ");
                StringBuilder builder = new StringBuilder(amountOfWords);

                for(int i = 0; i < amountOfWords; i++) {
                    builder.append(loremIpsumWords[i % loremIpsumWords.length] + " ");
                }
                textOutputArea.setText(builder.toString().substring(0, builder.toString().length() - 1));

                JFrame frame = new JFrame("Lorem ipsum");
                frame.setIconImage(Icons.NOTEPAD);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(300, 200);
                frame.setLocation(
                        (int) (Gui.FRAME.getLocation().getX() + (Gui.FRAME.getWidth() / 2 - frame.getWidth() / 2)),
                        (int) (Gui.FRAME.getLocation().getY() + (Gui.FRAME.getHeight() / 2 - frame.getHeight() / 2))
                );
                frame.add(textOutputArea);

                JScrollPane scrollPane = new JScrollPane(textOutputArea);
                scrollPane.setWheelScrollingEnabled(true);
                frame.add(scrollPane);
                frame.setVisible(true);

                Logger.log("Generated lorem ipsum text!");
            }
        });
        editMenuLorem.setIcon(null);
        editMenuLorem.setMargin(new Insets(2, -20, 2, 2));
        editMenu.add(editMenuLorem);
        editMenu.add(new JSeparator());

        JMenuItem editMenuDate = new JMenuItem(new AbstractAction("Date") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime date = LocalDateTime.now();

                int year = date.getYear();
                long day = date.getDayOfMonth();
                String month = date.getMonth().toString();
                String dateString = day + "." + month + " " + year;

                JTextArea inputArea1 = new JTextArea(dateString);

                Object[] message = new Object[] {
                        "Current date", inputArea1,
                };

                JOptionPane.showConfirmDialog(
                        Gui.FRAME,
                        message,
                        "Date",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.PLAIN_MESSAGE,
                        null
                );
            }
        });
        editMenuDate.setIcon(null);
        editMenuDate.setMargin(new Insets(2, -20, 2, 2));
        editMenu.add(editMenuDate);

        JMenuItem editMenuTime = new JMenuItem(new AbstractAction("Time") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime date = LocalDateTime.now();

                int seconds = date.getSecond();
                int minutes = date.getMinute();
                int hours = date.getHour();
                String timeString = hours + ":" + minutes + ":" + seconds;

                JTextArea inputArea1 = new JTextArea(timeString);

                Object[] message = new Object[] {
                        "Current time", inputArea1,
                };

                JOptionPane.showConfirmDialog(
                        Gui.FRAME,
                        message,
                        "Time",
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.PLAIN_MESSAGE,
                        null
                );
            }
        });
        editMenuTime.setIcon(null);
        editMenuTime.setMargin(new Insets(2, -20, 2, 2));
        editMenu.add(editMenuTime);

        Gui.menuBar.add(editMenu);
    }

    private static void optionsMenu() {
        //OPTIONS
        JMenu optionsMenu = new JMenu("Options");

        JMenu optionsFontMenu = new JMenu("Font");
        optionsFontMenu.setMargin(new Insets(2, -20, 2, 2));
        optionsMenu.add(optionsFontMenu);
        JMenuItem optionsFontMenuItemStyle = new JMenuItem(new AbstractAction("Style") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = (String) JOptionPane.showInputDialog(
                        Gui.FRAME,
                        "Select a font:",
                        "Fonts",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        Gui.getFontNames(),
                        new Font(null, 2, 2).getFamily()
                );

                if(input == null) {
                    Logger.log("Font selection canceled");
                    return;
                }
                Font currentFont = Gui.TEXT_AREA.getFont();
                Gui.TEXT_AREA.setFont(new Font(input, currentFont.getStyle(), currentFont.getSize()));
                Logger.log("Set font to: " + Gui.TEXT_AREA.getFont().getName());
            }
        });
        optionsFontMenuItemStyle.setIcon(null);
        optionsFontMenuItemStyle.setMargin(new Insets(2, -20, 2, 2));
        optionsFontMenu.add(optionsFontMenuItemStyle);

        JMenu optionsVisualsMenu = new JMenu("Visuals");
        optionsVisualsMenu.setMargin(new Insets(2, -20, 2, 2));
        optionsMenu.add(optionsVisualsMenu);

        JMenuItem optionsVisualsMenuItemTextColor = new JMenuItem(new AbstractAction("Text color") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(Gui.FRAME, "Choose text color", Gui.TEXT_AREA.getForeground(), false);
                Gui.TEXT_AREA.setForeground(color);

                Logger.log("Set text color to: " + Gui.TEXT_AREA.getForeground().toString());
            }
        });
        optionsVisualsMenuItemTextColor.setIcon(null);
        optionsVisualsMenuItemTextColor.setMargin(new Insets(2, -20, 2, 2));
        optionsVisualsMenu.add(optionsVisualsMenuItemTextColor);

        JMenuItem optionsVisualsMenuItemBackgroundColor = new JMenuItem(new AbstractAction("Background color") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(Gui.FRAME, "Choose background color", Gui.TEXT_AREA.getBackground(), false);
                Gui.TEXT_AREA.setBackground(color);

                Logger.log("Set background color to: " + Gui.TEXT_AREA.getBackground().toString());
            }
        });
        optionsVisualsMenuItemBackgroundColor.setIcon(null);
        optionsVisualsMenuItemBackgroundColor.setMargin(new Insets(2, -20, 2, 2));
        optionsVisualsMenu.add(optionsVisualsMenuItemBackgroundColor);

        JMenuItem optionsVisualsMenuItemSelectedTextColor = new JMenuItem(new AbstractAction("Selected text color") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(Gui.FRAME, "Choose selected text color", Gui.TEXT_AREA.getSelectedTextColor(), false);
                Gui.TEXT_AREA.setSelectedTextColor(color);

                Logger.log("Set selected text color to: " + Gui.TEXT_AREA.getSelectedTextColor().toString());
            }
        });
        optionsVisualsMenuItemSelectedTextColor.setIcon(null);
        optionsVisualsMenuItemSelectedTextColor.setMargin(new Insets(2, -20, 2, 2));
        optionsVisualsMenu.add(optionsVisualsMenuItemSelectedTextColor);

        JMenuItem optionsVisualsMenuItemSelectionColor = new JMenuItem(new AbstractAction("Selection color") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(Gui.FRAME, "Choose selection color", Gui.TEXT_AREA.getSelectionColor(), false);
                Gui.TEXT_AREA.setSelectionColor(color);

                Logger.log("Set selection color to: " + Gui.TEXT_AREA.getSelectionColor().toString());
            }
        });
        optionsVisualsMenuItemSelectionColor.setIcon(null);
        optionsVisualsMenuItemSelectionColor.setMargin(new Insets(2, -20, 2, 2));
        optionsVisualsMenu.add(optionsVisualsMenuItemSelectionColor);


        JCheckBoxMenuItem optionsMenuEncryption = new JCheckBoxMenuItem(new AbstractAction("Enable file encryption") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEncryptionEnabled = !isEncryptionEnabled;
                Logger.log("File encryption set to: " + isEncryptionEnabled);
            }
        });
        optionsMenuEncryption.setState(isEncryptionEnabled);
        optionsMenuEncryption.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        optionsMenuEncryption.setMargin(new Insets(2, -4, 2, 2));
        optionsMenu.add(optionsMenuEncryption);

        Gui.menuBar.add(optionsMenu);
    }

    private static void aboutMenu() {
        //ABOUT
        JMenu aboutMenu = new JMenu("About");
        JMenuItem aboutMenuInfo = new JMenuItem(new AbstractAction("Info") {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visitGitHub = JOptionPane.showOptionDialog(
                        Gui.FRAME,
                        "Author: Szyon\nVersion: 1.0\n\nCopyright © Szyon 2022\n",
                        "About",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        new ImageIcon(Icons.NOTEPAD.getScaledInstance(60, 60, Image.SCALE_DEFAULT)),
                        new String[]{"OK", "Visit Github"},
                        "OK") == 1
                        ;

                if(visitGitHub && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/szyongit"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        aboutMenuInfo.setIcon(null);
        aboutMenuInfo.setMargin(new Insets(2, -20, 2, 2));
        aboutMenu.add(aboutMenuInfo);

        JMenuItem aboutMenuLogger = new JMenuItem(new AbstractAction("Show logger") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gui.loadLogger();
            }
        });
        aboutMenuLogger.setIcon(null);
        aboutMenuLogger.setMargin(new Insets(2, -20, 2, 2));
        aboutMenu.add(aboutMenuLogger);

        Gui.menuBar.add(aboutMenu);
    }
}
