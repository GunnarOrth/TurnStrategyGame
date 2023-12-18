package hotciv.tools;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.standard.AbstractTool;

import java.awt.event.MouseEvent;

public class turnTool extends AbstractTool {
    private Game game;

    public turnTool(Game game, DrawingEditor editor) {
        super(editor);
        this.game = game;
    }

    public void mouseUp(MouseEvent event, int x, int y) {
        editor.drawing().unlock();
    }
    public void mouseDown(MouseEvent event, int x, int y) {
        Drawing model = editor.drawing();
        model.lock();

        int turnShieldY = GfxConstants.TURN_SHIELD_Y;
        int turnShieldX = GfxConstants.TURN_SHIELD_X;

        // 39 and 27 are pixel offsets within the bounds of the turn shield prop
        if(y >= turnShieldY && y <= turnShieldY + 39 && x >= turnShieldX && x <= turnShieldX + 27)
            game.endOfTurn(); // call end of turn if mouse click is within bounds of turn shield prop
    }
}
