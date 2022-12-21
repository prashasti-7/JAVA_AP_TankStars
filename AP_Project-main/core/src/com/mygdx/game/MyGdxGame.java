//game screen with two tanks

package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import world.*;

import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.System.exit;

public class MyGdxGame extends Template implements Screen{
	final Games game;
	private Stage stage;
	private TextButton buttonChoose;
	private Array<Bullet> bullets;
	private Texture bulletImage;
	long lastDropTime;
	private Tank tank;
	private Tank tankB;

	private TextureRegion tanktexture1;
	private TextureRegion tanktexture2;

	private Skin skin;
	private SpriteBatch batch = new SpriteBatch();

	private Sprite sprite;
	private OrthographicCamera cam;
	private Texture tank_1;
	private Texture tank_2;
//	private Rectangle tank;
//	private Rectangle tankB;

	final float GAME_WORLD_WIDTH = 1000;
	final float GAME_WORLD_HEIGHT = 500;
	private Viewport viewport;

	public MyGdxGame(final Games game){
		this.game = game;
		batch = new SpriteBatch();
		sprite = new Sprite(new Texture(Gdx.files.internal("game.png")));
		sprite.setPosition(0,0);
		bulletImage = new Texture(Gdx.files.internal("ball.png"));
		tank_1 = new Texture(Gdx.files.internal("tank.png"));
		tank_2 = new Texture(Gdx.files.internal("tankB.png"));
		tanktexture1 = new TextureRegion(tank_1, 0, 10, 70, 40);
		tanktexture2 = new TextureRegion(tank_2, 50, 10, 70, 40);

		sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
//		sprite = new Sprite(new Texture(Gdx.files.internal("tank.png")));
//		sprite.setPosition(0,20);


		float aspectratio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

		cam = new OrthographicCamera();
		viewport = new FitViewport(GAME_WORLD_WIDTH * aspectratio, GAME_WORLD_HEIGHT, cam);
		viewport.apply();
		cam.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2,0);

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 800, 480);

		tank = new Tank();
		tank.x = 800 / 2 - 480 / 2; // center the bucket horizontally
		tank.y = 110; // bottom left corner of the bucket is 20 pixels above
		// the bottom screen edge
		tank.width = 80;
		tank.height = 44;

		tankB = new Tank();
		tankB.x = 800 / 2 + 480 / 2; // center the bucket horizontally
		tankB.y = 110; // bottom left corner of the bucket is 20 pixels above
		// the bottom screen edge
		tankB.width = 80;
		tankB.height = 44;

		bullets = new Array<Bullet>();
		spawnBullet();
	};

	public void render (float delta) {
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		sprite.draw(batch);
		batch.draw(tank_1, tank.x, tank.y, tank.width, tank.height);
		batch.draw(tank_2, tankB.x, tankB.y, tankB.width, tankB.height);

		if(Gdx.input.isKeyPressed(Input.Keys.P)){
			for (Rectangle bullet : bullets) {
				batch.draw(bulletImage, bullet.x+50, bullet.y);
			}
			spawnBullet();
		}

		batch.end();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			tank.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			tank.x += 200 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			tankB.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			tankB.x += 200 * Gdx.graphics.getDeltaTime();
		int flag=0;
//		while(flag%2==0) {
//			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
//				tank.x -= 200 * Gdx.graphics.getDeltaTime();
//				flag++;
//			}
//			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//				tank.x += 200 * Gdx.graphics.getDeltaTime();
//				flag++;
//			}
//		}
//		while(flag%2==1){
//			if (Gdx.input.isKeyPressed(Input.Keys.A))
//				tankB.x -= 200 * Gdx.graphics.getDeltaTime();
//			if (Gdx.input.isKeyPressed(Input.Keys.D))
//				tankB.x += 200 * Gdx.graphics.getDeltaTime();
//			flag++;
//		}
		if(tank.x < 100){
			tank.x=100;
		}
		if(tank.x > 820){
			tank.x=820;
		}
		if(tankB.x < 100){
			tankB.x=100;
		}
		if(tankB.x > 820){
			tankB.x=820;
		}


		if(TimeUtils.nanoTime() - lastDropTime > 1000000000){
			spawnBullet();
		}

		Iterator<Bullet> iterator = bullets.iterator();
		while(iterator.hasNext()){
			Bullet bullet = iterator.next();
			bullet.x += 200*Gdx.graphics.getDeltaTime();
			if(bullet.x<0){
				iterator.remove();
			}
			if(bullet.overlaps(tankB)){
				try{
					if (tankB.getHealth() > 0) {
						tankB.setHealth(bullet.getPower());
					}
					else if(tankB.getHealth()<0){
						throw new ArithmeticException("Health in negative");
					}
					else{
						System.out.println("Game Ended. Tank A won!");
						exit(0);
					}
				}
				catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println(e);
					e.printStackTrace();
				}
				iterator.remove();
			}
		}
	}

	private void spawnBullet(){
		Bullet bullet = new Bullet();
		bullet.x = tank.x;
		bullet.y = 120;
		bullet.height = 30;
		bullet.width = 30;
		bullets.add(bullet);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2,0);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		bulletImage.dispose();
		batch.dispose();
		sprite.getTexture().dispose();
		batch.dispose();
	}
}
