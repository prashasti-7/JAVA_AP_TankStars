//screen to show the resume, new game, exit buttons

package world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameConstants;
import com.mygdx.game.MyGdxGame;

public class MainScreen extends Template implements Screen {
    private Games game;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonResume, buttonExit;
    private BitmapFont white, black;
    private Label outputLabel;
    private Texture backgroundImage;
    private Sprite sprite;
    private OrthographicCamera cam;

    final float GAME_WORLD_WIDTH = 1000;
    final float GAME_WORLD_HEIGHT = 500;
    private Viewport viewport;
    private TextureRegion backgroundTexture;
    private SpriteBatch batch;

    public MainScreen(final Games game){
        this.game = game;
        //Inner Class
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("mainScreen.png")));
        sprite.setPosition(0,0);
        sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

        float aspectratio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

        cam = new OrthographicCamera();
        viewport = new FitViewport(GAME_WORLD_WIDTH * aspectratio, GAME_WORLD_HEIGHT, cam);
        viewport.apply();
        cam.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2,0);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);   // This call should be after initialisation of stage.

        skin = new Skin(Gdx.files.internal(GameConstants.skin));
        buttonPlay = new TextButton("Play Button", skin, "small");
        buttonPlay.setSize(GameConstants.col_width*2, GameConstants.row_height*2);
        buttonPlay.setPosition(GameConstants.centerX+100, GameConstants.centerY+75);

        buttonResume = new TextButton("Resume Button", skin, "small");
        buttonResume.setSize(GameConstants.col_width*2, GameConstants.row_height*2);
        buttonResume.setPosition(GameConstants.centerX+100, buttonPlay.getY() - GameConstants.row_height - 70);

        buttonExit = new TextButton("Exit Button", skin, "small");
        buttonExit.setSize(GameConstants.col_width*2, GameConstants.row_height*2);
        buttonExit.setPosition(GameConstants.centerX+100, buttonExit.getY() + 55);

        stage.addActor(buttonPlay);
        buttonPlay.setTouchable(Touchable.enabled);
        buttonPlay.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("game");
                game.setScreen(new TankChoose(game));
            }
        });

        stage.addActor(buttonResume);
        buttonResume.setTouchable(Touchable.enabled);
        buttonResume.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("game");
                game.setScreen(new MyGdxGame(game));
            }
        });

        stage.addActor(buttonExit);
        buttonExit.setTouchable(Touchable.enabled);
        buttonExit.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("game");
                Gdx.app.exit();
            }
        });
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        stage.dispose();
        skin.dispose();
        sprite.getTexture().dispose();
        batch.dispose();
    }
}
