package com.jstarcraft.rns.evaluate;

import java.util.ArrayList;
import java.util.List;

import com.jstarcraft.ai.data.DataInstance;
import com.jstarcraft.ai.data.module.ArrayInstance;
import com.jstarcraft.ai.utility.Integer2FloatKeyValue;
import com.jstarcraft.rns.recommend.Recommender;

import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatList;

public abstract class AbstractRatingEvaluatorTestCase extends AbstractEvaluatorTestCase<FloatCollection> {

    @Override
    protected FloatCollection check(int userIndex) {
        DataInstance instance = testMarker.getInstance(0);
        int from = testPaginations[userIndex], to = testPaginations[userIndex + 1];
        FloatList scoreList = new FloatArrayList(to - from);
        for (int index = from, size = to; index < size; index++) {
            int position = testPositions[index];
            instance.setCursor(position);
            scoreList.add(instance.getQuantityMark());
        }
        return scoreList;
    }

    @Override
    protected List<Integer2FloatKeyValue> recommend(Recommender recommender, int userIndex) {
        DataInstance instance = testMarker.getInstance(0);
        int from = testPaginations[userIndex], to = testPaginations[userIndex + 1];
        ArrayInstance copy = new ArrayInstance(testMarker.getQualityOrder(), testMarker.getQuantityOrder());
        List<Integer2FloatKeyValue> recommendList = new ArrayList<>(to - from);
        for (int index = from, size = to; index < size; index++) {
            int position = testPositions[index];
            instance.setCursor(position);
            copy.copyInstance(instance);
            recommendList.add(new Integer2FloatKeyValue(copy.getQualityFeature(itemDimension), recommender.predict(copy)));
        }
        return recommendList;
    }

}