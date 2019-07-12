package com.jstarcraft.rns.recommend.collaborative.ranking;

import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.rns.configure.Configuration;
import com.jstarcraft.rns.evaluate.ranking.AUCEvaluator;
import com.jstarcraft.rns.evaluate.ranking.MAPEvaluator;
import com.jstarcraft.rns.evaluate.ranking.MRREvaluator;
import com.jstarcraft.rns.evaluate.ranking.NDCGEvaluator;
import com.jstarcraft.rns.evaluate.ranking.NoveltyEvaluator;
import com.jstarcraft.rns.evaluate.ranking.PrecisionEvaluator;
import com.jstarcraft.rns.evaluate.ranking.RecallEvaluator;
import com.jstarcraft.rns.recommend.collaborative.ranking.AspectModelRankingRecommender;
import com.jstarcraft.rns.task.RankingTask;

public class AspectModelRankingTestCase {

	@Test
	public void testRecommender() throws Exception {
		Configuration configuration = Configuration.valueOf("recommend/collaborative/ranking/aspectmodelranking-test.properties");
		RankingTask job = new RankingTask(AspectModelRankingRecommender.class, configuration);
		Map<String, Float> measures = job.execute();
		Assert.assertThat(measures.get(AUCEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.9289594F));
		Assert.assertThat(measures.get(MAPEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.45355427F));
		Assert.assertThat(measures.get(MRREvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.6159721F));
		Assert.assertThat(measures.get(NDCGEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.55231506F));
		Assert.assertThat(measures.get(NoveltyEvaluator.class.getSimpleName()), CoreMatchers.equalTo(11.742993F));
		Assert.assertThat(measures.get(PrecisionEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.3510527F));
		Assert.assertThat(measures.get(RecallEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.6224215F));
	}

}