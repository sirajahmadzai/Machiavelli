package client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.models.cards.Card;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class ViewHelper {
    private static final int CARD_PREF_WIDTH = 120;
    private static final int CARD_PREF_HEIGHT = 140;

    public static ImageView createCard(Card card) {
        ImageView imv = createImageView(card.getImgUrl());
        imv.setUserData(card);
        imv.setId(String.valueOf(card.getId()));
        return imv;
    }

    /**
     * creates an ImageView
     *
     * @param imgUrl
     * @return
     */
    public static ImageView createImageView(String imgUrl) {
        ImageView imageView = new ImageView(imgUrl);
        Image type = getImage(imgUrl);
        imageView.setImage(type);
        imageView.setFitWidth(CARD_PREF_WIDTH);
        imageView.setFitHeight(CARD_PREF_HEIGHT);

        return imageView;
    }

    /**
     * @param resource
     * @return Image
     */
    public static Image getImage(String resource) {
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
        return thisImage;
    }
}
