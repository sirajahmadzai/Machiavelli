package client.views.components;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;

public class ViewEffects {
    /**
     * PUBLIC STATIC FINALS
     */
    public static final Effect selectedCardEffect;
    public static final Effect defaultEffect;
    public static final Effect invalidEffect;
    public static final Effect newCardInSetEffect;

    /**
     *
     */
    static {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.6);
        selectedCardEffect = colorAdjust;

        ColorAdjust colorAdjust2 = new ColorAdjust();
        colorAdjust2.setHue(-0.1);
        colorAdjust2.setSaturation(0.2);
        invalidEffect = colorAdjust2;

        ColorAdjust colorAdjust3 = new ColorAdjust();
        colorAdjust3.setHue(0.1);
        colorAdjust3.setSaturation(0.2);
        newCardInSetEffect = colorAdjust3;

        defaultEffect = null;
    }
}
