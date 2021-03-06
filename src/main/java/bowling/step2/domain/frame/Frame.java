package bowling.step2.domain.frame;

import bowling.step2.domain.scores.Scores;

public abstract class Frame {

    protected final int frame;
    protected final Scores scores;
    protected final Frame prevFrame;

    protected Frame(int frame, Scores scores, Frame prevFrame) {
        this.frame = frame;
        this.scores = scores;
        this.prevFrame = prevFrame;
    }

    public Scores getScores() {
        return scores;
    }

    abstract public Frame createNextFrame(Scores scores);

    public Frame getPrevFrame() {
        return prevFrame;
    }

    public int getValue() {
        return frame;
    }
}