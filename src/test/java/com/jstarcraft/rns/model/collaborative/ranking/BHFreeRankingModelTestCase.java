package com.jstarcraft.rns.model.collaborative.ranking;

import java.util.Map;
import java.util.Properties;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.ai.evaluate.Evaluator;
import com.jstarcraft.ai.evaluate.ranking.AUCEvaluator;
import com.jstarcraft.ai.evaluate.ranking.MAPEvaluator;
import com.jstarcraft.ai.evaluate.ranking.MRREvaluator;
import com.jstarcraft.ai.evaluate.ranking.NDCGEvaluator;
import com.jstarcraft.ai.evaluate.ranking.NoveltyEvaluator;
import com.jstarcraft.ai.evaluate.ranking.PrecisionEvaluator;
import com.jstarcraft.ai.evaluate.ranking.RecallEvaluator;
import com.jstarcraft.core.utility.Configurator;
import com.jstarcraft.rns.model.collaborative.ranking.BHFreeRankingModel;
import com.jstarcraft.rns.task.RankingTask;

import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;

public class BHFreeRankingModelTestCase {

    @Test
    public void testRecommenderRanking() throws Exception {
        Properties keyValues = new Properties();
        keyValues.load(this.getClass().getResourceAsStream("/data.properties"));
        keyValues.load(this.getClass().getResourceAsStream("/recommend/collaborative/bhfreeranking-test.properties"));
        Configurator configuration = new Configurator(keyValues);
        RankingTask job = new RankingTask(BHFreeRankingModel.class, configuration);
        Object2FloatSortedMap<Class<? extends Evaluator>> measures = job.execute();
        Assert.assertEquals(0.9350284F, measures.getFloat(AUCEvaluator.class), 0F);
        Assert.assertEquals(0.4588323F, measures.getFloat(MAPEvaluator.class), 0F);
        Assert.assertEquals(0.62514687F, measures.getFloat(MRREvaluator.class), 0F);
        Assert.assertEquals(0.56000006F, measures.getFloat(NDCGEvaluator.class), 0F);
        Assert.assertEquals(11.643554F, measures.getFloat(NoveltyEvaluator.class), 0F);
        Assert.assertEquals(0.35153824F, measures.getFloat(PrecisionEvaluator.class), 0F);
        Assert.assertEquals(0.63331467F, measures.getFloat(RecallEvaluator.class), 0F);
    }

}
