package net.davekirkwood.sudoku.client.clock;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;

public class Clock {

   private Label timerField;
   
   private int time;
   
   private Timer timer;
   
   private int level;
   
   public Clock(Label timerField) {
      this.timerField = timerField;
      
      timer = new Timer() {
         public void run() {
           time++;
           updateLabel();
         }
       };
      
   }
   
   public void start(int level) {
      this.level = level;
      time = 0;
      updateLabel();
      timerField.setVisible(true);
      timer.scheduleRepeating(1000);
   }
   
   public void reset() {
      stop();
      start(level);
   }
   
   public void switchOff() {
      timerField.setVisible(false);
      timer.cancel();
   }
   
   public int stop() {
      timer.cancel();
      return time;
   }
   
   public int getTime() {
      return time;
   }
   
   private void updateLabel() {
      timerField.setText("Time: " + getTimeString(time));
   }
   
   public String getTimeString(int t) {
      int s = t % 60;
      t -= s;
      t/=60;
      int m = t % 60;
      t -= m;
      t/=60;
      int h = t;
      
      String ss = ("0" + String.valueOf(s));
      ss = ss.substring(ss.length() - 2);
      String mm = ("0" + String.valueOf(m));
      mm = mm.substring(mm.length() - 2);
      String hh = String.valueOf(h);
      return hh + ":" + mm + ":" + ss;
   }
      
}
