package com.alexeiddg.backend.scraper.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HtmlFileWriter {

    private static final Logger logger = Logger.getLogger(HtmlFileWriter.class.getName());

    public static void saveHtmlToFile(String htmlContent, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(htmlContent);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while writing HTML content to file", e);
        }
    }
}

