/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mousepilots.es.test.constraints;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import org.mousepilots.es.test.server.Request;

/**
 *
 * @author bhofsted
 * @param <T>
 */
public class ConstraintExceptionLogger<T extends Request> {

    public void logger(Set<ConstraintViolation<T>> exceptions) {

        List<String> lines = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Europe/Amsterdam"));
        lines.add("\n"+dateTime.format(dateFormat));
//        lines.add(ZoneId.systemDefault().toString()); //gives incorrect timezone
        for (ConstraintViolation<T> cv : exceptions) {
            lines.add(cv.getMessage());
        }

        Path file = Paths.get("constraintLogs\\failedConstraints.txt");
        try {
            if (Files.notExists(Paths.get("constraintLogs"))) {
                Files.createDirectory(Paths.get("constraintLogs"));
            }
            if (Files.notExists(file)) {
                Files.createFile(file);
            }
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void clearLog() {
        Path file = Paths.get("constraintLogs\\failedConstraints.txt");
        try {
            Files.deleteIfExists(file);
        } catch (IOException ex) {
            Logger.getLogger(ConstraintExceptionLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
