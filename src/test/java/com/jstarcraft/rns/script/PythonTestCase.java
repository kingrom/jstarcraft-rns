package com.jstarcraft.rns.script;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jstarcraft.ai.evaluate.Evaluator;
import com.jstarcraft.ai.evaluate.ranking.AUCEvaluator;
import com.jstarcraft.ai.evaluate.ranking.MAPEvaluator;
import com.jstarcraft.ai.evaluate.ranking.MRREvaluator;
import com.jstarcraft.ai.evaluate.ranking.NDCGEvaluator;
import com.jstarcraft.ai.evaluate.ranking.NoveltyEvaluator;
import com.jstarcraft.ai.evaluate.ranking.PrecisionEvaluator;
import com.jstarcraft.ai.evaluate.ranking.RecallEvaluator;
import com.jstarcraft.ai.evaluate.rating.MAEEvaluator;
import com.jstarcraft.ai.evaluate.rating.MPEEvaluator;
import com.jstarcraft.ai.evaluate.rating.MSEEvaluator;
import com.jstarcraft.core.script.PythonExpression;
import com.jstarcraft.core.script.ScriptContext;
import com.jstarcraft.core.script.ScriptExpression;
import com.jstarcraft.core.script.ScriptScope;
import com.jstarcraft.core.utility.Configurator;
import com.jstarcraft.core.utility.StringUtility;
import com.jstarcraft.rns.model.benchmark.RandomGuessModel;
import com.jstarcraft.rns.task.RankingTask;
import com.jstarcraft.rns.task.RatingTask;

import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;

public class PythonTestCase {

    private static final ClassLoader loader = PythonTestCase.class.getClassLoader();

    private static final String script;

    static {
        try {
            File file = new File(PythonTestCase.class.getResource("Recommend.py").toURI());
            script = FileUtils.readFileToString(file, StringUtility.CHARSET);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @BeforeClass
    public static void setProperty() {
        System.setProperty("python.console.encoding", StringUtility.CHARSET.name());
    }

    @Test
    public void testRanking() throws Exception {
        ScriptContext context = new ScriptContext();
        context.useClasses(Properties.class, Configurator.class, RankingTask.class, RatingTask.class, RandomGuessModel.class);
        ScriptScope scope = new ScriptScope();
        scope.createAttribute("loader", loader);
        scope.createAttribute("type", "ranking");
        ScriptExpression expression = new PythonExpression(context, scope, script);

        Object2FloatSortedMap<Class<? extends Evaluator>> measures = expression.doWith(Object2FloatSortedMap.class);
        Assert.assertThat(measures.get(AUCEvaluator.class), CoreMatchers.equalTo(0.5205948F));
        Assert.assertThat(measures.get(MAPEvaluator.class), CoreMatchers.equalTo(0.007114561F));
        Assert.assertThat(measures.get(MRREvaluator.class), CoreMatchers.equalTo(0.023391832F));
        Assert.assertThat(measures.get(NDCGEvaluator.class), CoreMatchers.equalTo(0.012065685F));
        Assert.assertThat(measures.get(NoveltyEvaluator.class), CoreMatchers.equalTo(91.31491F));
        Assert.assertThat(measures.get(PrecisionEvaluator.class), CoreMatchers.equalTo(0.005825241F));
        Assert.assertThat(measures.get(RecallEvaluator.class), CoreMatchers.equalTo(0.011579763F));
    }

    @Test
    public void testRating() throws Exception {
        ScriptContext context = new ScriptContext();
        context.useClasses(Properties.class, Configurator.class, RankingTask.class, RatingTask.class, RandomGuessModel.class);
        ScriptScope scope = new ScriptScope();
        scope.createAttribute("loader", loader);
        scope.createAttribute("type", "rating");
        ScriptExpression expression = new PythonExpression(context, scope, script);

        Object2FloatSortedMap<Class<? extends Evaluator>> measures = expression.doWith(Object2FloatSortedMap.class);
        Assert.assertThat(measures.get(MAEEvaluator.class), CoreMatchers.equalTo(1.2708743F));
        Assert.assertThat(measures.get(MPEEvaluator.class), CoreMatchers.equalTo(0.9947887F));
        Assert.assertThat(measures.get(MSEEvaluator.class), CoreMatchers.equalTo(2.425075F));
    }

}
