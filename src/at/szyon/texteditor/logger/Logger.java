package at.szyon.texteditor.logger;

import at.szyon.texteditor.gui.Gui;

import java.time.LocalDateTime;

public class Logger {
    private static StringBuilder stringBuilder;

    public static void log(String string) {
        if(stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        LocalDateTime date = LocalDateTime.now();
        int seconds = date.getSecond();
        int minutes = date.getMinute();
        int hours = date.getHour();
        String timeString = hours + ":" + minutes + ":" + seconds;

        stringBuilder.append("[" + timeString + "] " + string);
        stringBuilder.append(System.lineSeparator());

        if(Gui.LOGGER_TEXT_AREA != null) {
            Gui.LOGGER_TEXT_AREA.setText(Logger.getString());
        }
    }

    public static String getString() {
        return (stringBuilder == null ? null : stringBuilder.toString());
    }
}
