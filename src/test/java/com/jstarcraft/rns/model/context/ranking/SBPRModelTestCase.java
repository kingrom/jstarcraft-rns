package com.jstarcraft.rns.model.context.ranking;

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
import com.jstarcraft.rns.model.context.ranking.SBPRModel;
import com.jstarcraft.rns.task.RankingTask;

import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;

public class SBPRModelTestCase {

    @Test
    public void testRecommender() throws Exception {
        Properties keyValues = new Properties();
        keyValues.load(this.getClass().getResourceAsStream("/data.properties"));
        keyValues.load(this.getClass().getResourceAsStream("/recommend/context/ranking/sbpr-test.properties"));
        Configurator configuration = new Configurator(keyValues);
        RankingTask job = new RankingTask(SBPRModel.class, configuration);
        Object2FloatSortedMap<Class<? extends Evaluator>> measures = job.execute();
        Assert.assertEquals(0.9382595F, measures.getFloat(AUCEvaluator.class), 0F);
        Assert.assertEquals(0.46787822F, measures.getFloat(MAPEvaluator.class), 0F);
        Assert.assertEquals(0.63848364F, measures.getFloat(MRREvaluator.class), 0F);
        Assert.assertEquals(0.5691512F, measures.getFloat(NDCGEvaluator.class), 0F);
        Assert.assertEquals(14.824247F, measures.getFloat(NoveltyEvaluator.class), 0F);
        Assert.assertEquals(0.3539654F, measures.getFloat(PrecisionEvaluator.class), 0F);
        Assert.assertEquals(0.63641924F, measures.getFloat(RecallEvaluator.class), 0F);
    }

}
