package hotciv.tools;

import hotciv.framework.*;
import hotciv.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class setFocusTool extends AbstractTool{

    private Game game;

    public setFocusTool(Game game, DrawingEditor editor) {
        super(editor);
        this.game = game;
    }

    public void mouseUp(MouseEvent e, int x, int y) {
        editor.drawing().unlock();
    }
    public void mouseDown(MouseEvent e, int x, int y) {
        Drawing model = editor.drawing();
        model.lock();

        Position p = GfxConstants.getPositionFromXY(x,y);
        Unit unit = game.getUnitAt(p);
        City city = game.getCityAt(p);
        if(unit != null || city != null){
            game.setTileFocus(p);
        }
    }
}
