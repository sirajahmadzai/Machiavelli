package client;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewHelper {
    /**
     * @param resource
     * @return Image
     */
    private static Map<String, Image> imageCache = new HashMap<>();

    public static Image getImage(String resource) {
        if (imageCache.containsKey(resource)) {
            System.out.println("Image from cache.");
            return imageCache.get(resource);
        }

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL url = classLoader.getResource(resource);
        File file = null;
        if (url != null) {
            file = new File(url.getFile());
        }
        Image thisImage = null;
        try {
            if (file != null) {
                thisImage = new Image(new FileInputStream(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Image from file system.");
        imageCache.put(resource, thisImage);
        return thisImage;
    }
}
