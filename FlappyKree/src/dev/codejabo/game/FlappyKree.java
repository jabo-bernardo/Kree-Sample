package dev.codejabo.game;

import dev.codejabo.game.scenes.GameScene;
import dev.jabo.kree.Game;
import dev.jabo.kree.SceneManager;
import dev.jabo.kree.Window;

public class FlappyKree {

	private Window window;
	private Game game;
	
	// Scenes
	// TODO: Menu Scene, Game over Scene
	private GameScene gameScene;
	
	public FlappyKree() {
		
		Initialize();
		
	}
	
	public void Initialize() {
		
		// Initialization
		window = new Window("Flappy Kree", 800, 600);
		game = new Game(window);
		
		gameScene = new GameScene(game);
		
		// Set Scene
		SceneManager.setScene(gameScene);
		
		game.start();
		
	}
	
}
