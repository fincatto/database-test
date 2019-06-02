package com.fincatto.databasetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Loggable {
    
    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
    
    static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}