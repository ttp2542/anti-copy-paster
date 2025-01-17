package org.jetbrains.research.anticopypaster.models;

import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.research.anticopypaster.controller.CustomModelController;
import org.jetbrains.research.anticopypaster.metrics.features.FeaturesVector;
import org.jetbrains.research.anticopypaster.utils.MetricsGatherer;
import org.jetbrains.research.anticopypaster.utils.KeywordsMetrics;
import org.jetbrains.research.anticopypaster.utils.SizeMetrics;
import org.jetbrains.research.anticopypaster.utils.ComplexityMetrics;
import org.jetbrains.research.anticopypaster.utils.Flag;

import java.io.*;
import java.util.List;
import java.util.Scanner;


public class UserSettingsModel extends PredictionModel{

    private static final String FILE_PATH = ProjectManager.getInstance().getOpenProjects()[0]
            .getBasePath() + "/.idea/custom_metrics.txt";

    private final int DEFAULT_SENSITIVITY = 2;
    private MetricsGatherer metricsGatherer;

    private CustomModelController customModelController = CustomModelController.getInstance();

    private Flag keywordsMetrics;
    private Flag sizeMetrics;
    private Flag complexityMetrics;
    
    private int sizeSensitivity = 0;
    private int complexitySensitivity = 0;
    private int keywordsSensitivity = 0;

    public UserSettingsModel(MetricsGatherer mg){
        //The metricsGatherer instantiation calls a function that can't be used
        //outside the context of an installed plugin, so in order to unit test
        //our model, the metrics gatherer is passed in from the constructor
        if(mg != null){
            initMetricsGathererAndMetricsFlags(mg);
        }
        customModelController.setUserSettingsModel(this);
    }

    public int getSizeSensitivity(){
        return this.sizeSensitivity;
    }

    public int getComplexitySensitivity(){
        return this.complexitySensitivity;
    }

    public int getKeywordsSensitivity(){
        return this.keywordsSensitivity;
    }

    /**
    Helper initializaton method for the metrics gatherer.
    This is a separate method so that if we ever wanted to have the metrics 
    gatherer regather metrics and update the values in the sensitivity 
    thresholds
     */
    public void initMetricsGathererAndMetricsFlags(MetricsGatherer mg){
        this.metricsGatherer = mg;

        List<FeaturesVector> methodMetrics = mg.getMethodsMetrics();
        this.keywordsMetrics = new KeywordsMetrics(methodMetrics);
        this.complexityMetrics = new ComplexityMetrics(methodMetrics);
        this.sizeMetrics = new SizeMetrics(methodMetrics);

        readSensitivitiesFromFrontend();
    }

    public void setKeywordsSensitivity(int sensitivity){
        this.keywordsSensitivity = sensitivity;
        this.keywordsMetrics.changeSensitivity(sensitivity);
    }

    public void setComplexitySensitivity(int sensitivity){
        this.complexitySensitivity = sensitivity;
        this.complexityMetrics.changeSensitivity(sensitivity);
    }

    public void setSizeSensitivity(int sensitivity){
        this.sizeSensitivity = sensitivity;
        this.sizeMetrics.changeSensitivity(sensitivity);
    }    

    /**
    Defaulted to medium if the user has not set up flag values,reads in
     the sensitivities from the frontend file if the user has set values
    */
    private void readSensitivitiesFromFrontend(){
        //Default values if the user has not yet specified flag values
        int keywordsSensFromFrontend = DEFAULT_SENSITIVITY;
        int sizeSensFromFrontend = DEFAULT_SENSITIVITY;
        int complexitySensFromFrontend = DEFAULT_SENSITIVITY;

        //Grabs the sensitivity of the flags from the file if the file exists
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                //throw away first line
                scanner.nextLine();
                String keywordsDropdownValue = scanner.nextLine();
                String sizeDropdownValue = scanner.nextLine();
                String complexityDropdownValue = scanner.nextLine();

                keywordsSensFromFrontend = customModelController.parseSettingString(keywordsDropdownValue);
                sizeSensFromFrontend = customModelController.parseSettingString(sizeDropdownValue);
                complexitySensFromFrontend = customModelController.parseSettingString(complexityDropdownValue);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }

        setKeywordsSensitivity(keywordsSensFromFrontend);
        setSizeSensitivity(sizeSensFromFrontend);
        setComplexitySensitivity(complexitySensFromFrontend);
    }

    /**
    This just gets the count of how many flags are not turned off
     */
    private int countOnFlags() {
        int count = 0;
        if (sizeSensitivity != 0) {
            count++;
        }
        if (complexitySensitivity != 0) {
            count++;
        }
        if (keywordsSensitivity != 0) {
            count++;
        }
        return count;
    }

    /**
    Returns a value higher than 0.5 if the task satisfied the requirements
    to be extracted, lower than 0.5 means the notification will not appear.
    This is currently hardcoded to return 1 until the metrics category logic
    has been implemented.
     */
    @Override
    public float predict(FeaturesVector featuresVector){

        if(sizeMetrics == null || complexityMetrics == null || keywordsMetrics == null){
            return 0;
        }

        boolean sizeTriggered = this.sizeMetrics.isFlagTriggered(featuresVector);
        boolean complexityTriggered = this.complexityMetrics.isFlagTriggered(featuresVector);
        boolean keywordsTriggered = this.keywordsMetrics.isFlagTriggered(featuresVector);


        int count = countOnFlags();
        boolean shouldNotify;

        switch (count) {
            case 0:
                shouldNotify = false;
                break;
            case 1:
                // if ANY flags are flipped, this is true
                // 1 category being set to on would be: false || false || {category}
                shouldNotify = sizeTriggered || complexityTriggered || keywordsTriggered;
                break;
            case 2:
                // if 2 flags are flipped, this is true
                // 2 categories being set to on would be: false || ({category1} && {category2})
                shouldNotify = (sizeTriggered && complexityTriggered) || (sizeTriggered && keywordsTriggered) || (complexityTriggered && keywordsTriggered);
                break;
            case 3:
                // if all 3 flags are flipped, this is true
                shouldNotify = sizeTriggered && complexityTriggered && keywordsTriggered;
                break;
            default:
                shouldNotify = false;
                break;
        }

        return shouldNotify ? 1 : 0;
    }

    /**
     * This function logs all the pertinent metrics info for
     * a copy/paste event
     * @param filepath the filepath to the log file
     */
    public void logMetrics(String filepath){
        this.complexityMetrics.logMetric(filepath);
        this.keywordsMetrics.logMetric(filepath);
        this.sizeMetrics.logMetric(filepath);
    }

    /**
     * This function logs all the metrics thresholds
     * @param filepath the filepath to the log file
     */
    public void logThresholds(String filepath){
        this.complexityMetrics.logThresholds(filepath);
        this.keywordsMetrics.logThresholds(filepath);
        this.sizeMetrics.logThresholds(filepath);
    }
}
