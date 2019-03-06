package client;

import interfaces.viewHelperInterface;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewHelper implements viewHelperInterface {
    private static Map<String, Image> imageCache = new HashMap<>();
    private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    /**
     * Creates an image object using the resource path provided.
     * This helper also catches the images to minimize the file IO.
     *
     * @param resource the relative path to resource.
     * @return Image
     */
    public static Image getImage(String resource) {
        if (imageCache.containsKey(resource)) {
            return imageCache.get(resource);
        }

        URL url = classLoader.getResource(resource);
        Image image = null;
        if (url != null) {
            image = new Image(url.toString());
        }

        imageCache.put(resource, image);
        return image;
    }
}
