package tfip.day13workshop2.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static void createDir(String path) {
        File dir = new File(path);
        boolean isCreated = dir.mkdir();
        logger.info("dir created> " + isCreated);
        if (isCreated) {
            String osname = System.getProperty("os.name");
            if (!osname.contains("Windows")) { // for non windows users to set permission to write files
                try {
                    String perm = "rwxrwx---";
                    Set<PosixFilePermission> permissions = PosixFilePermissions.fromString(perm);
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
        }
    }

}
