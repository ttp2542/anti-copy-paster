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

    public String getComplexityDropdownValue(){
        return this.complexityDropdownValue;
    }

    public String getSizeDropdownValue(){
        return this.sizeDropdownValue;
    }

    public void setKeywordsDropdownValue(String dropdownValue){
        this.keywordsDropdownValue = dropdownValue;
    }
    public void setSizeDropdownValue(String dropdownValue){
        this.sizeDropdownValue = dropdownValue;
    }
    public void setComplexityDropdownValue(String dropdownValue){
        this.complexityDropdownValue = dropdownValue;
    }}
