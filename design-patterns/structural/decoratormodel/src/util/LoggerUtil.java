
package util;

import java.util.logging.*;

public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            logger.setLevel(Level.ALL);
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            logger.addHandler(ch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }
}
