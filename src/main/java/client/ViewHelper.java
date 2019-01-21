package client;

import interfaces.viewHelperInterface;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewHelper implements viewHelperInterface {
    private static Map<String, Image> imageCache = new HashMap<>();

    /**
     * gets the image from resourc
     *
     * @param resource
     * @return Image
     */
//    public static Image getImage(String resource) {
    public static Image getImage(String resource) {
        if (imageCache.containsKey(resource)) {
            return imageCache.get(resource);
        }

        ClassLoader classLoader = ViewHelper.class.getClassLoader();

        URL url = classLoader.getResource(resource);


        /* My Code */
        System.out.println(url.toString());

        /* End */
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
