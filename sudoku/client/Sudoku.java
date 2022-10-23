package net.davekirkwood.sudoku.client;


import org.vaadin.gwtgraphics.client.DrawingArea;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

import net.davekirkwood.sudoku.client.clock.Clock;
import net.davekirkwood.sudoku.client.game.Game;
import net.davekirkwood.sudoku.client.graphics.Board;
import net.davekirkwood.sudoku.client.graphics.GameCompletedDialog;
import net.davekirkwood.sudoku.client.graphics.LevelChooserDialog;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sudoku implements EntryPoint, AchievementListener {

	private Board board;
	private DrawingArea canvas;
	private Label statusField;
	private MenuItem playGameItem;

	private Clock timer;
	private LevelChooserDialog lcDialog;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		HTMLPanel html = new HTMLPanel("");
		DecoratorPanel panel = new DecoratorPanel();
		panel.add(html);
		RootPanel.get().add(panel);

		/*
		 * Menu bar
		 */
		final MenuBar menuBar = new MenuBar();

		MenuBar gameMenu = new MenuBar(true);
		gameMenu.addItem("New Game", new Command() {
			public void execute() {
				newGame();
			}
		});
		playGameItem = new MenuItem("Restart this game", new Command() {
			public void execute() {
				board.restartThisGame();
				playGameItem.setText("Restart this game");
				if (board.getLevel() == Game.NEW_PUZZLE) {
					board.setLevel(Game.CUSTOM);
				}
				statusField.setText("Level: " + Game.getGameDesciprion(board.getLevel()));
				if (board.getLevel() == Game.CUSTOM) {
					timer.switchOff();
				} else {
					timer.reset();
				}
			}
		});
		gameMenu.addItem(playGameItem);

		MenuBar solveMenu = new MenuBar(true);
		solveMenu.addItem("Solve current puzzle", new Command() {
			public void execute() {
				board.restartThisGame();
				if (board.solve()) {
					board.endGame();
					statusField.setText("Puzzle solved.");
					playGameItem.setEnabled(false);
				} else {
					statusField.setText("Puzzle has no solution");
				}
				timer.switchOff();
			}
		});
		
		menuBar.addItem("Game", gameMenu);
		menuBar.addItem("Solver", solveMenu);
		menuBar.setStyleName("sudoku-menubar");
		
		gameMenu.setStyleName("sudoku-menubarMenu");
		solveMenu.setStyleName("sudoku-menubarMenu");
		
		html.add(menuBar, "menubarContainer");

		/*
		 * Main Canvas
		 */
		canvas = new DrawingArea(Board.BUTTON_SIZE * 14 + (Board.MARGIN * 2), Board.BUTTON_SIZE * 9 + (Board.MARGIN * 2));
		html.add(new FocusPanel(canvas), "canvasContainer");
		board = new Board(canvas, this);

		/*
		 * Status Panel - status field and timer field
		 */
		DockPanel statusPanel = new DockPanel();
		statusPanel.setStyleName("statuspanel");
		statusField = new Label();
		statusField.setStyleName("statusfield");
		statusPanel.add(statusField, DockPanel.WEST);

		final Label timerField = new Label("00:00:00");
		timer = new Clock(timerField);
		timerField.setStyleName("timerfield");
		statusPanel.add(timerField, DockPanel.EAST);
		html.add(statusPanel, "statusFieldContainer");
		
		/*
		 * Initialise level chooser and start new game.
		 */
		lcDialog = new LevelChooserDialog(this);
		
		newGame();

	}

	public void newGame() {
		lcDialog.setPopupPosition((canvas.getWidth() - LevelChooserDialog.WIDTH) /  2, (canvas.getHeight()  - LevelChooserDialog.HEIGHT) /  2);
		lcDialog.show();
	}

	public void startGame(int level) {

		boolean cheat = false;
		if (level == Game.NEW_PUZZLE) {
			new Game(board, Game.NEW_PUZZLE, cheat);
			statusField.setText("Enter puzzle then select solve current puzzle from menu.");
			playGameItem.setText("Play this game");
			playGameItem.setEnabled(true);
			timer.switchOff();
		} else {
			new Game(board, level, cheat);
			statusField.setText("Level: " + Game.getGameDesciprion(level));
			playGameItem.setText("Restart this game");
			playGameItem.setEnabled(true);
			timer.start(level);
		}
	}

	public void gameCompleted() {
		board.endGame();
		int time = timer.stop();
		int level = board.getLevel();
		String timeString = timer.getTimeString(time);

		GameCompletedDialog gcd = new GameCompletedDialog(level, timeString, null);
		gcd.setPopupPosition( (canvas.getWidth() - GameCompletedDialog.WIDTH) / 2,
				                (canvas.getHeight() - GameCompletedDialog.HEIGHT) / 2);
		gcd.show();
		gcd.addCloseHandler(new CloseHandler<PopupPanel>() {

			@SuppressWarnings("rawtypes")
			public void onClose(CloseEvent event) {
				statusField.setText("Congratulations, you completed the game in " + timer.getTimeString(timer.getTime()) + " !");
			}

		});

	}

}
