package net.davekirkwood.sudoku.client.graphics;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.davekirkwood.sudoku.client.Sudoku;
import net.davekirkwood.sudoku.client.game.Game;

public class LevelChooserDialog extends PopupPanel {

   private Sudoku sudoku;
   
   private VerticalPanel vp = new VerticalPanel();

   public static int WIDTH = 300;
   public static int HEIGHT = 300;
   
   public LevelChooserDialog(Sudoku sudoku) {
      super(false, true);
      
      this.setPixelSize(WIDTH, HEIGHT);
      this.sudoku = sudoku;

      HTML t1 = new HTML("Select difficulty level:");
      Button bEasy = new Button("Easy", new ClickHandler() { public void onClick(ClickEvent event) { select(Game.EASY); } });
      Button bIntermediate = new Button("Intermediate", new ClickHandler() { public void onClick(ClickEvent event) { select(Game.INTERMEDIATE); } });
      Button bHard = new Button("Hard", new ClickHandler() { public void onClick(ClickEvent event) { select(Game.HARD); } });
      Button bVeryHard = new Button("Very Hard", new ClickHandler() { public void onClick(ClickEvent event) { select(Game.VERY_HARD); } });

      HTML tSolver = new HTML("or");
      Button bSolver = new Button("Solve existing puzzle", new ClickHandler() { public void onClick(ClickEvent event) { select(Game.NEW_PUZZLE); } });
      
      bEasy.setStyleName("levelChooser-Button");
      bIntermediate.setStyleName("levelChooser-Button");
      bHard.setStyleName("levelChooser-Button");
      bVeryHard.setStyleName("levelChooser-Button");
      bSolver.setStyleName("levelChooser-Button");
      
      t1.setStyleName("levelChooser-Text");
      tSolver.setStyleName("levelChooser-Text");
      
      vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      
      vp.add(t1);
      vp.add(bEasy);
      vp.add(bIntermediate);
      vp.add(bHard);
      vp.add(bVeryHard);
      vp.add(tSolver);
      vp.add(bSolver);
      setAnimationEnabled(true);
      setGlassEnabled(true);

      add(vp);
   }
   
   public void select(int num) {
      sudoku.startGame(num);
      this.hide();
   }
}
