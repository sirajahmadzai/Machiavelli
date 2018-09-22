package client.views.components;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;

public class ViewEffects {
    public static final Effect selectedCardEffect;
    public static final Effect defaultEffect;

    static {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.6);
        selectedCardEffect = colorAdjust;

        defaultEffect = null;
    }
}
