package at.szyon.texteditor.icon;

import at.szyon.texteditor.gui.Gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Icons {
    public static Image NOTEPAD, SAVE, LOAD, NEW_DOCUMENT, EXIT;

    public static void load() throws IOException {
        Icons.NOTEPAD = ImageIO.read(Gui.class.getResourceAsStream("/rsc/notepad.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Icons.SAVE = ImageIO.read(Gui.class.getResourceAsStream("/rsc/save.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
        Icons.LOAD = ImageIO.read(Gui.class.getResourceAsStream("/rsc/load.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
        Icons.NEW_DOCUMENT = ImageIO.read(Gui.class.getResourceAsStream("/rsc/new_document.png")).getScaledInstance(15, 13, Image.SCALE_SMOOTH);
        Icons.EXIT = ImageIO.read(Gui.class.getResourceAsStream("/rsc/exit.png")).getScaledInstance(13, 13, Image.SCALE_SMOOTH);
    }
}
