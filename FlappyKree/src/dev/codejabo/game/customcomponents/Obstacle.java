package dev.codejabo.game.customcomponents;

import java.awt.Graphics;

import dev.codejabo.game.scenes.GameScene;
import dev.jabo.kree.Component;
import dev.jabo.kree.Vector2;

//This is a custom component learn how to make one at [https://jabo-bernardo.github.io/kree-documentation/#creating-player-movement-component]
public class Obstacle extends Component {
	
	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	
	private Vector2 position;
	private Vector2 scale;
	
	GameScene instance;

	public Obstacle() {
		
		this.name = "Obstacle";
		
	}
	
	@Override
	public void Render(Graphics arg0) {
		
	}

	@Override
	public void Update() {
		if(gameObject != null) {
			position = gameObject.transform.position;
			scale = gameObject.transform.scale;
			
			instance = (GameScene) gameObject.getParentScene();
		}
		if(position.x < 0 - scale.x) {
			position.x = 800; // Left side of screen
		}
		gameObject.transform.translate(new Vector2((int) (-1 * instance.getGameSpeed()), 0));
	}

}
