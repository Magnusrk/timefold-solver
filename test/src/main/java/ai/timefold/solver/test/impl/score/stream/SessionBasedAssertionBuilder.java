package ai.timefold.solver.test.impl.score.stream;

import java.util.Objects;

import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraintStreamScoreDirectorFactory;

final class SessionBasedAssertionBuilder<Solution_, Score_ extends Score<Score_>> {

    private final AbstractConstraintStreamScoreDirectorFactory<Solution_, Score_, ?> constraintStreamScoreDirectorFactory;

    public SessionBasedAssertionBuilder(
            AbstractConstraintStreamScoreDirectorFactory<Solution_, Score_, ?> constraintStreamScoreDirectorFactory) {
        this.constraintStreamScoreDirectorFactory = Objects.requireNonNull(constraintStreamScoreDirectorFactory);
    }

    public DefaultMultiConstraintAssertion<Solution_, Score_> multiConstraintGiven(ConstraintProvider constraintProvider,
            Object... facts) {
        var scoreInliner = constraintStreamScoreDirectorFactory.fireAndForget(facts);
        return new DefaultMultiConstraintAssertion<>(constraintProvider, constraintStreamScoreDirectorFactory,
                scoreInliner.extractScore(), scoreInliner.getConstraintIdToConstraintMatchTotalMap(),
                scoreInliner.getIndictmentMap());
    }

    public DefaultSingleConstraintAssertion<Solution_, Score_> singleConstraintGiven(Object... facts) {
        var scoreInliner = constraintStreamScoreDirectorFactory.fireAndForget(facts);
        return new DefaultSingleConstraintAssertion<>(constraintStreamScoreDirectorFactory,
                scoreInliner.extractScore(), scoreInliner.getConstraintIdToConstraintMatchTotalMap(),
                scoreInliner.getIndictmentMap());
    }

}
