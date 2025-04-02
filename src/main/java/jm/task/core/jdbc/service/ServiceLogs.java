package jm.task.core.jdbc.service;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceLogs {
    private static final Logger LOGGER = Logger.getLogger(ServiceLogs.class.getName());

    public void logError(String message, Exception e) {
        LOGGER.log(Level.SEVERE, message, e);
    }

    public void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }
}
