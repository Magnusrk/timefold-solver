package ai.timefold.solver.core.testdomain.inheritance.solution.baseannotated.thirdparty;

import java.util.List;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.simple.SimpleScore;
import ai.timefold.solver.core.impl.domain.solution.descriptor.SolutionDescriptor;
import ai.timefold.solver.core.testdomain.TestdataValue;

@PlanningSolution
public class TestdataExtendedThirdPartySolution extends TestdataThirdPartySolutionPojo {

    public static SolutionDescriptor<TestdataExtendedThirdPartySolution> buildSolutionDescriptor() {
        return SolutionDescriptor.buildSolutionDescriptor(TestdataExtendedThirdPartySolution.class,
                TestdataExtendedThirdPartyEntity.class);
    }

    private Object extraObject;

    private SimpleScore score;

    public TestdataExtendedThirdPartySolution() {
    }

    public TestdataExtendedThirdPartySolution(String code) {
        super(code);
    }

    public TestdataExtendedThirdPartySolution(String code, Object extraObject) {
        super(code);
        this.extraObject = extraObject;
    }

    public Object getExtraObject() {
        return extraObject;
    }

    public void setExtraObject(Object extraObject) {
        this.extraObject = extraObject;
    }

    @PlanningScore
    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    @Override
    @ValueRangeProvider(id = "valueRange")
    @ProblemFactCollectionProperty
    public List<TestdataValue> getValueList() {
        return super.getValueList();
    }

    @Override
    @PlanningEntityCollectionProperty
    public List<TestdataThirdPartyEntityPojo> getEntityList() {
        return super.getEntityList();
    }

}
