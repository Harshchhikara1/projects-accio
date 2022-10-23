package net.davekirkwood.sudoku.client.graphics;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.davekirkwood.sudoku.client.game.Game;

public class GameCompletedDialog extends PopupPanel {
   private VerticalPanel vp = new VerticalPanel();
   
   public static int WIDTH = 300;
   public static int HEIGHT = 300;
   
   public GameCompletedDialog(int level, String time, String firstName) {
      super(false, true);
      
      this.setPixelSize(WIDTH, HEIGHT);

      HTML t1 = new HTML("<H2>Congratulations" + (firstName != null ? "<BR>" + firstName : "") + "</H2>");
//      HTML t2 = new HTML("<IMG SRC=\"you \"></IMG>");
      HTML t3 = new HTML("You completed the <B>" + Game.getGameDesciprion(level) + "</B> level");
      HTML t4 = new HTML("in <B>" + time + "</B>!!!");
      Button bOk = new Button("Ok", new ClickHandler() { public void onClick(ClickEvent event) { hide(); } });
      
      vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      
      t1.setStyleName("congrats-dialog");
      t3.setStyleName("congrats-dialog");
      t4.setStyleName("congrats-dialog");
      
      bOk.setStyleName("congrats-dialog");
      
      vp.add(t1);
//      vp.add(t2);
      vp.add(t3);
      vp.add(t4);
      vp.add(bOk);

      setAnimationEnabled(true);
      setGlassEnabled(true);

      add(vp);
   }
}
