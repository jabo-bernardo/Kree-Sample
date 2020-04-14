package dev.codejabo.game.customcomponents;

import java.awt.Graphics;

import dev.jabo.kree.Component;
import dev.jabo.kree.Input;
import dev.jabo.kree.components.RigidBody;

// This is a custom component learn how to make one at [https://jabo-bernardo.github.io/kree-documentation/#creating-player-movement-component]
public class Bird extends Component {

	RigidBody birdBody;
	
	public Bird() {
		
		this.name = "Bird";
		
	}
	
	@Override
	public void Update() {
		// Initialize
		if(gameObject != null) {
			birdBody = (RigidBody) gameObject.getComponent("RigidBody");
			birdBody.mass = 0.5f;
		}
		
		if(Input.isKeyPressed(32)) {
			birdBody.mass = -0.5f;
			gameObject.transform.rotation -= 0.5f;
		} else {
			birdBody.mass = 0.5f;
			gameObject.transform.rotation += 0.5f;
		}
		
	}
	
	@Override
	public void Render(Graphics arg0) {
		
	}

}
