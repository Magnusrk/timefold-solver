package ai.timefold.solver.core.impl.bavet.uni;

import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ai.timefold.solver.core.impl.bavet.common.AbstractFlattenLastNode;
import ai.timefold.solver.core.impl.bavet.common.Propagator;
import ai.timefold.solver.core.impl.bavet.common.tuple.TupleLifecycle;
import ai.timefold.solver.core.impl.bavet.common.tuple.UniTuple;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FlattenLastUniNodeTest {

    @Mock
    private TupleLifecycle<UniTuple<String>> downstream;

    private static String merge(String... facts) {
        /*
         * flattenLast() may return the same item twice, or return items that equals.
         * If that happens, each must be treated as its own individual occurrence.
         * String interning will turn two equal strings into the same instance, ensuring that the test covers this case.
         */
        return Arrays.stream(facts)
                .map(String::intern)
                .collect(Collectors.joining(","));
    }

    private static List<String> split(String factString) {
        return Arrays.stream(factString.split(","))
                .map(String::intern)
                .collect(Collectors.toList());
    }

    private static UniTuple<String> createTuple(String... facts) {
        return new UniTuple<>(merge(facts), 1);
    }

    private static UniTuple<String> modifyTuple(UniTuple<String> tuple, String... facts) {
        ((UniTuple<String>) tuple).factA = merge(facts);
        return tuple;
    }

    @Test
    void insertAndRetract() {
        AbstractFlattenLastNode<UniTuple<String>, UniTuple<String>, String, String> node =
                new FlattenLastUniNode<>(0, FlattenLastUniNodeTest::split, downstream, 1);

        // First tuple is inserted, A and B make it downstream.
        UniTuple<String> firstTuple = createTuple("A", "B");
        node.insert(firstTuple);
        verifyNoInteractions(downstream);

        Propagator propagator = node.getPropagator();
        propagator.propagateEverything();
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "A")));
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "B")));
        verifyNoMoreInteractions(downstream);
        reset(downstream);

        // Second tuple is inserted, B and C make it downstream even though B already did before.
        UniTuple<String> secondTuple = createTuple("B", "C");
        node.insert(secondTuple);
        verifyNoInteractions(downstream);

        propagator.propagateEverything();
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "B")));
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "C")));
        verifyNoMoreInteractions(downstream);
        reset(downstream);

        // First tuple is retracted, A and B are retracted from downstream.
        node.retract(firstTuple);
        verifyNoInteractions(downstream);

        propagator.propagateEverything();
        verify(downstream).retract(argThat(t -> Objects.equals(t.factA, "A")));
        verify(downstream).retract(argThat(t -> Objects.equals(t.factA, "B")));
        verifyNoMoreInteractions(downstream);
        reset(downstream);

        // Second tuple is retracted, the second B is retracted, C is retracted.
        node.retract(secondTuple);
        verifyNoInteractions(downstream);

        propagator.propagateEverything();
        verify(downstream).retract(argThat(t -> Objects.equals(t.factA, "B")));
        verify(downstream).retract(argThat(t -> Objects.equals(t.factA, "C")));
        verifyNoMoreInteractions(downstream);
        reset(downstream);

        // Nothing happens on an empty node.
        propagator.propagateEverything();
        verifyNoInteractions(downstream);
    }

    @Test
    void modify() {
        AbstractFlattenLastNode<UniTuple<String>, UniTuple<String>, String, String> node =
                new FlattenLastUniNode<>(0, FlattenLastUniNodeTest::split, downstream, 1);

        // First tuple is inserted.
        UniTuple<String> firstTuple = createTuple("A", "B");
        node.insert(firstTuple);

        // Second tuple is inserted.
        UniTuple<String> secondTuple = createTuple("B", "C");
        node.insert(secondTuple);

        // Clear the propagation queue.
        Propagator propagator = node.getPropagator();
        propagator.propagateEverything();
        reset(downstream);

        // The tuple is updated, removing A, adding X and another B.
        firstTuple = modifyTuple(firstTuple, "B", "X", "B");
        node.update(firstTuple);
        verifyNoInteractions(downstream);

        propagator.propagateEverything();
        verify(downstream).retract(argThat(t -> Objects.equals(t.factA, "A")));
        verify(downstream).update(argThat(t -> Objects.equals(t.factA, "B")));
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "X")));
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "B")));
        verifyNoMoreInteractions(downstream);
        reset(downstream);

        // Remove the B from the second tuple, adding X.
        secondTuple = modifyTuple(secondTuple, "X", "C");
        node.update(secondTuple);
        verifyNoInteractions(downstream);

        propagator.propagateEverything();
        verify(downstream).retract(argThat(t -> Objects.equals(t.factA, "B")));
        verify(downstream).update(argThat(t -> Objects.equals(t.factA, "C")));
        verify(downstream).insert(argThat(t -> Objects.equals(t.factA, "X")));
        verifyNoMoreInteractions(downstream);
        reset(downstream);

        // Remove all Bs from the first tuple.
        firstTuple = modifyTuple(firstTuple, "X");
        node.update(firstTuple);
        verifyNoInteractions(downstream);

        propagator.propagateEverything();
        verify(downstream, times(2)).retract(argThat(t -> Objects.equals(t.factA, "B")));
        verify(downstream).update(argThat(t -> Objects.equals(t.factA, "X")));
        verifyNoMoreInteractions(downstream);
    }

}
