package com.orion.pingv2.Feature.Languages;

import com.orion.pingv2.UI.Model.DataStruct.FileTreeModel;


import java.util.Optional;

public class LanguagesRecognition {
    public static Optional<Languages> recognise(FileTreeModel input)
    {
        Compilation.setInput(input);
        // Get root from the Tree
        FileTreeModel.MyFile root = (FileTreeModel.MyFile) input.getRoot();

        // Check if Directory or File
        if (root.isDirectory())
        {
            FileTreeModel.MyFile[] files = root.listFiles();

            // Check the presence of pom.xml, CMakeLists or makefile for JAVA, C++ and C
            for (FileTreeModel.MyFile file : files)
            {
                if (file.toString().equals("pom.xml"))
                    return Optional.of(Languages.JAVA);
                else if (file.toString().equals("CMakeLists.txt"))
                    return Optional.of(Languages.CPP);
                else if (file.toString().equals("makefile"))
                    return Optional.of(Languages.C);
            }

            // Check for files extensions : First detected, first returned
            for (FileTreeModel.MyFile f : files)
            {
                String fileName = f.toString();
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i+1);
                }
                switch (extension)
                {
                    case "py" -> {
                        return Optional.of(Languages.PYTHON);
                    }
                    case "js" -> {
                        return Optional.of(Languages.JS);
                    }
                }
            }
            // if nothing detected return empty
            return Optional.empty();
        }
        else
        {
            // extracting extension of the file
            String fileName = root.toString();
            String extension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i+1);
            }

            // checking all supported extensions
            return switch (extension) {
                case "py" -> Optional.of(Languages.PYTHON);
                case "js" -> Optional.of(Languages.JS);
                case "c", "h" -> Optional.of(Languages.C);
                case "cc", "hh", "hxx", "cpp" -> Optional.of(Languages.CPP);
                case "java" -> Optional.of(Languages.JAVA);
                default -> Optional.empty();
            };
        }
    }
}
