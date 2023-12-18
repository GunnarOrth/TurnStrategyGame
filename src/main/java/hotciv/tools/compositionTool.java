package hotciv.tools;

import hotciv.framework.Game;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.AbstractTool;
import minidraw.standard.NullTool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class compositionTool extends AbstractTool implements Tool{
    private Game game;
    private List<Tool> tools = new ArrayList<Tool>();
    public compositionTool(Game game, DrawingEditor editor) {
        super(editor);
        this.game = game;

        tools.add(new turnTool(game, editor));
        tools.add(new actionTool(game, editor));
        tools.add(new moveTool(game,editor));
        tools.add(new setFocusTool(game, editor));

    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseDown(e, x, y);
        }

    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseDrag(e, x, y);
        }

    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseUp(e, x, y);
        }
    }

    @Override
    public void mouseMove(MouseEvent e, int x, int y) {
        for(Tool tool : tools){
            tool.mouseMove(e, x, y);
        }
    }

}
