package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    public GameScreen gameScreen;
    public GameScreen2 gameScreen2;
    public MenuScreen menuScreen;
    public GameOverScreen gameOverScreen;
    public GameWinScreen gameWinScreen;
    public GameEnd gameEndScreen;
    public AssetManager manager;

    @Override
    public void create() {

        manager = new AssetManager();
        manager.load("menu.png", Texture.class);
        manager.load("primermapa.jpg", Texture.class);
        manager.load("segundomapa.jpg", Texture.class);
        manager.load("gameend.png", Texture.class);
        manager.load("meta.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("gamewin.png", Texture.class);
        manager.load("mono.png", Texture.class);
        manager.load("suelo.png", Texture.class);
        manager.load("baba.png", Texture.class);
        manager.load("platano.png", Texture.class);
        manager.load("monodie.png", Texture.class);
        manager.load("monowin.png", Texture.class);
        manager.finishLoading();

        gameScreen = new GameScreen(this);
        gameScreen2 = new GameScreen2(this);
        gameEndScreen = new GameEnd(this);
        menuScreen = new MenuScreen(this);
        gameOverScreen = new GameOverScreen(this);
        gameWinScreen = new GameWinScreen(this);

        setScreen(menuScreen);

    }
}
