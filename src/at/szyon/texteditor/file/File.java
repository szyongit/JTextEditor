package at.szyon.texteditor.file;

import at.szyon.basicfile.FileHandler;
import at.szyon.texteditor.gui.Gui;
import at.szyon.texteditor.gui.Menu;
import at.szyon.texteditor.logger.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.Base64;

public class File {

    private static boolean isSaved = true;

    private static JFileChooser fileChooser;
    public static void saveFile() {
        fileChooser = new JFileChooser(at.szyon.basicfile.FileUtility.roots()[0]);
        fileChooser.setSelectedFile(new java.io.File("text.txt"));
        fileChooser.setDialogTitle("Save");

        int chooserOption = fileChooser.showSaveDialog(Gui.FRAME);

        if(chooserOption == JFileChooser.CANCEL_OPTION || chooserOption == JFileChooser.ERROR_OPTION) {
            File.setSaved(false);
            Logger.log("File securing canceled!");
            return;
        }

        try {
            FileHandler fileHandler = new FileHandler(fileChooser.getSelectedFile());
            if(Menu.isEncryptionEnabled) {
                fileHandler.getEncryptor().enable((s -> {
                    return Base64.getEncoder().encodeToString(s.getBytes());
                }), (s -> {
                    return new String(Base64.getDecoder().decode(s));
                }));
            }

            if(!fileHandler.fileExists()) {
                fileHandler.createFile();
                fileHandler.overwrite(Gui.TEXT_AREA.getText());
                File.setSaved(true);
                Logger.log("File saved!");
                return;
            }

            int overwriteOption = JOptionPane.showConfirmDialog(Gui.FRAME, "Do you want to overwrite it?", "The selected file already exists!", JOptionPane.YES_NO_OPTION);
            if(overwriteOption == 0) {
                fileHandler.overwrite(Gui.TEXT_AREA.getText());
                File.setSaved(true);
                Logger.log("File saved!");
                return;
            }

            File.setSaved(false);
            Logger.log("File securing canceled!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadFile() {
        fileChooser = new JFileChooser(at.szyon.basicfile.FileUtility.roots()[0]);
        fileChooser.setDialogTitle("Load");
        int chooserOption = fileChooser.showOpenDialog(Gui.FRAME);

        if(chooserOption == JFileChooser.CANCEL_OPTION || chooserOption == JFileChooser.ERROR_OPTION) {
            Logger.log("File loading canceled!");
            return;
        }

        FileHandler fileHandler = new FileHandler(fileChooser.getSelectedFile());
        if(Menu.isEncryptionEnabled) {
            fileHandler.getEncryptor().enable((s -> {
                return Base64.getEncoder().encodeToString(s.getBytes());
            }), (s -> {
                return new String(Base64.getDecoder().decode(s));
            }));
        }

        if(!fileHandler.fileExists()) {
            JOptionPane.showMessageDialog(Gui.FRAME, "The selected file does not exist!", "An error occurred!", JOptionPane.ERROR_MESSAGE);
            Logger.log("File loading canceled!");
            return;
        }

        fileHandler.update(false, true);

        StringBuilder text = new StringBuilder();
        String[] lines = fileHandler.readAll();

        for (String line : lines) {
            text.append(line);
            text.append(System.lineSeparator());
        }

        Gui.TEXT_AREA.setText(text.toString());
        Logger.log("File loaded!");
    }

    public static final boolean isSaved() {
        return File.isSaved;
    }
    public static final void setSaved(boolean saved) {
        if(File.isSaved == saved) return;
        File.isSaved = saved;

        if(!saved) {
            Gui.FRAME.setTitle(Gui.FRAME.getTitle() + " (unsaved)");
        } else {
            String guiTitle = Gui.FRAME.getTitle();
            int unsavedIndex = guiTitle.lastIndexOf("(unsaved)");
            if(unsavedIndex < 0) return;
            Gui.FRAME.setTitle(guiTitle.substring(0, guiTitle.length() - unsavedIndex + 2));
        }
    }
}
