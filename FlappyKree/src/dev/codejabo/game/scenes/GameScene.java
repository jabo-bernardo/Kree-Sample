package dev.codejabo.game.scenes;

import java.awt.Font;
import java.awt.Graphics;

import dev.codejabo.game.customcomponents.Bird;
import dev.codejabo.game.customcomponents.Objective;
import dev.codejabo.game.customcomponents.Obstacle;
import dev.codejabo.game.gfx.Assets;
import dev.jabo.kree.Game;
import dev.jabo.kree.GameObject;
import dev.jabo.kree.Random;
import dev.jabo.kree.Scene;
import dev.jabo.kree.Vector2;
import dev.jabo.kree.components.BoxCollider;
import dev.jabo.kree.components.RigidBody;
import dev.jabo.kree.components.SpriteRenderer;
import dev.jabo.kree.ui.Text;

public class GameScene extends Scene {
	
	private GameObject bird;
	
	private GameObject[] obstacleBottom = new GameObject[3];
	private GameObject[] obstacleTop = new GameObject[3];
	private GameObject[] objective = new GameObject[3];
	
	// UI
	private Text scoreText = new Text(this, "Score: 0", new Vector2(32, 32), 128);
	
	private int playerScore = 0;
	
	private float gameSpeed = 2.0f;
	
	private boolean gameOver;

	public GameScene(Game game) {
		super(game);

	}

	@Override
	public void Initialize() {
		
		scoreText.setFont(new Font("Roboto", Font.BOLD, 24));

		bird = new GameObject(this, "Bird");
		bird.setPosition(new Vector2(128, 300));
		bird.setScale(new Vector2(32, 32));
		bird.addComponent(new Bird());
		bird.addComponent(new SpriteRenderer("/bird.png"));
		bird.addComponent(new RigidBody());
		bird.addComponent(new BoxCollider());
		
		
		for(int i = 0; i < obstacleBottom.length; i++) {
			obstacleBottom[i] = new GameObject(this, "Obstacle");
			obstacleBottom[i].setScale(new Vector2(128, Random.range(128, 350)));
			obstacleBottom[i].setPosition(new Vector2(356 + (i * 300), 600-obstacleBottom[i].transform.scale.y + 32));
			obstacleBottom[i].addComponent(new SpriteRenderer("/wood.png"));
			obstacleBottom[i].addComponent(new Obstacle());
			obstacleBottom[i].addComponent(new BoxCollider());
			((BoxCollider)obstacleBottom[i].getComponent("Box Collider")).trigger = true;
		}
		
		for(int i = 0; i < obstacleTop.length; i++) {
			obstacleTop[i] = new GameObject(this, "Obstacle");
			obstacleTop[i].setScale(new Vector2(128, 600-obstacleBottom[i].transform.scale.y - 128));
			obstacleTop[i].setPosition(new Vector2(obstacleBottom[i].transform.position.x, 0));
			obstacleTop[i].addComponent(new SpriteRenderer("/wood.png"));
			obstacleTop[i].addComponent(new Obstacle());
			obstacleTop[i].addComponent(new BoxCollider());
			((BoxCollider)obstacleTop[i].getComponent("Box Collider")).trigger = true;
		}
		
		for(int i = 0; i < objective.length; i++) {
			objective[i] = new GameObject(this, "Objective");
			objective[i].setScale(new Vector2(128, 160));
			objective[i].setPosition(new Vector2(obstacleBottom[i].transform.position.x, obstacleTop[i].transform.scale.y));
			objective[i].addComponent(new Objective());
			objective[i].addComponent(new BoxCollider());
			objective[i].addComponent(new SpriteRenderer("/foliage.png"));
			((BoxCollider)objective[i].getComponent("Box Collider")).trigger = true;
			
		}
	}
	
	int counter = 0;
	@Override
	public void Update() {
		// game over
		if(gameOver) {
			gameSpeed = 0;
			return;
		}
		
		// Game over if player is not on screen
		if(bird.transform.position.y > 800) {
			gameOver = true;
		}
		
		// Game over if player is not on screen		
		if(bird.transform.position.y < 0) {
			gameOver = true;
		}
		counter++;
		
		// Every 10 seconds adjust game speed
		if(counter >= 600) {
			gameSpeed += 1f;
			counter = 0;
		}
		
		// Check if collides on foliages (+score)
		for(GameObject obj : objective) {
			BoxCollider birdCollider = (BoxCollider) bird.getComponent("Box Collider");
			BoxCollider objectiveCollider = (BoxCollider) obj.getComponent("Box Collider");		
			if(birdCollider.getCollider() == null | objectiveCollider.getCollider() == null) {
				continue;
			}
			if(objectiveCollider.getCollider().contains(birdCollider.getCollider())) {
				if(!((Objective)obj.getComponent("Objective")).activated) {
					((Objective)obj.getComponent("Objective")).activated = true;
					playerScore++;
				}
			}
			
		}
		
		// Checks if collides with woods
		for(int i = 0; i < obstacleTop.length; i++) {
			BoxCollider birdCollider = (BoxCollider) bird.getComponent("Box Collider");
			BoxCollider obstacleTopCollider = (BoxCollider) obstacleTop[i].getComponent("Box Collider");
			BoxCollider obstacleBottomCollider = (BoxCollider) obstacleBottom[i].getComponent("Box Collider");
			
			if(birdCollider.getCollider() == null || obstacleTopCollider.getCollider() == null || obstacleBottomCollider.getCollider() == null) {
				continue;
			}
			
			if(	obstacleTopCollider.getCollider().contains(birdCollider.getCollider()) ||
				obstacleBottomCollider.getCollider().contains(birdCollider.getCollider())) {
				gameOver = true;
			}
		}
		
		// Score
		scoreText.setText("Score: " + playerScore);
		
	}

	@Override
	public void Render(Graphics g) {		
		
		// Draw background
		g.drawImage(Assets.background.image, 0, 0, null);
		
	}
	
	public float getGameSpeed() {
		return gameSpeed;
	}
	
	public GameObject[] getTopObstacles() {
		return obstacleTop;
	}
	
	public GameObject[] getBottomObstacles() {
		return obstacleBottom;
	}

}
