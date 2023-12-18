package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.standard.factories.SemiCivFactory;
import hotciv.stub.StubGame2;
import hotciv.tools.compositionTool;
import hotciv.tools.turnTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class SemiCivGUI {
  
  public static void main(String[] args) {
    Game game = new GameImpl(new SemiCivFactory());

    DrawingEditor editor = 
      new MiniDrawApplication( "SemiCiv GUI",
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Play SemiCiv!.");

    editor.setTool(new compositionTool(game, editor));
  }
}
