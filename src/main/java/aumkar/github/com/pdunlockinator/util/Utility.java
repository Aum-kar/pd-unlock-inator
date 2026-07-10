package aumkar.github.com.pdunlockinator.util;

public class Utility {
    public static String generateFilename(String name) {
        int dot = name.lastIndexOf('.');
        return (dot == -1)
                ? name + "_unlocked"
                : name.substring(0, dot)
                + "_unlocked"
                + name.substring(dot);
    }
}
