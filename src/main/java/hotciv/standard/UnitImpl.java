package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    Player player;
    String unitType;
    int moveCount;
    int maxMoveCount;
    int defense;
    int attack;

    public UnitImpl(String unitType, Player player) {
        this.unitType = unitType;
        this.player = player;
        maxMoveCount = GameConstants.unitInfo.get(unitType)[3];
        resetMoveCount();
        defense = GameConstants.unitInfo.get(unitType)[1];
        attack = GameConstants.unitInfo.get(unitType)[2];
    }

    public String getTypeString() {
        return unitType;
    }

    public Player getOwner() {
        return player;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getDefensiveStrength() {
        return defense;
    }

    public int getAttackingStrength() {
        return attack;
    }

    public void resetMoveCount(){
        moveCount = maxMoveCount;
    }

    public void setMaxMoves(int maxMoves){
        this.maxMoveCount = maxMoves;
        resetMoveCount();
    }
    public void setDefense(int defense){
        this.defense = defense;
    }
    public void makeMoves(int moves){
        moveCount -= moves;
    }
}
