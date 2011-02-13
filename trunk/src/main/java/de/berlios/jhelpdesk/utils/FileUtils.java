package de.berlios.jhelpdesk.utils;

import java.io.File;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Metody wspomagające operacje na plikach.
 *
 * @author jjhop
 */
@Component
public class FileUtils {

    private final static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private File attachmentsTmpDirectory;

    public static void cleanPaths(Collection<String> paths) {
        for (String path : paths) {
            File pathToDelete = new File(path);
            log.debug("Attempt to delete directory: " + pathToDelete.getAbsolutePath());
            if (pathToDelete.exists()) {
                cleanDirectory(pathToDelete);
                boolean deletionRes = pathToDelete.delete();
                log.debug(deletionRes ? " [deleted]" : " [error]");
            } else {
                log.debug(" [not exists]");
            }
        }
    }

    public File createTmpDirForTicketstamp(String ticketstamp) {
        try {
            File f = new File(attachmentsTmpDirectory, ticketstamp);
            if (!f.exists()) {
                f.mkdirs();
            }
            return f;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Value("${tickets.attachments.tmpdir}")
    public void setAttachmentsTmpDirectory(String attachmentsTmpDir) {
        log.info("Probuję utworzy katalog: " + attachmentsTmpDir);
        try {
            attachmentsTmpDirectory = new File(attachmentsTmpDir);
            if (!attachmentsTmpDirectory.exists()) {
                attachmentsTmpDirectory.mkdirs();
            }
            log.info("Udało się!");
        } catch (Exception ex) {
            log.error("Klapa! [" + ex.getMessage() + "]");
            throw new RuntimeException(ex);
        }
    }
    
    private static boolean cleanDirectory(File directory) {
        for (File f : directory.listFiles()) {
            boolean result = f.delete();
            if (!result) {
                log.warn("Can't delete file: " + f.getName());
                return false;
            }
        }
        return true;
    }
}
