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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameConstants;
import com.mygdx.game.MyGdxGame;

public class TankChoose extends Template implements Screen {
    private Games game;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonChoose;
    private BitmapFont white, black;
    private Label outputLabel;
    private Texture backgroundImage;
    private TextureRegion backgroundTexture;
    private Sprite sprite;
    private OrthographicCamera cam;

    final float GAME_WORLD_WIDTH = 1000;
    final float GAME_WORLD_HEIGHT = 500;
    private Viewport viewport;
    private SpriteBatch batch;

    public TankChoose(final Games game){
        this.game = game;
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("chooseTank.png")));
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
        buttonChoose = new TextButton("Choose tank", skin, "small");
        buttonChoose.setSize(GameConstants.col_width*2, GameConstants.row_height*2-30);
        buttonChoose.setPosition(GameConstants.screenHeight/2, GameConstants.screenHeight/2 - 200);

        stage.addActor(buttonChoose);
        buttonChoose.setTouchable(Touchable.enabled);
        buttonChoose.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("game");
                game.setScreen(new MyGdxGame(game));
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
        Gdx.gl.glClearColor(0,0,0f,1);
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
