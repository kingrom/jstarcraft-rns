
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
import com.jstarcraft.rns.recommend.collaborative.ranking.FISMaucRecommender;
import com.jstarcraft.rns.task.RankingTask;

public class FISMAUCTestCase {

	@Test
	public void testRecommender() throws Exception {
		Configuration configuration = Configuration.valueOf("recommend/collaborative/ranking/fismauc-test.properties");
		RankingTask job = new RankingTask(FISMaucRecommender.class, configuration);
		Map<String, Float> measures = job.execute();
		Assert.assertThat(measures.get(AUCEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.9249077F));
		Assert.assertThat(measures.get(MAPEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.45034197F));
		Assert.assertThat(measures.get(MRREvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.5995571F));
		Assert.assertThat(measures.get(NDCGEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.5467371F));
		Assert.assertThat(measures.get(NoveltyEvaluator.class.getSimpleName()), CoreMatchers.equalTo(11.886874F));
		Assert.assertThat(measures.get(PrecisionEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.3489492F));
		Assert.assertThat(measures.get(RecallEvaluator.class.getSimpleName()), CoreMatchers.equalTo(0.6170319F));
	}

}