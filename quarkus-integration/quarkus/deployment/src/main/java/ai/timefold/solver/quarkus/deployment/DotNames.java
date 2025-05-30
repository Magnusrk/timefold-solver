package ai.timefold.solver.quarkus.deployment;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import jakarta.inject.Named;

import ai.timefold.solver.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import ai.timefold.solver.core.api.domain.constraintweight.ConstraintWeight;
import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.entity.PlanningPin;
import ai.timefold.solver.core.api.domain.entity.PlanningPinToIndex;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.solution.ConstraintWeightOverrides;
import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningEntityProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.ProblemFactProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.domain.variable.AnchorShadowVariable;
import ai.timefold.solver.core.api.domain.variable.CascadingUpdateShadowVariable;
import ai.timefold.solver.core.api.domain.variable.CustomShadowVariable;
import ai.timefold.solver.core.api.domain.variable.IndexShadowVariable;
import ai.timefold.solver.core.api.domain.variable.InverseRelationShadowVariable;
import ai.timefold.solver.core.api.domain.variable.NextElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PiggybackShadowVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningListVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import ai.timefold.solver.core.api.domain.variable.PlanningVariableReference;
import ai.timefold.solver.core.api.domain.variable.PreviousElementShadowVariable;
import ai.timefold.solver.core.api.domain.variable.ShadowVariable;
import ai.timefold.solver.core.api.score.calculator.EasyScoreCalculator;
import ai.timefold.solver.core.api.score.calculator.IncrementalScoreCalculator;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.api.solver.SolverManager;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.SolverManagerConfig;
import ai.timefold.solver.core.preview.api.domain.variable.declarative.ShadowSources;

import org.jboss.jandex.DotName;

public final class DotNames {
    // Jakarta classes
    static final DotName NAMED = DotName.createSimple(Named.class);

    // Timefold classes
    static final DotName PLANNING_SOLUTION = DotName.createSimple(PlanningSolution.class.getName());
    static final DotName PLANNING_ENTITY_COLLECTION_PROPERTY =
            DotName.createSimple(PlanningEntityCollectionProperty.class.getName());
    static final DotName PLANNING_ENTITY_PROPERTY = DotName.createSimple(PlanningEntityProperty.class.getName());
    static final DotName PLANNING_SCORE = DotName.createSimple(PlanningScore.class.getName());
    static final DotName PROBLEM_FACT_COLLECTION_PROPERTY = DotName.createSimple(ProblemFactCollectionProperty.class.getName());
    static final DotName PROBLEM_FACT_PROPERTY = DotName.createSimple(ProblemFactProperty.class.getName());

    static final DotName EASY_SCORE_CALCULATOR = DotName.createSimple(EasyScoreCalculator.class.getName());
    static final DotName CONSTRAINT_PROVIDER = DotName.createSimple(ConstraintProvider.class.getName());
    static final DotName INCREMENTAL_SCORE_CALCULATOR =
            DotName.createSimple(IncrementalScoreCalculator.class.getName());
    static final DotName CONSTRAINT_CONFIGURATION_PROVIDER =
            DotName.createSimple(ConstraintConfigurationProvider.class.getName());
    static final DotName CONSTRAINT_WEIGHT = DotName.createSimple(ConstraintWeight.class.getName());
    static final DotName CONSTRAINT_WEIGHT_OVERRIDES = DotName.createSimple(ConstraintWeightOverrides.class.getName());

    static final DotName PLANNING_ENTITY = DotName.createSimple(PlanningEntity.class.getName());
    static final DotName PLANNING_PIN = DotName.createSimple(PlanningPin.class.getName());
    static final DotName PLANNING_PIN_TO_INDEX = DotName.createSimple(PlanningPinToIndex.class.getName());
    static final DotName PLANNING_ID = DotName.createSimple(PlanningId.class.getName());

    static final DotName PLANNING_VARIABLE = DotName.createSimple(PlanningVariable.class.getName());
    static final DotName PLANNING_LIST_VARIABLE = DotName.createSimple(PlanningListVariable.class.getName());
    static final DotName PLANNING_VARIABLE_REFERENCE = DotName.createSimple(PlanningVariableReference.class.getName());
    static final DotName VALUE_RANGE_PROVIDER = DotName.createSimple(ValueRangeProvider.class.getName());

    static final DotName ANCHOR_SHADOW_VARIABLE = DotName.createSimple(AnchorShadowVariable.class.getName());
    static final DotName CUSTOM_SHADOW_VARIABLE = DotName.createSimple(CustomShadowVariable.class.getName());
    static final DotName INDEX_SHADOW_VARIABLE = DotName.createSimple(IndexShadowVariable.class.getName());
    static final DotName INVERSE_RELATION_SHADOW_VARIABLE = DotName.createSimple(InverseRelationShadowVariable.class.getName());
    static final DotName NEXT_ELEMENT_SHADOW_VARIABLE = DotName.createSimple(NextElementShadowVariable.class.getName());
    static final DotName PIGGYBACK_SHADOW_VARIABLE = DotName.createSimple(PiggybackShadowVariable.class.getName());
    static final DotName PREVIOUS_ELEMENT_SHADOW_VARIABLE = DotName.createSimple(PreviousElementShadowVariable.class.getName());
    static final DotName SHADOW_VARIABLE = DotName.createSimple(ShadowVariable.class.getName());
    static final DotName CASCADING_UPDATE_SHADOW_VARIABLE =
            DotName.createSimple(CascadingUpdateShadowVariable.class.getName());
    static final DotName SHADOW_SOURCES = DotName.createSimple(ShadowSources.class.getName());

    static final DotName SOLVER_CONFIG = DotName.createSimple(SolverConfig.class.getName());
    static final DotName SOLVER_MANAGER_CONFIG = DotName.createSimple(SolverManagerConfig.class.getName());
    static final DotName SOLVER_FACTORY = DotName.createSimple(SolverFactory.class.getName());
    static final DotName SOLVER_MANAGER = DotName.createSimple(SolverManager.class.getName());

    // Need to use String since timefold-solver-test is not on the compile classpath
    static final DotName CONSTRAINT_VERIFIER =
            DotName.createSimple("ai.timefold.solver.test.api.score.stream.ConstraintVerifier");

    static final DotName[] PLANNING_ENTITY_FIELD_ANNOTATIONS = {
            PLANNING_PIN,
            PLANNING_PIN_TO_INDEX,
            PLANNING_VARIABLE,
            PLANNING_LIST_VARIABLE,
            ANCHOR_SHADOW_VARIABLE,
            CUSTOM_SHADOW_VARIABLE,
            INDEX_SHADOW_VARIABLE,
            INVERSE_RELATION_SHADOW_VARIABLE,
            NEXT_ELEMENT_SHADOW_VARIABLE,
            PIGGYBACK_SHADOW_VARIABLE,
            PREVIOUS_ELEMENT_SHADOW_VARIABLE,
            SHADOW_VARIABLE,
            CASCADING_UPDATE_SHADOW_VARIABLE
    };

    static final DotName[] GIZMO_MEMBER_ACCESSOR_ANNOTATIONS = {
            PLANNING_ENTITY_COLLECTION_PROPERTY,
            PLANNING_ENTITY_PROPERTY,
            PLANNING_SCORE,
            PROBLEM_FACT_COLLECTION_PROPERTY,
            PROBLEM_FACT_PROPERTY,
            CONSTRAINT_CONFIGURATION_PROVIDER,
            CONSTRAINT_WEIGHT,
            PLANNING_PIN,
            PLANNING_PIN_TO_INDEX,
            PLANNING_ID,
            PLANNING_VARIABLE,
            PLANNING_LIST_VARIABLE,
            PLANNING_VARIABLE_REFERENCE,
            VALUE_RANGE_PROVIDER,
            ANCHOR_SHADOW_VARIABLE,
            CUSTOM_SHADOW_VARIABLE,
            INDEX_SHADOW_VARIABLE,
            INVERSE_RELATION_SHADOW_VARIABLE,
            NEXT_ELEMENT_SHADOW_VARIABLE,
            PIGGYBACK_SHADOW_VARIABLE,
            PREVIOUS_ELEMENT_SHADOW_VARIABLE,
            SHADOW_VARIABLE,
            CASCADING_UPDATE_SHADOW_VARIABLE
    };

    static final Set<DotName> SOLVER_INJECTABLE_TYPES = Set.of(
            SOLVER_CONFIG,
            SOLVER_MANAGER_CONFIG,
            SOLVER_FACTORY,
            SOLVER_MANAGER);

    public enum BeanDefiningAnnotations {
        PLANNING_SCORE(DotNames.PLANNING_SCORE, "scoreDefinitionClass"),
        PLANNING_SOLUTION(DotNames.PLANNING_SOLUTION, "solutionCloner"),
        PLANNING_ENTITY(DotNames.PLANNING_ENTITY, "pinningFilter", "difficultyComparatorClass",
                "difficultyWeightFactoryClass"),
        PLANNING_VARIABLE(DotNames.PLANNING_VARIABLE, "strengthComparatorClass",
                "strengthWeightFactoryClass"),
        CUSTOM_SHADOW_VARIABLE(DotNames.CUSTOM_SHADOW_VARIABLE, "variableListenerClass"),
        SHADOW_VARIABLE(DotNames.SHADOW_VARIABLE, "variableListenerClass");

        private final DotName annotationDotName;
        private final List<String> parameterNames;

        BeanDefiningAnnotations(DotName annotationDotName, String... parameterNames) {
            this.annotationDotName = annotationDotName;
            this.parameterNames = Arrays.asList(parameterNames);
        }

        public DotName getAnnotationDotName() {
            return annotationDotName;
        }

        public List<String> getParameterNames() {
            return parameterNames;
        }
    }

    private DotNames() {
    }

}
