package playground.Bot5;

import playground.Seed;
import playground.SimplePlayGround;

/**
 * Created by user on 11.06.2017.
 */
public interface Playable {
    int[] makeBotMove(Seed seed, String playGround, int depth, int diff);
}
