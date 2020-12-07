package com.example.onjava8.chapter21;

import static com.example.onjava8.chapter21.Outcome.*;

// enums/RoShamBo2.java
// Switching one enum on another
// {java enums.RoShamBo2}
public enum RoShamBo2 implements Competitor<RoShamBo2> {
    PAPER(DRAW, LOSE, WIN),
    SCISSORS(WIN, DRAW, LOSE),
    ROCK(LOSE, WIN, DRAW);
    private Outcome vPAPER;
    private Outcome vSCISSORS;
    private Outcome vROCK;
    RoShamBo2(Outcome paper,Outcome scissors, Outcome rock) {
        this.vPAPER = paper;
        this.vSCISSORS = scissors;
        this.vROCK = rock;
    }
    @Override
    public Outcome compete(RoShamBo2 it) {
        switch(it) {
            default:
            case PAPER:
                return this.vPAPER;
            case SCISSORS:
                return this.vSCISSORS;
            case ROCK:
                return this.vROCK;
        }
    }
    public static void main(String[] args) {
        RoShamBo.play(RoShamBo2.class, 20);
    }
}

