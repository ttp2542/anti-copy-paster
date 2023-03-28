package org.jetbrains.research.anticopypaster.ide.ui;

public class CustomMetricsModel {
    String keywordsDropdownValue;
    String keywordsCheckboxValue;
    String couplingDropdownValue;
    String couplingCheckboxValue;
    String sizeDropdownValue;
    String sizeCheckboxValue;
    String complexityDropdownValue;
    String complexityCheckboxValue;

    public String getKeywordsDropdownValue(){
        return this.keywordsDropdownValue;
    }

    public String getKeywordsCheckboxValue(){
        return this.keywordsCheckboxValue;
    }

    public String getComplexityDropdownValue(){
        return this.complexityDropdownValue;
    }

    public String getComplexityCheckboxValue(){
        return this.complexityCheckboxValue;
    }

    public String getSizeDropdownValue(){
        return this.sizeDropdownValue;
    }

    public String getSizeCheckboxValue(){
        return this.sizeCheckboxValue;
    }
}
