package model.block;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class TempSpikeBlock extends Block{

    private final Image hided = new Image("graphics/blocks/spikeBlockHide.png");
    private final Image notHided = new Image("graphics/blocks/spikeBlock.png");

    public TempSpikeBlock(double x, double y, Pane layer) {
        super(x, y, "graphics/blocks/spikeBlockHide.png", layer);
        setBreakable(false);
        setPrickly(false);
        setToPass(true);
        setDmg(2);
        spikeAnimation();
    }

    private void spikeAnimation() {
        AnimationTimer animationTimer = new AnimationTimer(){
            long lastUpdate = 0;
            @Override
            public void handle(long l) {
                if ((l - lastUpdate - 1_000_000_000) >= 2_000_000_000) { // <--- zmiana co 3s
                    if (isPrickly()) {
                        setPrickly(false);
                        getImageView().setImage(hided);
                    } else {
                        setPrickly(true);
                        getImageView().setImage(notHided);
                    }
                    lastUpdate = l;
                }
            }
        };
        animationTimer.start();
    }

}
