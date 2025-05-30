package ai.timefold.solver.core.impl.exhaustivesearch.node;

import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.api.score.director.ScoreDirector;
import ai.timefold.solver.core.impl.exhaustivesearch.node.bounder.ScoreBounder;
import ai.timefold.solver.core.impl.score.director.InnerScore;
import ai.timefold.solver.core.preview.api.move.Move;

public class ExhaustiveSearchNode {

    private final ExhaustiveSearchLayer layer;
    private final ExhaustiveSearchNode parent;
    private final long breadth;

    // The move to get from the parent to this node
    private Move move;
    private Move undoMove;
    private InnerScore<?> score;
    /**
     * Never worse than the best possible score a leaf node below this node might lead to.
     *
     * @see ScoreBounder#calculateOptimisticBound(ScoreDirector, InnerScore)
     */
    private InnerScore<?> optimisticBound;
    private boolean expandable = false;

    public ExhaustiveSearchNode(ExhaustiveSearchLayer layer, ExhaustiveSearchNode parent) {
        this.layer = layer;
        this.parent = parent;
        this.breadth = layer.assignBreadth();
    }

    public ExhaustiveSearchLayer getLayer() {
        return layer;
    }

    public ExhaustiveSearchNode getParent() {
        return parent;
    }

    public long getBreadth() {
        return breadth;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Move getUndoMove() {
        return undoMove;
    }

    public void setUndoMove(Move undoMove) {
        this.undoMove = undoMove;
    }

    @SuppressWarnings("unchecked")
    public <Score_ extends Score<Score_>> InnerScore<Score_> getScore() {
        return (InnerScore<Score_>) score;
    }

    public <Score_ extends Score<Score_>> void setInitializedScore(Score_ score) {
        setScore(InnerScore.fullyAssigned(score));
    }

    public void setScore(InnerScore<?> score) {
        this.score = score;
    }

    @SuppressWarnings("unchecked")
    public <Score_ extends Score<Score_>> InnerScore<Score_> getOptimisticBound() {
        return (InnerScore<Score_>) optimisticBound;
    }

    public void setOptimisticBound(InnerScore<?> optimisticBound) {
        this.optimisticBound = optimisticBound;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    // ************************************************************************
    // Calculated methods
    // ************************************************************************

    public int getDepth() {
        return layer.getDepth();
    }

    public String getTreeId() {
        return layer.getDepth() + "-" + breadth;
    }

    public Object getEntity() {
        return layer.getEntity();
    }

    public boolean isLastLayer() {
        return layer.isLastLayer();
    }

    public long getParentBreadth() {
        return parent == null ? -1 : parent.getBreadth();
    }

    @Override
    public String toString() {
        return getTreeId() + " (" + layer.getEntity() + ")";
    }

}
