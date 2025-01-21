package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants;

public class BabaEntity extends Actor {

    private Texture textura;
    private World world;
    public Body body;
    private Fixture fixture;

    public boolean die = false;

    public float h_player=0.5f;
    public float w_player=0.5f;

    private PlayerEntity player;

    public BabaEntity(Texture textura, World world, Vector2 position, PlayerEntity player){

        this.player = player;
        this.textura = textura;
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0,0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(-0.5f, -0.5f);
        shape.set(vertices);

        fixture = body.createFixture(shape, 1);
        fixture.setUserData("baba");
        shape.dispose();

        setSize(Constants.PIXELS_IN_METERS,Constants.PIXELS_IN_METERS);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(!die) {
            setPosition((body.getPosition().x-w_player)*Constants.PIXELS_IN_METERS,
                    (body.getPosition().y-h_player)*Constants.PIXELS_IN_METERS);


            batch.draw(textura, getX(), getY(), getWidth(), getHeight());
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float playerX = player.body.getPosition().x;
        float yoX = body.getPosition().x;
        float distancia = yoX-playerX;

        int ataque = randomWithRange(3,5);
        int recuperacion = randomWithRange(2,3);

        if((Math.abs(distancia)<ataque)&&(Math.abs(distancia)>recuperacion)) {
            float velocidadY = body.getLinearVelocity().y;
            if(distancia>0){
                body.setLinearVelocity(-Constants.PLAYER_SPEED, velocidadY);
            }else{
                body.setLinearVelocity(Constants.PLAYER_SPEED, velocidadY);
            }

        }
    }

    public int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
