package com.orion.pingv2.Utils;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Matcher execRegex(String pattern, String str) {
        Pattern r = Pattern.compile(pattern);
        return r.matcher(str);
    }

    public static void createDir(String location) {
        try {
            Path path = Paths.get(location);
            Files.createDirectories(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLinesArray(String filePath) {
        Path path = Path.of(filePath);

        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static String getLastLine(String str) {
        String[] lines = str.split("\n");
        return lines[lines.length - 1];
    }

    public static String getFileContent(String filePath) {
        List<String> lines = Utils.getLinesArray(filePath);
        return String.join("\n", lines);
    }

    public static void downloadFile(String link, String fileName) {
        try {
            URL url = new URL(link);
            HttpsURLConnection conn;

            while ((conn = (HttpsURLConnection) url.openConnection()).getContentLength() <= 0)
            {}

            InputStream ins = conn.getInputStream();
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] outputByte = new byte[4096];
            int length = conn.getContentLength();
            int read = 0;

            while (read < length) {
                int bytesRead = ins.read(outputByte, 0, 4096);
                read += bytesRead;
                fout.write(outputByte, 0, bytesRead);
            }
            fout.flush();
            fout.close();

            System.out.println(read + ", " + length);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static BufferedImage iconToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics g = bufferedImage.createGraphics();

        icon.paintIcon(null, g, 0,0);
        g.dispose();

        return bufferedImage;
    }
}

