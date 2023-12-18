package hotciv.standard;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameObserverSpy implements GameObserver {
    List<List<String>> changes;
    public GameObserverSpy(){
       changes = new ArrayList<>();
    }
    @Override
    public void worldChangedAt(Position pos) {
        changes.add(Arrays.asList("worldChange", pos.toString()));
    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        changes.add(Arrays.asList("turnEnds",nextPlayer.name(), Integer.toString(age)));
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        changes.add(Arrays.asList("tileFocusChanged",position.toString()));
    }

    public List<List<String>> getChanges(){return changes;}
}
