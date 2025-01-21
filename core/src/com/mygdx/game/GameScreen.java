package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import entities.PlatanoEntity;
import entities.SueloEntity;
import entities.BabaEntity;
import entities.MetaEntity;
import entities.PlayerEntity;

public class GameScreen extends BaseScreen{

    private Skin skin;
    private Stage stage;
    private World world;
    private SpriteBatch batch;
    private Texture texture;
    private float escala;
    PlayerEntity mono;
    SueloEntity suelo;
    ArrayList<PlatanoEntity> platano = new ArrayList<PlatanoEntity>();
    SueloEntity[] bloques = new SueloEntity[30];
    ArrayList<BabaEntity> baba = new ArrayList<BabaEntity>();
    MetaEntity meta;

    ArrayList<Body> cuerposABorrar = new ArrayList<Body>();
    public TextArea puntuacion;
    public int contadorPlatano = 0;


    public GameScreen(MainGame game){
        super(game);

        stage = new Stage(new FitViewport(640,320));
        world = new World(new Vector2(0,-10),true);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        puntuacion = new TextArea("Platano: "+ contadorPlatano, skin);
        puntuacion.setX(100);
        puntuacion.setY(250);
        puntuacion.setHeight(30);
        puntuacion.setWidth(100);
        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact,"player","baba")){

                    float monoY;
                    float babaY;


                    if(contact.getFixtureA().getUserData().equals("player")) {
                        monoY = contact.getFixtureA().getBody().getPosition().y;
                        babaY = contact.getFixtureB().getBody().getPosition().y;
                    }else{
                        monoY = contact.getFixtureB().getBody().getPosition().y;
                        babaY = contact.getFixtureA().getBody().getPosition().y;
                    }

                    if((monoY-babaY)>0.8){
                        if(contact.getFixtureA().getUserData().equals("baba")){
                            cuerposABorrar.add(contact.getFixtureA().getBody());
                        }
                        if(contact.getFixtureB().getUserData().equals("baba")){
                            cuerposABorrar.add(contact.getFixtureB().getBody());
                        }
                    }else{
                        playerDie();
                    }
                }
                if(areCollided(contact,"player","platano")) {

                    float monoY;
                    float platanoY;


                    if (contact.getFixtureA().getUserData().equals("player")) {
                        monoY = contact.getFixtureA().getBody().getPosition().y;
                        platanoY = contact.getFixtureB().getBody().getPosition().y;
                    } else {
                        monoY = contact.getFixtureB().getBody().getPosition().y;
                        platanoY = contact.getFixtureA().getBody().getPosition().y;
                    }
                    if ((monoY-platanoY)>0) {
                        if (contact.getFixtureA().getUserData().equals("platano")) {
                            cuerposABorrar.add(contact.getFixtureA().getBody());
                            contadorPlatano++;
                        }
                        if (contact.getFixtureB().getUserData().equals("platano")) {
                            cuerposABorrar.add(contact.getFixtureB().getBody());
                            contadorPlatano++;
                        }
                    }

                }
                if(areCollided(contact,"player", "suelo")){
                    mono.setJumping(false);
                }
                if(areCollided(contact,"player", "meta")){
                    playerWin();
                }

            }

            @Override
            public void endContact(Contact contact) {
                if(areCollided(contact,"player", "suelo")){
                    mono.setJumping(true);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void show() {
        super.show();

        stage.setDebugAll(true);

        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("primermapa.jpg"));



        Texture texturaPlayer = game.manager.get("mono.png");
        Texture texturaPlayerdie = game.manager.get("monodie.png");
        Texture texturaPlayerwin = game.manager.get("monowin.png");
        ArrayList<Texture> arrayTexturaPlayer = new ArrayList<Texture>();
        arrayTexturaPlayer.add(texturaPlayer);
        arrayTexturaPlayer.add(texturaPlayerdie);
        arrayTexturaPlayer.add(texturaPlayerwin);

        Texture texturaSuelo = game.manager.get("suelo.png");
        Texture texturaBaba = game.manager.get("baba.png");
        Texture texturaPlatano = game.manager.get("platano.png");
        Texture texturaMeta = game.manager.get("meta.png");

        mono = new PlayerEntity(arrayTexturaPlayer,world,new Vector2(0,5));
        suelo = new SueloEntity(texturaSuelo,world,new Vector2(0,-1),100,2);
        for(int i=0;i<bloques.length;i++){
            int positionX = randomWithRange(6,90);
            int positionY = randomWithRange(1,1);
            int width = randomWithRange(1,5);
            int height = randomWithRange(1,2);
            bloques[i] = new SueloEntity(texturaSuelo,world,new Vector2(positionX,positionY),width,height);
        }
        for(int i=0;i<15;i++) {
            int positionX = randomWithRange(6,90);
            BabaEntity g = new BabaEntity(texturaBaba, world, new Vector2(positionX, 1), mono);
            baba.add(g);
        }
        for(int i=0;i<5;i++) {
            int positionX = randomWithRange(6,90);
            PlatanoEntity p = new PlatanoEntity(texturaPlatano, world, new Vector2(positionX, 6), mono);
            platano.add(p);
        }
        meta = new MetaEntity(texturaMeta,world,new Vector2(87f,2f));

        stage.addActor(mono);
        stage.addActor(suelo);

        for(int i=0;i<bloques.length;i++){
            stage.addActor(bloques[i]);
        }
        for(int i = 0; i< baba.size(); i++) {
            stage.addActor(baba.get(i));
        }
        for(int i = 0; i< platano.size(); i++) {
            stage.addActor(platano.get(i));
        }
        stage.addActor(puntuacion);
        stage.addActor(meta);
    }

    public int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture, 0, 0, texture.getWidth() * escala, texture.getHeight() * escala);
        batch.end();

        stage.act();
        world.step(delta,6,2);
        stage.draw();

        puntuacion.setText("Platanos: " + contadorPlatano);

        for (Body cuerpo: cuerposABorrar) {
            for(int i = 0; i< baba.size(); i++){
                if(baba.get(i).body.equals(cuerpo)){
                    baba.get(i).die = true;
                    //world.destroyBody(cuerpo);
                    baba.get(i).detach();
                    baba.get(i).remove();
                    baba.remove(i);
                }
            }
            for(int i = 0; i< platano.size(); i++){
                if(platano.get(i).body.equals(cuerpo)){
                    //world.destroyBody(cuerpo);
                    platano.get(i).detach();
                    platano.get(i).remove();
                    platano.remove(i);
                }
            }
        }

        cuerposABorrar.clear();

        if(mono.getX()>150){
            stage.getCamera().position.x = mono.getX() + 170;
            puntuacion.setX(mono.getX()-100);

        }else{
            stage.getCamera().position.x = 320;
        }


        //stage.getCamera().position.y = player.getY();

        stage.getCamera().update();


    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        float widthImagen = texture.getWidth();
        float heightImagen = texture.getHeight();
        float r = heightImagen / widthImagen;
        if (heightImagen > height){
            heightImagen = height;
            widthImagen = heightImagen / r;
        }
        if (widthImagen > width){
            widthImagen = width;
            heightImagen = widthImagen * r;
        }
        escala = width / widthImagen;
    }

    @Override
    public void hide() {
        super.hide();

        mono.detach();
        mono.remove();

        suelo.detach();
        suelo.remove();

        //puntuacion.detach();
        puntuacion.remove();

        for(int i=0;i<bloques.length;i++){
            bloques[i].detach();
            bloques[i].remove();
        }

        for(int i = 0; i< baba.size(); i++) {
            baba.get(i).detach();
            baba.get(i).remove();
        }
        for(int i = 0; i< platano.size(); i++) {
            platano.get(i).detach();
            platano.get(i).remove();
        }

    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
        world.dispose();
    }
    public void playerDie(){

        mono.setDie(true);

        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.gameOverScreen);
                    }
                })
        ));
    }

    public void playerWin(){

        mono.setWin(true);

        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.gameWinScreen);
                    }
                })
        ));
    }

}
