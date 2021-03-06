package com.doom_tp.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.doom_tp.game.entities.Entity;
import com.doom_tp.game.entities.Player;
import com.doom_tp.game.entities.Attack;
import com.doom_tp.game.entities.Enemy1;
import com.doom_tp.game.entities.Enemy2;
import com.doom_tp.game.entities.Enemy3;
import com.doom_tp.game.entities.Enemy4;
import com.doom_tp.game.entities.Enemy5;
import com.doom_tp.game.entities.Enemy6;

public abstract class GameMap {

	protected ArrayList<Entity> entities;
	private Music music;
	float playerX = 0;
	float playerY = 0;
	private long playerStartTime = System.currentTimeMillis();
	private long enemyStartTime = System.currentTimeMillis();
	private boolean move6 = false;
	private float[] Xarray = new float[1000];
	private float[] Yarray = new float[1000];
	private int stepCounter = 0,enemyStep = 0;
	
	public GameMap() {
		entities = new ArrayList<Entity>();
		entities.add(new Player(10,1000,this));
		entities.add(new Enemy1(1150,950,this));//Right, On Land
		entities.add(new Enemy2(45,1000,this));//Left, Start Area
		entities.add(new Enemy3(50,150,this));//On Lava
		entities.add(new Enemy4(0,1500,this));//Falling Down
		entities.add(new Enemy5(55,1000,this));//Flying Up
		entities.add(new Enemy6(500,1000,this));//Following
		entities.add(new Attack(0,0,this));
		
		music = Gdx.audio.newMusic(Gdx.files.internal("bensound-thejazzpiano.mp3"));
		music.setVolume(0.5f);
		music.setLooping(true);
		music.play();
	}
	
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		for (Entity entity :  entities) {
			entity.render(batch);
		}
		
		if(Gdx.input.isTouched()) {
			camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			camera.update();
		}else {
			camera.position.x = playerX+400;
			camera.position.y = playerY;
		}
	}
	
	public void update(float delta) {
		playerX = entities.get(0).getX();
		playerY = entities.get(0).getY();
		float attackX = entities.get(7).getX();
		float attackY = entities.get(7).getY();
		
		for (Entity entity :  entities) {
			entity.update(delta, -9.8f);
		}
		
		enemyDetection(playerX,playerY);
		attackDetection(attackX, attackY);
		
		long currentTime = System.currentTimeMillis();
		if(currentTime - playerStartTime > 1000) {
			playerStartTime = currentTime;
			Xarray[stepCounter] = entities.get(0).getX();
			Yarray[stepCounter] = entities.get(0).getY();
			stepCounter ++;
			if(stepCounter > 999) {
				stepCounter = 0;
			}
		}
	}
	
	public void enemyDetection(float playerX,float playerY) {
		float enemy1X = entities.get(1).getX();
		float enemy1Y = entities.get(1).getY();
		if((playerX < enemy1X+5 && playerX > enemy1X-10)&&(playerY < enemy1Y+30 && playerY > enemy1Y-1)) {
			System.exit(0);
		}
		
		float enemy2X = entities.get(2).getX();
		float enemy2Y = entities.get(2).getY();
		if((playerX < enemy2X+5 && playerX > enemy2X-10)&&(playerY < enemy2Y+30 && playerY > enemy2Y-1)) {
			System.exit(0);
		}
		
		float enemy3X = entities.get(3).getX();
		float enemy3Y = entities.get(3).getY();
		if((playerX < enemy3X+5 && playerX > enemy3X-10)&&(playerY < enemy3Y+30 && playerY > enemy3Y-1)) {
			System.exit(0);
		}
		
		float enemy4X = entities.get(4).getX();
		float enemy4Y = entities.get(4).getY();
		if((playerX < enemy4X+5 && playerX > enemy4X-10)&&(playerY < enemy4Y+30 && playerY > enemy4Y-1)) {
			System.exit(0);
		}
		
		float enemy5X = entities.get(5).getX();
		float enemy5Y = entities.get(5).getY();
		if((playerX < enemy5X+5 && playerX > enemy5X-10)&&(playerY < enemy5Y+30 && playerY > enemy5Y-1)) {
			System.exit(0);
		}
		
		moveEnemy6();
		float enemy6X = entities.get(6).getX();
		float enemy6Y = entities.get(6).getY();
		if((playerX < enemy6X+10 && playerX > enemy6X-5)&&(playerY < enemy6Y+5 && playerY > enemy6Y-5)) {
			System.exit(0);
		}
	}
	
	private void moveEnemy6() {
		long currentTime = System.currentTimeMillis();
		if(currentTime - enemyStartTime > 2001) {
			move6 = true;
		}
		
		if(move6) {
			if(currentTime - enemyStartTime > 1050) {
				enemyStartTime = currentTime;
				float X = Xarray[enemyStep];
			float Y = Yarray[enemyStep];
			entities.set(6, new Enemy6(X-7,Y,this));
			enemyStep ++;
			if(enemyStep > 999) {
				enemyStep = 0;
			}
			}
		}
	}
	
	public void attackDetection(float attackX,float attackY) {
		float enemy1X = entities.get(1).getX();
		float enemy1Y = entities.get(1).getY();
		if((attackX < enemy1X+3 && attackX > enemy1X-3)&&(attackY < enemy1Y+17 && attackY > enemy1Y-1)) {
			entities.set(1, new Enemy1(1500,2000,this));
		}
		
		float enemy2X = entities.get(2).getX();
		float enemy2Y = entities.get(2).getY();
		if((attackX < enemy2X+7 && attackX > enemy2X-18)&&(attackY < enemy2Y+15 && attackY > enemy2Y-10)) {
			entities.set(2, new Enemy1(1500,2000,this));
		}
		
		float enemy3X = entities.get(3).getX();
		float enemy3Y = entities.get(3).getY();
		if((attackX < enemy3X+7 && attackX > enemy3X-18)&&(attackY < enemy3Y+15 && attackY > enemy3Y-10)) {
			entities.set(3, new Enemy1(1500,2000,this));
		}
		
		float enemy4X = entities.get(4).getX();
		float enemy4Y = entities.get(4).getY();
		if((attackX < enemy4X+7 && attackX > enemy4X-18)&&(attackY < enemy4Y+15 && attackY > enemy4Y-10)) {
			entities.set(4, new Enemy1(1500,2000,this));
		}
		
		float enemy5X = entities.get(5).getX();
		float enemy5Y = entities.get(5).getY();
		if((attackX < enemy5X+7 && attackX > enemy5X-18)&&(attackY < enemy5Y+15 && attackY > enemy5Y-10)) {
			entities.set(5, new Enemy1(1500,2000,this));
		}	
	}
	
	public float getPlayerX() {
		return entities.get(0).getX();
	}
	public float getPlayerY() {
		return entities.get(0).getY();
	}
	
	public void dispose() {
		music.dispose();
	}
	
	/**
	 * This method gets a tile by the pixel position within the game world at a specified layer.
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int)(x / TileType.TILE_SIZE), (int)(y / TileType.TILE_SIZE));
	}
	
	/**
	 * This method gets a tile at its coordinate within the map at a specified layer.
	 * @param layer
	 * @param col
	 * @param row
	 * @return
	 */
	public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

	public boolean collideMap(float x, float y, int width, int height) {
		if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight())
			return true;
		
		for (int row = (int)(y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
			for (int col = (int)(x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TileType type = getTileTypeByCoordinate(layer, col, row);
					if (type != null && type.isCollidable())
						return true;
				}
			}
		}
		return false;
	}
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayers();
	
	public int getPixelWidth() {
		return this.getWidth() * TileType.TILE_SIZE;
	}
	public int getPixelHeight() {
		return this.getHeight() * TileType.TILE_SIZE;
	}
}
