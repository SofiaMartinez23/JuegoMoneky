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

public class PlatanoEntity extends Actor {

    private Texture textura;
    private World world;
    public Body body;
    private Fixture fixture;

    public boolean die = false;

    public float h_player=0.5f;
    public float w_player=0.5f;

    private PlayerEntity player;

    public PlatanoEntity(Texture textura, World world, Vector2 position, PlayerEntity player){

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
        fixture.setUserData("platano");
        shape.dispose();

        setSize(Constants.PIXELS_IN_METERS,Constants.PIXELS_IN_METERS);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        setPosition((body.getPosition().x - w_player) * Constants.PIXELS_IN_METERS,
                (body.getPosition().y - h_player) * Constants.PIXELS_IN_METERS);


        batch.draw(textura, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
