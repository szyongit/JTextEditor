package at.szyon.texteditor.main;

import at.szyon.texteditor.file.File;
import at.szyon.texteditor.gui.Gui;
import at.szyon.texteditor.icon.Icons;
import at.szyon.texteditor.logger.Logger;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Icons.load();
                Gui.load();
                Logger.log("Gui loaded!");
            } catch (Exception e) {
                Logger.log("An error occurred: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Error message: " + e.getMessage(), "An unexpected error occurred!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static final void exit() {
        if(!File.isSaved()) {
            int shouldSaveOption = JOptionPane.showConfirmDialog(
                    Gui.FRAME,
                    "Do you want to save the file?",
                    "Unsaved changes!",
                    JOptionPane.YES_NO_OPTION
            );
            if(shouldSaveOption == 0) File.saveFile();
        }

        System.exit(0);
    }
}
