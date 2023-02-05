package com.orion.pingv2.Feature;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    private final ProcessBuilder processBuilder = new ProcessBuilder();
    private String currentDirectory = System.getProperty("user.dir");

    public int returnCode;

    public String getPrompt() {
        return "[" + currentDirectory + "]$ ";
    }

    public void setCurrentDirectory(String value) {
        currentDirectory = value;
    }

    public String command(String input){
        processBuilder.directory(new File(currentDirectory));

        if (System.getProperty("os.name").matches("(?i)windows.*"))
            processBuilder.command("cmd.exe", "/c", input);
        else
            processBuilder.command("bash", "-c", input);

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            BufferedReader errStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            returnCode = exitVal;

            if (exitVal != 0)
            {
                String l;
                while ((l = errStream.readLine()) != null) {
                    output.append(l + "\n");
                }
            }

            String st = String.valueOf(output);
            return st;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Error";
    }
}


