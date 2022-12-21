package world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class SplashScreen extends Template implements Screen {
    private Texture logo;
    private SpriteBatch batch;
    final Games game;
    private BitmapFont font;
    private Sprite sprite;
    private OrthographicCamera cam;
    private float time = 0.75f;

    final float GAME_WORLD_WIDTH = 100;
    final float GAME_WORLD_HEIGHT = 50;
    private Viewport viewport;

    public SplashScreen(final Games game){
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        sprite = new Sprite(new Texture(Gdx.files.internal("gameScreen.png")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

        float aspectratio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

        cam = new OrthographicCamera();
        viewport = new FitViewport(GAME_WORLD_WIDTH * aspectratio, GAME_WORLD_HEIGHT, cam);
        viewport.apply();
        cam.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2,0);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        time -= delta;

        if(time<0){
            System.out.println("clicked");
            game.setScreen(new MainScreen(game));
        }
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
    public void dispose() {
        sprite.getTexture().dispose();
        batch.dispose();
    }
}
