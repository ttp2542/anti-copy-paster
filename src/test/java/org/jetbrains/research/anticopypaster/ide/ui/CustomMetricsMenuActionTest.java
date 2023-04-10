//package org.jetbrains.research.anticopypaster.ide.ui;
//
//import com.intellij.openapi.actionSystem.AnActionEvent;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.project.ProjectManager;
//import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
//import org.junit.Test;
//import org.powermock.api.mockito.PowerMockito;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class CustomMetricsMenuActionTest extends LightPlatformCodeInsightFixtureTestCase {
//
//    @Test
//    public void testActionPerformed() throws Exception {
//        // Mock the CustomMetricsMenu object
//        CustomMetricsMenu dialog = mock(CustomMetricsMenu.class);
//        when(dialog.showAndGet()).thenReturn(true);
//        when(dialog.getKeywordsDropdownValue()).thenReturn("value1");
//        when(dialog.getKeywordsCheckboxValue()).thenReturn(true);
//        when(dialog.getCouplingDropdownValue()).thenReturn("value2");
//        when(dialog.getCouplingCheckboxValue()).thenReturn(false);
//        when(dialog.getSizeDropdownValue()).thenReturn("value3");
//        when(dialog.getSizeCheckboxValue()).thenReturn(true);
//        when(dialog.getComplexityDropdownValue()).thenReturn("value4");
//        when(dialog.getComplexityCheckboxValue()).thenReturn(false);
//
//        // Mock the Project and FileWriter objects
//        Project project = mock(Project.class);
//        ProjectManager projectManager = mock(ProjectManager.class);
//        when(projectManager.getOpenProjects()).thenReturn(new Project[]{project});
//        PowerMockito.mockStatic(ProjectManager.class);
//        when(ProjectManager.getInstance()).thenReturn(projectManager);
//        FileWriter fileWriter = mock(FileWriter.class);
//        PowerMockito.whenNew(FileWriter.class).withArguments(anyString()).thenReturn(fileWriter);
//
//        // Call the actionPerformed method with the mocked dialog
//        CustomMetricsMenuAction action = new CustomMetricsMenuAction();
//        AnActionEvent event = mock(AnActionEvent.class);
//        action.actionPerformed(event);
//
//        // Verify that the file was written correctly
//        String expectedContent = "Custom Metrics Values:\n" +
//                "value1\ntrue\nvalue2\nfalse\nvalue3\ntrue\nvalue4\nfalse\n";
//        verify(fileWriter).write(expectedContent);
//    }
//
//}
