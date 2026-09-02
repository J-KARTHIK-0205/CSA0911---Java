interface Logger {
    void debug(String message);
    void info(String message);
    void warning(String message);
    void error(String message);
}

class Log4jLogger implements Logger {
    @Override
    public void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }

    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void warning(String message) {
        System.out.println("[WARNING] " + message);
    }

    @Override
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}

public class Q70_LoggerInterface {
    public static void main(String[] args) {
        Logger logger = new Log4jLogger();
        logger.debug("Starting application");
        logger.info("Application started successfully");
        logger.warning("Low disk space");
        logger.error("Unhandled exception occurred");
    }
}
