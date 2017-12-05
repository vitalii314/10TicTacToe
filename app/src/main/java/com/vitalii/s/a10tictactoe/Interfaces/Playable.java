package com.vitalii.s.a10tictactoe.Interfaces;

import com.vitalii.s.a10tictactoe.Models.playground.Seed;

/**
 * Created by user on 11.06.2017.
 */
public interface Playable {
    int[] makeBotMove(Seed seed, String playGround, int depth, int diff);
}
