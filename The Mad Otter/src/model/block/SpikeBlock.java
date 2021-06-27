package model.block;

import javafx.scene.layout.Pane;

public class SpikeBlock extends Block{
    public SpikeBlock(double x, double y,Pane layer) {
        super(x, y, "graphics/blocks/spikeBlock.png", layer);
        setBreakable(false);
        setPrickly(true);
        setToPass(true);
        setDmg(2);
    }
}
