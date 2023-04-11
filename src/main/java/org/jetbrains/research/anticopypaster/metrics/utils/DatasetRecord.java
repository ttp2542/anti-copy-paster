package org.jetbrains.research.anticopypaster.metrics.utils;

import com.google.gson.JsonObject;
import org.jetbrains.research.anticopypaster.metrics.features.FeatureItem;
import org.jetbrains.research.anticopypaster.metrics.features.FeaturesVector;
import org.jetbrains.research.anticopypaster.metrics.location.LocationItem;
import org.jetbrains.research.anticopypaster.metrics.location.LocationVector;

import java.io.FileWriter;
import java.io.IOException;

public class DatasetRecord {
    private final JsonObject jsonRecord = new JsonObject();

    public DatasetRecord(FeaturesVector featuresVector, LocationVector locationVector,
                         double score, String rawCode) {
        for (FeatureItem item : featuresVector.getItems()) {
            jsonRecord.addProperty(item.getName(), item.getValue());
        }

        for (LocationItem item : locationVector.getItems()) {
            jsonRecord.addProperty(item.getName(), item.getValue());
        }

        jsonRecord.addProperty("Score", score);
        jsonRecord.addProperty("Code", rawCode);
    }

    public void writeRecord(FileWriter fw) throws IOException {
        fw.write(jsonRecord + ",\n");
    }
}
