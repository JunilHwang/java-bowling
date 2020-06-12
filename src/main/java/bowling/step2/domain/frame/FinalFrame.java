package bowling.step2.domain.frame;

import bowling.step2.domain.scores.FinalScores;
import bowling.step2.domain.scores.Scores;

public class FinalFrame implements Frame {

    private final int frame;
    private final FinalScores scores;

    private FinalFrame(int frame, FinalScores scores) {
        this.frame = frame;
        this.scores = scores;
    }

    public static Frame of (int frame, FinalScores scores) {
        return new FinalFrame(frame, scores);
    }

    public FinalScores getScores () {
        return scores;
    }

    public Frame next () {
        return of(frame + 1, FinalScores.init());
    }
}