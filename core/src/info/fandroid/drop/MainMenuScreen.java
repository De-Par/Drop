package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    final info.fandroid.drop.Drop game;
    OrthographicCamera camera;
    static Texture areaImage;
    final protected int W = Gdx.graphics.getWidth();
    final protected int H = Gdx.graphics.getHeight();

    public MainMenuScreen (final info.fandroid.drop.Drop gam) {

        this.game = gam;
        areaImage = new Texture("Area.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()); //2100 1000
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {

        Gdx.graphics.getGL20().glClearColor(0.3f, 0.3f, 0.3f, 1);  //  Очистка игрового поля, закрашивание его в черный цвет для последующей анимации
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

            game.batch.draw(areaImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Создание заднего фона
            game.font2.draw(game.batch, "WELCOME TO WALL-E!", H/2, W/2);

        game.batch.end();

            if (Gdx.input.justTouched() || Gdx.input.isCursorCatched()) {
                dispose();
                game.setScreen(new DropKey(game));
            }
    }

    @Override
    public void resize(int width, int height) {}

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
        areaImage.dispose();
    }
}