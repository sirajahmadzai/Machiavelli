package interfaces;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public interface viewHelperInterface {
    static Map<String, Image> imageCache = new HashMap<>();

    public static Image getImage(String resource)
    {
        if (imageCache.containsKey(resource)) {
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

        imageCache.put(resource, thisImage);
        return thisImage;
    }

}
