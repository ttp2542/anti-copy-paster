package org.jetbrains.research.anticopypaster.utils;

import org.jetbrains.research.anticopypaster.metrics.features.FeaturesVector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SizeMetrics extends Flag{

    public SizeMetrics(List<FeaturesVector> featuresVectorList){
        super(featuresVectorList);
        calculateAverageSizeMetrics();
    }

    private void calculateAverageSizeMetrics(){
        ArrayList<Float> sizeMetricsValues = new ArrayList<Float>();

        for(FeaturesVector f : featuresVectorList){
            sizeMetricsValues.add(getSizeMetricFromFV(f));
        }

        Collections.sort(sizeMetricsValues);
        boxPlotCalculations(sizeMetricsValues);
    }

    /**
    This takes metric 1 from the array and gets size
    of the enclosing method
     */
    private float getSizeMetricFromFV(FeaturesVector fv){
        if(fv != null){
            float[] fvArr = fv.buildArray();
            lastCalculatedMetric = fvArr[0];
            return lastCalculatedMetric;
        }
        lastCalculatedMetric = 0;
        return lastCalculatedMetric;
    }
    
    /**
    Required override function from Flag. This just compares the size (M1/M12)
    of the passed in FeaturesVector against the correct quartile value 
    based on the box plot depending on whatever the sensitivity is.
     */
    @Override
    public boolean isFlagTriggered(FeaturesVector featuresVector){
        float fvSizeValue = getSizeMetricFromFV(featuresVector);
        switch(sensitivity) {
            case 0:
                return false;
            case 1:
                return fvSizeValue >= metricQ1; 
            case 2:
                return fvSizeValue >= metricQ2; 
            case 3:
                return fvSizeValue >= metricQ3; 
            default:
                return false;
        }
    }

    /**
     * Easier to use logMetric
     * @param filepath path to the log file
     */
    public void logMetric(String filepath){
        logMetric(filepath, "Size");
    }

    /**
     * Easier to use logThresholds
     * @param filepath path to the log file
     */
    public void logThresholds(String filepath){
        logThresholds(filepath, "Size");
    }
}