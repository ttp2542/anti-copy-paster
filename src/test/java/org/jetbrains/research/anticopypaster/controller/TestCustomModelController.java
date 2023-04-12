package org.jetbrains.research.anticopypaster.controller;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.jetbrains.research.anticopypaster.ide.ui.CustomMetricsModel;
import org.jetbrains.research.anticopypaster.models.UserSettingsModel;
import org.jetbrains.research.anticopypaster.models.UserSettingsModelTest;
import org.jetbrains.research.anticopypaster.utils.MetricsGatherer;
import org.jetbrains.research.anticopypaster.metrics.features.FeaturesVector;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCustomModelController extends LightJavaCodeInsightFixtureTestCase {

    private UserSettingsModel model;
    private CustomMetricsModel customMetricsModel;
    private CustomModelController customModelController;

    /**
     * Overridden from LightJavaCodeInsightFixtureTestCase. Setup now also ensures
     * the project is initialized fully and adds the testdata to the project.
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        while (!getProject().isInitialized());
        model = new UserSettingsModel(null);
        customModelController = new CustomModelController();
        customMetricsModel = new CustomMetricsModel();
        customModelController.setUserSettingsModel(model);
    }

    /**
     Mock metrics gatherer needs to go here
     */
    public class MetricsGathererMock {
        @Mock
        private MetricsGatherer mockMetricsGatherer;

        List<FeaturesVector> fvArray;

        public MetricsGathererMock(List<FeaturesVector> fvArray) {
            mockMetricsGatherer = mock(MetricsGatherer.class);

            this.fvArray = fvArray;

            // mock methods for the MetricsGatherer class, should only need to mock getMethodMetrics() here
            when(mockMetricsGatherer.getMethodsMetrics())
                    .thenReturn(this.fvArray);

        }

        public MetricsGatherer getMock() {
            return mockMetricsGatherer;
        }
    }

    private float[] generateAndFillArray(int value){
        float[] array = new float[78]; // generate array for metrics
        Arrays.fill(array, value); // set every value in the array to the passed in value
        return array;
    }

    public void testParseSettingStringOff() {
        assertEquals(customModelController.parseSettingString("Off"), 0, 0);
    }
    public void testParseSettingStringLow() {
        assertEquals(customModelController.parseSettingString("Low"), 1, 0);
    }

    public void testParseSettingStringMedium() {
        assertEquals(customModelController.parseSettingString("Medium"), 2, 0);
    }

    public void testParseSettingStringHigh() {
        assertEquals(customModelController.parseSettingString("High"), 3, 0);
    }

    public void testParseSettingStringBadString() {
        assertEquals(customModelController.parseSettingString("Something"), 0, 0);
    }

    /**
     * Testing the changeSettings method to see if it correctly changes the value of the userSettingsModels sensitivities
     */
    public void testChangeSettingsFromMediumToLow(){
        List<FeaturesVector> fvList = new ArrayList<FeaturesVector>();

        float[] fvArrayValue1 = generateAndFillArray(1);
        fvArrayValue1[3] = 1;

        fvList.add(new UserSettingsModelTest.FeaturesVectorMock(fvArrayValue1).getMock());

        UserSettingsModelTest.MetricsGathererMock mockMg = new UserSettingsModelTest.MetricsGathererMock(fvList);
        this.model.initMetricsGathererAndMetricsFlags(mockMg.getMock());

        model.setComplexitySensitivity(2);
        model.setKeywordsSensitivity(2);
        model.setSizeSensitivity(2);

        //We are using this object to change the user model from medium (2) to low (1)
        customMetricsModel.setSizeDropdownValue("Low");
        customMetricsModel.setComplexityDropdownValue("Low");
        customMetricsModel.setKeywordsDropdownValue("Low");

        customModelController.changeSettings(customMetricsModel);

        assertEquals(model.getComplexitySensitivity(), 1);
        assertEquals(model.getSizeSensitivity(), 1);
        assertEquals(model.getKeywordsSensitivity(), 1);

    }

    public void testChangeSettingsFromOffToHigh(){
        List<FeaturesVector> fvList = new ArrayList<FeaturesVector>();

        float[] fvArrayValue1 = generateAndFillArray(1);
        fvArrayValue1[3] = 1;

        fvList.add(new UserSettingsModelTest.FeaturesVectorMock(fvArrayValue1).getMock());

        UserSettingsModelTest.MetricsGathererMock mockMg = new UserSettingsModelTest.MetricsGathererMock(fvList);
        this.model.initMetricsGathererAndMetricsFlags(mockMg.getMock());

        model.setComplexitySensitivity(0);
        model.setKeywordsSensitivity(0);
        model.setSizeSensitivity(0);

        //We are using this object to change the user model from off (0) to high (3)
        customMetricsModel.setSizeDropdownValue("High");
        customMetricsModel.setComplexityDropdownValue("High");
        customMetricsModel.setKeywordsDropdownValue("High");

        customModelController.changeSettings(customMetricsModel);

        assertEquals(model.getComplexitySensitivity(), 3);
        assertEquals(model.getSizeSensitivity(), 3);
        assertEquals(model.getKeywordsSensitivity(), 3);
    }


}
