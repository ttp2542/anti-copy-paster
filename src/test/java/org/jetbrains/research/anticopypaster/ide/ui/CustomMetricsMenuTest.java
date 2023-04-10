package org.jetbrains.research.anticopypaster.ide.ui;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CustomMetricsMenuTest extends LightJavaCodeInsightFixtureTestCase {

    // Boolean to ensure the testdata is only added once across the multiple tests
    private boolean addedTestClass = false;

    /**
     * Gets the path for the testdata.
     * @return A string of the testdata path
     */
    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testdata";
    }

    /**
     * Overridden from LightJavaCodeInsightFixtureTestCase. Setup now also ensures
     * the project is initialized fully and adds the testdata to the project.
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        while (!getProject().isInitialized());
        if(!addedTestClass) {
            myFixture.copyDirectoryToProject("", "");
            addedTestClass = true;
        }
    }
    public void testGetKeywordsDropdownValue() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> keywordsDropdown = menu.getKeywordsDropdown();
        keywordsDropdown.setSelectedIndex(1);
        assertEquals("Low", menu.getKeywordsDropdownValue());
    }

    public void testGetSizeDropdownValue() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> sizeDropdown = menu.getSizeDropdown();
        sizeDropdown.setSelectedIndex(2);
        assertEquals("Medium", menu.getSizeDropdownValue());
    }

    public void testGetComplexityDropdownValue() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> complexityDropdown = menu.getComplexityDropdown();
        complexityDropdown.setSelectedIndex(3);
        assertEquals("High", menu.getComplexityDropdownValue());
    }

    public void testInitialState() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> keywordsDropdown = menu.getKeywordsDropdown();
        JComboBox<String> sizeDropdown = menu.getSizeDropdown();
        JComboBox<String> complexityDropdown = menu.getComplexityDropdown();

        assertEquals("Off", keywordsDropdown.getSelectedItem());
        assertEquals("Off", sizeDropdown.getSelectedItem());
        assertEquals("Off", complexityDropdown.getSelectedItem());
    }

//    public void testStateFromFile() {
//        // Create the menu and verify that it loaded the values from the file
//        CustomMetricsMenu menu = new CustomMetricsMenu();
//        JComboBox<String> keywordsDropdown = menu.getKeywordsDropdown();
//        JComboBox<String> sizeDropdown = menu.getSizeDropdown();
//        JComboBox<String> complexityDropdown = menu.getComplexityDropdown();
//
//        assertEquals("Low", keywordsDropdown.getSelectedItem());
//        assertEquals("High", sizeDropdown.getSelectedItem());
//        assertEquals("Medium", complexityDropdown.getSelectedItem());
//    }
}
