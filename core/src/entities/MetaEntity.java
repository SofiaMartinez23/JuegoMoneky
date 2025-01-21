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

public class MetaEntity extends Actor {

    private Texture textura;
    private World world;
    private Body body;
    private Fixture fixture;

    public float h_player=1f;
    public float w_player=1f;

    public MetaEntity(Texture textura, World world, Vector2 position){

        this.textura = textura;
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w_player,h_player);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("meta");
        shape.dispose();

        setSize(2*Constants.PIXELS_IN_METERS,2*Constants.PIXELS_IN_METERS);

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
