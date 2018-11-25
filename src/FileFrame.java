import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class FileFrame {
    public static String chooseFileAndGetPath() {
        JFrame fileFrame = new JFrame();
        fileFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt")
                        || f.isDirectory();
            }

            public String getDescription() {
                return "TXT Files";
            }
        });
        String path = "";
        int r = chooser.showOpenDialog(fileFrame);
        if (r == APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                path = selectedFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileFrame.dispose();
        } else {
            fileFrame.dispose();
        }
        return path;
    }
}
