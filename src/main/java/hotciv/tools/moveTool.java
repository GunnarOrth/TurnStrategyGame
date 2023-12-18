package hotciv.tools;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
import javax.swing.*;

import hotciv.framework.*;
import hotciv.view.*;
import hotciv.stub.*;
import minidraw.standard.handlers.DragTracker;
import minidraw.standard.handlers.SelectAreaTracker;

public class moveTool extends AbstractTool implements Tool{

    private Game game;
    protected Tool fChild;
    protected Tool cachedNullTool;
    protected Figure draggedFigure;
    private Position from;
    private Position to;
    Drawing model;

    public moveTool(Game game, DrawingEditor editor) {
        super(editor);
        this.game = game;

        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
    }
    public void mouseDown(MouseEvent e, int x, int y)
    {
        model = editor().drawing();

        model.lock();


        from = GfxConstants.getPositionFromXY(x,y);
        //check if there's a unit there
        if(game.getUnitAt(from) != null) {
            draggedFigure = model.findFigure(e.getX(), e.getY());
            fChild = createDragTracker(draggedFigure);
            fChild.mouseDown(e, x, y);
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        fChild.mouseDrag(e, x, y);
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        fChild.mouseMove(e, x, y);
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        to = GfxConstants.getPositionFromXY(x,y);
        boolean success = game.moveUnit(from, to);
        if(success){
            fChild.mouseUp(e,x, y);
        }
        fChild = cachedNullTool;
        model.unlock();

    }

    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor(), f);
    }
}
