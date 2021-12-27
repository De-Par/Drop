package info.fandroid.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

    SpriteBatch batch;
    BitmapFont font;
    BitmapFont font2;

    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font2 = new BitmapFont();
        font.setColor(Color.GOLD);
        font2.setColor(Color.GOLD);
        font.getData().setScale(4,4);
        font2.getData().setScale(4,4);
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
        font2.dispose();
    }
}