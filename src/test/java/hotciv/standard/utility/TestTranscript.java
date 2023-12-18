package hotciv.standard.utility;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.Transcript;
import org.junit.Before;
import org.junit.Test;

import hotciv.framework.*;

import org.junit.*;

import java.util.ArrayList;

import static hotciv.framework.GameConstants.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestTranscript {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl();
    }

    @Test
    public void transcribe() {
        game = new Transcript(new GameImpl());
        ((Transcript) game).transcribe(true);
        game.moveUnit(new Position(2, 0), new Position(3, 0));
        game.endOfTurn();
        game.performUnitActionAt(new Position(3, 2));
        ArrayList<String> t = ((Transcript) game).getTranscript();
        assertThat(t.size(), is(3));
        assertThat(t.get(0), is("RED moves archer from [2,0] to [3,0]."));
        assertThat(t.get(1), is("RED ends turn."));
        assertThat(t.get(2), is("BLUE has legion perform action at [3,2]."));
        ((Transcript) game).transcribe(false);
        game.endOfTurn();
        assertThat(t.size(), is(3));
    }
}
