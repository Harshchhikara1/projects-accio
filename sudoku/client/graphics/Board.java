package net.davekirkwood.sudoku.client.graphics;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.shape.Rectangle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

import net.davekirkwood.sudoku.client.AchievementListener;
import net.davekirkwood.sudoku.client.game.Game;
import net.davekirkwood.sudoku.client.history.History;
import net.davekirkwood.sudoku.client.history.HistoryObj;
import net.davekirkwood.sudoku.client.history.HistoryRecord;
import net.davekirkwood.sudoku.client.solver.Solver;

public class Board implements ClickHandler, MouseDownHandler, MouseUpHandler, MouseMoveHandler {
   
   public static final int BUTTON_SIZE = 72;
   public static final int MARGIN = 20;
   
   private int level;
   
   private Square[][] board = new Square[9][9];
   
   private DrawingArea canvas;
   private AchievementListener achievementListener;
   
   private List<Square> squares = new ArrayList<Square>();
   
   private List<ControlButton> controlButtons = new ArrayList<ControlButton>();
   
   private ControlButton normalButton;
   private ControlButton pencilCandidateButton;
   private ControlButton pencilCornerButton;
   private ControlButton _1Button;
   private ControlButton _2Button;
   private ControlButton _3Button;
   private ControlButton _4Button;
   private ControlButton _5Button;
   private ControlButton _6Button;
   private ControlButton _7Button;
   private ControlButton _8Button;
   private ControlButton _9Button;
   private ControlButton undoButton;
   private ControlButton clearButton;
   private ControlButton redoButton;
   
   
   
   public Board(DrawingArea canvas, AchievementListener achievementListener) {
      this.achievementListener = achievementListener;
      this.canvas = canvas;
      
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            board[x][y] = new Square(canvas, x, y, MARGIN + (x*BUTTON_SIZE), MARGIN + (y*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE);
            canvas.add(board[x][y]);
            squares.add(board[x][y]);
            board[x][y].initBackground();
         }
      }
      for(int x=0; x<3; x++) {
         for(int y=0; y<3; y++) {
            Rectangle square = new Rectangle(MARGIN + (x*(BUTTON_SIZE*3)), MARGIN + (y*(BUTTON_SIZE*3)), (BUTTON_SIZE*3), (BUTTON_SIZE*3));
            square.setFillOpacity(0);
            square.setStrokeWidth(3);
            canvas.add(square);
         }
      }
      
      normalButton = new ControlButton(canvas, MARGIN + (10*BUTTON_SIZE), MARGIN + (2*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#B9B9B9", "#000000", "#7DA9FF", "n", Square.FONT_SIZE_NORMAL, false);
      pencilCandidateButton = new ControlButton(canvas, MARGIN + (int)(11.1*BUTTON_SIZE), MARGIN + (2*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#B9B9B9", "#555555", "#7DA9FF", "n", Square.FONT_SIZE_PENCIL, false);
      pencilCornerButton = new ControlButton(canvas, MARGIN + (int)(12.2*BUTTON_SIZE), MARGIN + (2*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#B9B9B9", "#555555", "#7DA9FF", "n", Square.FONT_SIZE_PENCIL, true);
      _1Button = new ControlButton(canvas, MARGIN + (10*BUTTON_SIZE), MARGIN + (int)(3.1*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "1", Square.FONT_SIZE_NORMAL, false);
      _2Button = new ControlButton(canvas, MARGIN + (int)(11.1*BUTTON_SIZE), MARGIN + (int)(3.1*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "2", Square.FONT_SIZE_NORMAL, false);
      _3Button = new ControlButton(canvas, MARGIN + (int)(12.2*BUTTON_SIZE), MARGIN + (int)(3.1*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "3", Square.FONT_SIZE_NORMAL, false);
      _4Button = new ControlButton(canvas, MARGIN + (10*BUTTON_SIZE), MARGIN + (int)(4.2*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "4", Square.FONT_SIZE_NORMAL, false);
      _5Button = new ControlButton(canvas, MARGIN + (int)(11.1*BUTTON_SIZE), MARGIN + (int)(4.2*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "5", Square.FONT_SIZE_NORMAL, false);
      _6Button = new ControlButton(canvas, MARGIN + (int)(12.2*BUTTON_SIZE), MARGIN + (int)(4.2*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "6", Square.FONT_SIZE_NORMAL, false);
      _7Button = new ControlButton(canvas, MARGIN + (10*BUTTON_SIZE), MARGIN + (int)(5.3*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "7", Square.FONT_SIZE_NORMAL, false);
      _8Button = new ControlButton(canvas, MARGIN + (int)(11.1*BUTTON_SIZE), MARGIN + (int)(5.3*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "8", Square.FONT_SIZE_NORMAL, false);
      _9Button = new ControlButton(canvas, MARGIN + (int)(12.2*BUTTON_SIZE), MARGIN + (int)(5.3*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#7DA9FF", "#000000", "9", Square.FONT_SIZE_NORMAL, false);
      undoButton = new ControlButton(canvas, MARGIN + (10*BUTTON_SIZE), MARGIN + (int)(6.4*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#FFFFFF", "#000000", "Undo", 20, false);
      clearButton = new ControlButton(canvas, MARGIN + (int)(11.1*BUTTON_SIZE), MARGIN + (int)(6.4*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#FFFFFF", "#000000", "Clear", 20, false);
      redoButton = new ControlButton(canvas, MARGIN + (int)(12.2*BUTTON_SIZE), MARGIN + (int)(6.4*BUTTON_SIZE), BUTTON_SIZE, BUTTON_SIZE, "#FFFFFF", "#000000", "Redo", 20, false);
      
      addControlButton(normalButton);
      addControlButton(pencilCandidateButton);
      addControlButton(pencilCornerButton);
      addControlButton(_1Button);
      addControlButton(_2Button);
      addControlButton(_3Button);
      addControlButton(_4Button);
      addControlButton(_5Button);
      addControlButton(_6Button);
      addControlButton(_7Button);
      addControlButton(_8Button);
      addControlButton(_9Button);
      addControlButton(undoButton);
      addControlButton(clearButton);
      addControlButton(redoButton);

      canvas.addClickHandler(this);
      canvas.addMouseDownHandler(this);
      canvas.addMouseUpHandler(this);
      canvas.addMouseMoveHandler(this);
      

      normalButton.setHighlighted(true);
      
   }
   
   private void addControlButton(ControlButton button) {
      canvas.add(button);
      controlButtons.add(button);
      button.initBackground();
   	
   }
   
   /**
    * Returns true if game is completed
    * @param x
    * @param y
    * @param value
    * @return
    */
   public void setValue(int x, int y, int value) {
      setValue(x,y,value, false);
   }
   
   public void setValue(int x, int y, int value, boolean lock) {
      board[x][y].setValue(value);
      board[x][y].setLocked(lock);
   }
   
   public void setHighlighted(int x, int y, boolean highlighted) {
      board[x][y].setHighlighted(highlighted);
   }
   
   public int getValue(int x, int y) {
      return board[x][y].getValue();
   }
   
   public boolean isLocked(int x, int y) {
      return board[x][y].isLocked();
   }
//   
   public void clear() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            board[x][y].clear();
            board[x][y].setLocked(false);
            board[x][y].setConflicted(false);
            board[x][y].setHighlighted(false);
         }
      }
   }
   
   private void checkForConflicts() {
      
      clearConflicts();
      
      // Check columns
      for(int x=0; x<9; x++) {
         for(int y1=0; y1<9; y1+=1) {
            for(int y2=y1+1; y2<9; y2+=1) {
               if(board[x][y1].getValue() > 0 && board[x][y1].getValue() == board[x][y2].getValue()) {
                  board[x][y1].setConflicted(true);
                  board[x][y2].setConflicted(true);
               }
            }
         }
      }
      
      // Check rows
      for(int y=0; y<9; y++) {
         for(int x1=0; x1<9; x1+=1) {
            for(int x2=x1+1; x2<9; x2+=1) {
               if(board[x1][y].getValue() > 0 && board[x1][y].getValue() == board[x2][y].getValue()) {
                  board[x1][y].setConflicted(true);
                  board[x2][y].setConflicted(true);
               }
            }
         }
      }
      
      // Check squares
      for(int startX=0; startX<9; startX+=3) {
         for(int startY=0; startY<9; startY+=3) {
            
            ArrayList<Integer> nums = new ArrayList<Integer>();
            ArrayList<Integer> conflicts = new ArrayList<Integer>();
            
            for(int x=0; x<3; x++) {
               for(int y=0; y<3; y++) {
                  int value = board[startX+x][startY+y].getValue();
                  if(nums.contains( Integer.valueOf( value ))) {
                     conflicts.add( Integer.valueOf( value ) );
                  }
                  nums.add( Integer.valueOf( value ) );
               }
            }
            for(int x=0; x<3; x++) {
               for(int y=0; y<3; y++) {
                  int value = board[startX+x][startY+y].getValue();
                  if(value > 0) {
                     if(conflicts.contains( Integer.valueOf( value ))) {
                        board[startX+x][startY+y].setConflicted(true);
                     }
                  }
               }
            }
         }
      }
   }
   
   private boolean isConflicted() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            if(board[x][y].isConflicted()) {
               return true;
            }
         }
      }
      return false;
   }
   
   private boolean isCompleted() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            if(board[x][y].getValue() == 0) {
               return false;
            }
         }
      }
      return true;
   }
   
   public void clearSelection() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            board[x][y].setHighlighted(false);
         }
      }
   }
   
   private void clearConflicts() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            board[x][y].setConflicted(false);
         }
      }
   }
   
   public boolean isValid() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            if(board[x][y].isConflicted()) {
               return false;
            }
         }
      }
      return true;
   }

   public int getLevel() {
      return level;
   }

   public void setLevel(int level) {
      this.level = level;
      normalButton.setHighlighted(true);
      pencilCandidateButton.setHighlighted(false);
      pencilCornerButton.setHighlighted(false);
      History.clearHistory();
   	for(ControlButton controlButton : controlButtons) {
   		controlButton.setVisible(true);
   	}
      if(level == Game.NEW_PUZZLE) {
      	pencilCandidateButton.setVisible(false);
      	pencilCornerButton.setVisible(false);
      	undoButton.setVisible(false);
      	redoButton.setVisible(false);
//      } else {
//      	pencilCandidateButton.setVisible(true);
//      	pencilCornerButton.setVisible(true);
//      	undoButton.setVisible(true);
//      	redoButton.setVisible(true);
      }
      	
   }
   
   public void endGame() {
   	for(ControlButton controlButton : controlButtons) {
   		controlButton.setVisible(false);
   	}
   }


   public void restartThisGame() {
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            if(!board[x][y].isLocked()) {
               board[x][y].clear();
            }
         }
      }
      clearConflicts();
      setLevel(level);
   }
   
   public boolean solve() {
      int[][] puzzle = new int[9][9];
      for(int x=0; x<9; x++) {
         for(int y=0; y<9; y++) {
            puzzle[x][y] = getValue(x, y);
         }
      }
      checkForConflicts();
      if(isValid()) {
         Solver solver = new Solver();
         Solver.ASSUMPTIONS = 0;
         if(solver.solve(puzzle)) {
            for(int x=0; x<9; x++) {
               for(int y=0; y<9; y++) {
                  setValue(x, y, puzzle[x][y], isLocked(x,  y));
               }
            }
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void onClick(ClickEvent event) {
//      clearSelection();
      SButton buttonClick = getButtonForCoords(event.getX(), event.getY());
		ControlButton controlButton = (ControlButton)buttonClick;
		if(controlButton.isVisible()) {
			if(controlButton == normalButton) {
				normalButton.setHighlighted(true);
				pencilCandidateButton.setHighlighted(false);
				pencilCornerButton.setHighlighted(false);
			} else if(controlButton == pencilCandidateButton) {
				normalButton.setHighlighted(false);
				pencilCandidateButton.setHighlighted(true);
				pencilCornerButton.setHighlighted(false);
			} else if(controlButton == pencilCornerButton) {
				normalButton.setHighlighted(false);
				pencilCandidateButton.setHighlighted(false);
				pencilCornerButton.setHighlighted(true);
			} else if(controlButton == _1Button) {
				setValueInGrid(1);
			} else if(controlButton == _2Button) {
				setValueInGrid(2);
			} else if(controlButton == _3Button) {
				setValueInGrid(3);
			} else if(controlButton == _4Button) {
				setValueInGrid(4);
			} else if(controlButton == _5Button) {
				setValueInGrid(5);
			} else if(controlButton == _6Button) {
				setValueInGrid(6);
			} else if(controlButton == _7Button) {
				setValueInGrid(7);
			} else if(controlButton == _8Button) {
				setValueInGrid(8);
			} else if(controlButton == _9Button) {
				setValueInGrid(9);
			} else if(controlButton == undoButton) {
				HistoryRecord undo = History.getUndo();
				List<HistoryObj> redo = new ArrayList<HistoryObj>();
				for(HistoryObj u : undo.getHistoryObjects()) {
					redo.add(new HistoryObj(u.getSquare()));
					u.getSquare().applyHistory(u);
				}
				History.addRedo(new HistoryRecord(redo));
			} else if(controlButton == clearButton) {
				setValueInGrid(0);
			} else if(controlButton == redoButton) {
				HistoryRecord redo = History.getRedo();
				List<HistoryObj> undo = new ArrayList<HistoryObj>();
				for(HistoryObj r : redo.getHistoryObjects()) {
					undo.add(new HistoryObj(r.getSquare()));
					r.getSquare().applyHistory(r);
				}
				History.addUndo(new HistoryRecord(undo));
			}
		}
   }
   
   private void setValueInGrid(int value) {
   	
   	List<HistoryObj> historyObjects = new ArrayList<HistoryObj>();
   	
   	if(normalButton.isHighlighted()) {
   		for(Square square : squares) {
   			if(square.isHighlighted()) {
   				historyObjects.add(new HistoryObj(square));
   				square.setValue(value);
   				if(level == Game.NEW_PUZZLE) {
   					square.setHighlighted(false);
   					square.setLocked(value != 0);
   				}
   			}
   		}
   	} else if(pencilCandidateButton.isHighlighted()) {
   		for(Square square : squares) {
   			if(square.isHighlighted() && square.getValue() == 0) {
   				historyObjects.add(new HistoryObj(square));
   				square.toggleCandidate(value);
   			}
   		}
   	} else if(pencilCornerButton.isHighlighted()) {
   		for(Square square : squares) {
   			if(square.isHighlighted() && square.getValue() == 0) {
   				historyObjects.add(new HistoryObj(square));
   				square.toggleCorner(value);
   			}
   		}
   	}
   	
   	if(historyObjects.size() > 0) {
	   	History.addUndo(new HistoryRecord(historyObjects));
	   	History.clearRedo();
   	}
   	
      if(isCompleted()) {
         checkForConflicts();
         if(!isConflicted()) {
         	achievementListener.gameCompleted();
         }
      } else {
         clearConflicts();
      }

   }
   
	@Override
	public void onMouseDown(MouseDownEvent event) {
		mouseDown = true;
		event.preventDefault();
		SButton button = getButtonForCoords(event.getX(), event.getY());
		if(button != null && !(button instanceof ControlButton)) {
			if(!event.isAltKeyDown() && !event.isControlKeyDown() && !event.isShiftKeyDown()) {
				clearSelection();
			}
		}
		if(button != null) {
			if(button instanceof Square) {
				Square square = (Square)button;
				if(level == Game.NEW_PUZZLE || !square.isLocked()) {
					square.setHighlighted(true);
				}
			}
		}
//		Window.alert("MD");
	}
	
	private boolean mouseDown = false;
	
	private SButton getButtonForCoords(int x, int y) {
      for(SButton button : squares) {
      	if(x > button.getX() && x < button.getX() + button.getWidth()) {
      		if(y > button.getY() && y < button.getY() + button.getHeight()) {
      			return button;
      		}
      	}
      }
      for(ControlButton button : controlButtons) {
      	if(x > button.getX() && x < button.getX() + button.getWidth()) {
      		if(y > button.getY() && y < button.getY() + button.getHeight()) {
      			return button;
      		}
      	}
      }
      return null;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
//		clearSelection();
		if(mouseDown) {
			SButton button = getButtonForCoords(event.getX(), event.getY());
			if(button != null) {
				if(button instanceof Square) {
					Square square = (Square)button;
					if(level == Game.NEW_PUZZLE || !square.isLocked()) {
						square.setHighlighted(true);
					}
				}
			}
		}
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		mouseDown = false;
//		Window.alert("MU");
	}
   
	
}
