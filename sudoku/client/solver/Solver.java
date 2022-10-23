package net.davekirkwood.sudoku.client.solver;

import java.util.Vector;

/**
 * Solves the puzzle 2d int array - if cannot solve will recursively call with just any
 * added number until solution is found
 */
public class Solver {
   
   public static int ASSUMPTIONS = 0;
   
   public class Assumption {
      public int row;
      public int col;
      public int value;
   };
   
   private Vector assumptions;
   
   private int done_x, done_y;

   public Solver() {
   }
   
   public boolean solve(int[][] nums) {
      
      boolean doneSomething=false;
      
      for(int row=0;row<9;row++) {
         for(int col=0;col<9;col++) {
            
            if(!doneSomething && nums[row][col]==0) {
               
               Vector cannotBe=new Vector();
               
               for(int i=0;i<9;i++) {
                  if(i!=col) {
                     Integer sq=new Integer(nums[row][i]);
                     if(sq.intValue() != 0 && !cannotBe.contains(sq)) {
                        cannotBe.addElement(sq);
                     }
                  }
               }
               
               for(int i=0;i<9;i++) {
                  if(i!=row) {
                     Integer sq=new Integer(nums[i][col]);
                     if(sq.intValue()!=0 && !cannotBe.contains(sq)) {
                        cannotBe.addElement(sq);
                     }
                  }
               }
               
               int square_x = (int)Math.floor(row/3);
               int square_y = (int)Math.floor(col/3);
               for(int x=square_x*3;x<(square_x*3)+3;x++) {
                  for(int y=square_y*3;y<(square_y*3)+3;y++) {
                     if(x!=row && y!=col) {
                        Integer sq = new Integer(nums[x][y]);
                        if(sq.intValue()!=0 && !cannotBe.contains(sq)) {
                           cannotBe.addElement(sq);
                        } 
                     }
                  }
               }
               
               if(cannotBe.size() == 8) {
                  for(int canBe=1;canBe<=9;canBe++) {
                     if(!cannotBe.contains(new Integer(canBe))) {
                        nums[row][col] = canBe;
                        done_x=row;
                        done_y=col;
                        doneSomething=true;
                        canBe=10;   //break
                     }
                  }
               } else {
                  
                  if(assumptions == null) {
                     assumptions = new Vector();
                     for(int i=1; i<=9; i++) {
                        if(!cannotBe.contains(new Integer(i))) {
                           Assumption a = new Assumption();
                           a.row = row;
                           a.col = col;
                           a.value = new Integer(i).intValue();
                           assumptions.addElement(a);
                        }
                     }
                  }
                 
               }
               
            }
         }
      }
      
      if(doneSomething) {
      
         boolean isDone=true;
         for(int row=0;row<9;row++) {
            for(int col=0;col<9;col++) {
               if(nums[row][col]==0) {
                  isDone=false;
               }
            }
         }
         
         if(!isDone) {
            Solver recurse = new Solver();
            if(recurse.solve(nums)) {
               return true;
            } else {
               nums[done_x][done_y] = 0;
               return false;
            }
         } else {
            return true;
         }
      } else {
         
         // Will have to make assumption about first empty square
         
         while(!assumptions.isEmpty()) {
            Assumption a = (Assumption)assumptions.elementAt(0);
            assumptions.removeElementAt(0);
            nums[a.row][a.col] = a.value;
            ASSUMPTIONS++;
            Solver r = new Solver();
            if(r.solve(nums)) {
               return true;
            } else {
               nums[a.row][a.col] = 0;
            }
         }
         
         return false;
      }
      
   }
}