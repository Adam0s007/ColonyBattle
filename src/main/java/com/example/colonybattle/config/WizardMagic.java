package com.example.colonybattle.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WizardMagic {
    private static WizardMagic instance;
    private boolean magicEnabled = false;
    private WizardMagic() {}
    public static WizardMagic getInstance() {
        if (instance == null) {
            instance = new WizardMagic();
        }
        return instance;
    }
}
