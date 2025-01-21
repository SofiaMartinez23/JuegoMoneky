package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameWinScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;

    private TextButton menu;
    private TextButton siguientenivel;
    private Image title;

    public GameWinScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640,320));

        title = new Image(game.manager.get("gamewin.png",Texture.class));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        siguientenivel = new TextButton("Next Level", skin);
        menu = new TextButton("Go to Menu", skin);

        siguientenivel.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen2(game));
            }
        });

        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });

        title.setPosition(0, 0);
        title.setSize(640, 320);

        siguientenivel.setSize(200, 60);
        siguientenivel.setPosition(320-(siguientenivel.getWidth()/2), 110-(siguientenivel.getHeight()/2));

        menu.setSize(200, 60);
        menu.setPosition(320-(menu.getWidth()/2), 45-(menu.getHeight()/2));

        stage.addActor(title);
        stage.addActor(siguientenivel);
        stage.addActor(menu);
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void hide() {
        super.hide();

        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
