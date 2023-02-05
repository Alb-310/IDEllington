package com.orion.pingv2.Feature.Languages;

import com.orion.pingv2.UI.Model.DataStruct.FileTreeModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compilation {
    // USAGE:
    // Call compilePrep() with the Tree and the detected language from LanguagesRecognition
    // if file is null then no main function has been found and compile button must be deactivated
    // Call the ***Compile() function associated with the detected language
    private static FileTreeModel input;
    private static String file = null;
    private static boolean isRunnable = false;
    private static int lastReturnValue;

    public static FileTreeModel getInput() {
        return input;
    }

    public static void setInput(FileTreeModel input) {
        Compilation.input = input;
    }

    public static int getLastReturnValue() {
        return lastReturnValue;
    }

    public static boolean getIsRunnable() {
        return isRunnable;
    }

    private static String commandExec(String command)
    {
        FileTreeModel.MyFile root = (FileTreeModel.MyFile) input.getRoot();
        File env;
        if (root.isDirectory())
            env = root.getFile();
        else
        {
            env = root.getFile().getParentFile();
        }
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.directory(env);

        if (System.getProperty("os.name").matches("(?i)windows.*"))
            processBuilder.command("cmd.exe", "/c", command);
        else
            processBuilder.command("bash", "-c", command);

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
            if (exitVal != 0)
            {
                String l;
                while ((l = errStream.readLine()) != null) {
                    output.append(l + "\n");
                }
            }

            output.append("\nExited with return value: ").append(exitVal).append("\n");

            lastReturnValue = exitVal;
            return output.toString();
        } catch (IOException | InterruptedException ignored) {

        }

        lastReturnValue = 1;
        return "(null)";
    }

    public static String pythonCompile()
    {
        return commandExec("python " + file);
    }

    public static String JSCompile()
    {
        return commandExec("Node " + file);
    }

    public static String CCompile()
    {
        return commandExec("make");
    }

    public static String CPPCompile()
    {
        return commandExec("cmake --build .");
    }

    public static String JAVACompile()
    {
        return commandExec("mvn exec:java "); // PAS SURE A 100%
    }

    public static String run(Languages languages) {
        switch (languages) {
            case C:
                return CCompile();
            case CPP:
                return CPPCompile();
            case JS:
                return JSCompile();
            case JAVA:
                return JAVACompile();
            case PYTHON:
                return pythonCompile();
        }

        return "(null)";
    }

    public static void compilePrep(Languages language)
    {
        FileTreeModel.MyFile root = (FileTreeModel.MyFile) input.getRoot();

        if (language == Languages.C || language == Languages.CPP || language == Languages.JAVA) {
            isRunnable = root.isDirectory();
            return;
        }

        if (language == Languages.PYTHON)
        {
            if (!root.isDirectory())
            {
                file = root.getFile().getAbsolutePath();
                isRunnable = true;
                return;
            }
            else
            {
                FileTreeModel.MyFile[] files = root.listFiles();
                for (FileTreeModel.MyFile f : files)
                {
                    String name = f.toString();
                    if (name.endsWith(".py"))
                    {
                        try {
                            String content = Files.readString(f.getFile().toPath());
                            if (content.matches("(?si).*__main__.*")) {
                                file = f.getFile().getAbsolutePath();
                                isRunnable = true;
                                return;
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        else if (language == Languages.JS)
        {
            if (!root.isDirectory())
            {
                file = root.getFile().getAbsolutePath();
                isRunnable = true;
                return;
            }
            else
            {
                FileTreeModel.MyFile[] files = root.listFiles();
                for (FileTreeModel.MyFile f : files)
                {
                    String name = f.toString();
                    if (name.endsWith(".js"))
                    {
                        try {
                            String content = Files.readString(f.getFile().toPath());
                            if (content.matches("(?si).*main.*")) {
                                file = f.getFile().getAbsolutePath();
                                isRunnable = true;
                                return;
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }
        }
    }
}
