package hotciv.tools;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.GfxConstants;
import hotciv.view.UnitFigure;
import minidraw.framework.Drawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.AbstractTool;

import java.awt.event.MouseEvent;

public class actionTool extends AbstractTool {
    private Game game;
    public actionTool(Game game, DrawingEditor editor) {
        super(editor);
        this.game = game;
    }
    @Override
    public void mouseUp(MouseEvent arg0, int arg1, int arg2) { editor.drawing().unlock(); }
    @Override
    public void mouseDown(MouseEvent event, int x, int y) {
        super.mouseDown(event, x, y);
        Drawing model = editor.drawing();
        model.lock();

        Unit clickedUnit = game.getUnitAt(new Position((super.fAnchorY-18)/30, (super.fAnchorX-13)/30));


        Figure figure = model.findFigure(x, y);
        // if figure clicked is a unit and valid and is the current player
        if (figure != null && clickedUnit != null && figure.getClass() == UnitFigure.class  && event.isShiftDown()) {
            Position p = GfxConstants.getPositionFromXY(x, y);
            game.performUnitActionAt(p);
        }
    } // end mouseDown
}
