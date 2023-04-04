package org.jetbrains.research.anticopypaster.ide.ui;

import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomMetricsMenuTest {

    @Test
    public void testGetKeywordsDropdownValue() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> keywordsDropdown = menu.getKeywordsDropdown();
        keywordsDropdown.setSelectedIndex(1);
        assertEquals("Low", menu.getKeywordsDropdownValue());
    }

    @Test
    public void testGetSizeDropdownValue() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> sizeDropdown = menu.getSizeDropdown();
        sizeDropdown.setSelectedIndex(2);
        assertEquals("Medium", menu.getSizeDropdownValue());
    }

    @Test
    public void testGetComplexityDropdownValue() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> complexityDropdown = menu.getComplexityDropdown();
        complexityDropdown.setSelectedIndex(3);
        assertEquals("High", menu.getComplexityDropdownValue());
    }

    @Test
    public void testInitialState() {
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> keywordsDropdown = menu.getKeywordsDropdown();
        JComboBox<String> sizeDropdown = menu.getSizeDropdown();
        JComboBox<String> complexityDropdown = menu.getComplexityDropdown();

        assertEquals("Off", keywordsDropdown.getSelectedItem());
        assertEquals("Off", sizeDropdown.getSelectedItem());
        assertEquals("Off", complexityDropdown.getSelectedItem());
    }

    @Test
    public void testStateFromFile() {
        // Set up the file with some values
        String basePath = ProjectManager.getInstance().getOpenProjects()[0].getBasePath();
        String filePath = basePath + "/.idea/custom_metrics.txt";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        String contents = "Test header\nLow\nHigh\nMedium\n";
        try {
            file.createNewFile();
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create the menu and verify that it loaded the values from the file
        CustomMetricsMenu menu = new CustomMetricsMenu();
        JComboBox<String> keywordsDropdown = menu.getKeywordsDropdown();
        JComboBox<String> sizeDropdown = menu.getSizeDropdown();
        JComboBox<String> complexityDropdown = menu.getComplexityDropdown();

        assertEquals("Low", keywordsDropdown.getSelectedItem());
        assertEquals("High", sizeDropdown.getSelectedItem());
        assertEquals("Medium", complexityDropdown.getSelectedItem());
    }
}
