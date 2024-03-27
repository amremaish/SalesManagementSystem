package com.sales.util;

public class Validator {

    public static boolean isValidFolderName(String folderName) {

        if (folderName == null || folderName.isEmpty()) {
            return false;
        }

        int minNameLength = 3;
        int maxNameLength = 50;
        if (folderName.length() < minNameLength || folderName.length() > maxNameLength) {
            return false;
        }

        // Check if folder name contains only alphanumeric characters and underscores
        if (!folderName.matches("^[a-zA-Z0-9_]*$")) {
            return false;
        }

        return true;
    }
}
