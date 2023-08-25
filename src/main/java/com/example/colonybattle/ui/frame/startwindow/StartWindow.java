package com.example.colonybattle.ui.frame.startwindow;

import javax.swing.*;

public class StartWindow extends JFrame {

    private final FrameSetup frameSetup;
    private final ComponentFactory componentFactory;
    private final SpinnerController spinnerController;
    private final GameStarter gameStarter;

    public StartWindow() {
        this.frameSetup = new FrameSetup(this);
        this.componentFactory = new ComponentFactory(this);
        this.spinnerController = new SpinnerController(this, componentFactory);
        this.gameStarter = new GameStarter(spinnerController);

        frameSetup.setupFrame();
        componentFactory.initializeComponents(gameStarter);
    }
}