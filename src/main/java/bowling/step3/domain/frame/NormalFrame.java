package bowling.step3.domain.frame;

import bowling.step3.domain.*;
import bowling.step3.domain.scores.*;

import java.util.Objects;
import java.util.stream.Stream;

public class NormalFrame extends Frame {
    private Frame nextFrame;

    private NormalFrame(int frame, Scores scores, Frame nextFrame) {
        super(frame, scores);
        this.nextFrame = nextFrame;
    }

    public static Frame of(int frame, Scores scores, Frame nextFrame) {
        return new NormalFrame(frame, scores, nextFrame);
    }

    public void createNextFrame(Scores scores) {
        this.scores = scores;
        if (!scores.isType(ScoreType.STRIKE) && !scores.isFullOf()) {
            return;
        }
        int nextFrameValue = frame + 1;
        this.nextFrame = nextFrameValue < Frames.LAST_FRAME
                            ? of(nextFrameValue, NormalScores.init(), null)
                            : FinalFrame.of(nextFrameValue, FinalScores.init());
    }

    public Frame getNextFrame() {
        return nextFrame;
    }

    @Override
    protected int calculateScoreOfSpared() {
        if (nextFrame == null || nextFrame.scores.isEmpty()) {
            return EMPTY_CALC;
        }
        return nextFrame.scores
                        .stream()
                        .limit(1)
                        .reduce(scores.totalScore(), (total, score) -> total + score.getValue(), Integer::sum);
    }

    @Override
    protected int calculateScoreOfStrike() {
        if (nextFrame == null || !nextFrame.scores.isFullOf()) {
            return EMPTY_CALC;
        }
        if (nextFrame.scores.isType(ScoreType.STRIKE)) {
            return calculateScoreOfTwoStrike();
        }
        return scores.totalScore() + nextFrame.scores.totalScore();
    }

    private int calculateScoreOfTwoStrike() {
        Frame nextNextFrame = nextFrame.getNextFrame();
        if (nextNextFrame == null || nextNextFrame.getScores().isEmpty()) {
            return EMPTY_CALC;
        }
        return Stream.concat(nextFrame.getScores().stream(),
                             nextNextFrame.getScores().stream())
                     .filter(Objects::nonNull)
                     .limit(2)
                     .reduce(scores.totalScore(), (total, score) -> total + score.getValue(), Integer::sum);
    }
}