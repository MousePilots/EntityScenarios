package org.mousepilots.es.maven.model.generator.plugin;

import java.io.File;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * File utilities class that handles all kinds of file related tasks.
 * @author Nicky Ernste
 * @version 1.0, 8-12-2015
 */
public class FileUtils {

    /**
     * Cleans a certain directory by removing all files and sub-folders
     * recursively.
     *
     * @param directory the directory to clean.
     */
    public static void clean(File directory) {
        if (directory != null && directory.exists()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    clean(file);
                }
                file.delete();
            }
        }
    }

    /**
     * initialises the generated sources directory for the creation of the
     * generated meta model classes.
     *
     * @param generatedSourceDir The directory to initialise.
     * @param project The {@link MavenProject} project this directory belongs to.
     * @throws MojoFailureException if the generated sources directory could not
     * be created if it did not exist.
     */
    public static void initGeneratedSourcesDir(File generatedSourceDir, MavenProject project) throws MojoFailureException {
        if (generatedSourceDir.exists()) {
            clean(generatedSourceDir);
        } else {
            if (!generatedSourceDir.mkdirs()) {
                final String message = "failed to create generated sources directory " + generatedSourceDir.getAbsolutePath();
                throw new MojoFailureException(message);
            }
        }
        project.addCompileSourceRoot(generatedSourceDir.getAbsolutePath());
    }
}