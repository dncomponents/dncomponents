package com.dncomponents.client.components.tooltip;

import com.google.gwt.animation.client.Animation;
import elemental2.dom.CSSProperties;
import elemental2.dom.HTMLElement;

import java.math.BigDecimal;

public class FadeAnimation extends Animation {

    private HTMLElement element;
    private double opacityIncrement;
    private double targetOpacity;
    private double baseOpacity;

    public FadeAnimation(HTMLElement element) {
        this.element = element;
    }

    @Override
    protected void onUpdate(double progress) {
        element.style.opacity = CSSProperties.OpacityUnionType.of(baseOpacity + progress * opacityIncrement);
//        element..setOpacity(baseOpacity + progress * opacityIncrement);
    }

    private void setOpacity(double d) {
        element.style.opacity = CSSProperties.OpacityUnionType.of(d);
    }

    @Override
    protected void onComplete() {
        super.onComplete();
        setOpacity(targetOpacity);
    }

    public void fade(int duration, double targetOpacity) {
        if (targetOpacity > 1.0) {
            targetOpacity = 1.0;
        }
        if (targetOpacity < 0.0) {
            targetOpacity = 0.0;
        }
        setOpacity(0);
        this.targetOpacity = targetOpacity;
        String opacityStr = element.style.opacity.asString();
        try {
            baseOpacity = new BigDecimal(opacityStr).doubleValue();
            opacityIncrement = targetOpacity - baseOpacity;
            run(duration);
        } catch (NumberFormatException e) {
            // set opacity directly
            onComplete();
        }
    }

}