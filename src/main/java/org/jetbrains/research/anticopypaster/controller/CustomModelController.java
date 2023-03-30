package org.jetbrains.research.anticopypaster.controller;

import org.jetbrains.research.anticopypaster.ide.ui.CustomMetricsModel;
import org.jetbrains.research.anticopypaster.models.UserSettingsModel;

/**
 * Singleton instance since only one controller needs to exist.
 */
public final class CustomModelController {
    private static final CustomModelController INSTANCE = new CustomModelController();
    private UserSettingsModel userSettingsModel;

    private CustomModelController(){

    }

    public static CustomModelController getInstance(){
        return INSTANCE;
    }

    public void setUserSettingsModel(UserSettingsModel userSettingsModel){
        this.userSettingsModel = userSettingsModel;
    }

    public void changeSettings(CustomMetricsModel customMetricsModel){
        if(userSettingsModel != null) {
            userSettingsModel.setComplexitySensitivity(parseSettingString(
                    customMetricsModel.getComplexityDropdownValue())
            );
            userSettingsModel.setKeywordsSensitivity(parseSettingString(
                    customMetricsModel.getKeywordsDropdownValue())
            );
            userSettingsModel.setSizeSensitivity(parseSettingString(
                    customMetricsModel.getSizeDropdownValue())
            );
        }
    }

    /**
     * Parses the settings string and converts to integer
     * @param setting The string off/low/medium/high
     * @return integer representation that CustomMetricsModel uses (0 - 3)
     */
    public int parseSettingString(String setting){
        setting = setting.toLowerCase();
        switch(setting){
            case("high"):
                return 3;
            case("medium"):
                return 2;
            case("low"):
                return 1;
            // default case handles both "off" and improper formatting
            default:
                return 0;
        }
    }
}
