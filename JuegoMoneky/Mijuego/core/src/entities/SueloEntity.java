package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants;

public class SueloEntity extends Actor {

    private Texture textura;
    private TextureRegion texturaRegion;
    private World world;
    private Body body;
    private Fixture fixture;


    public SueloEntity(Texture textura, World world, Vector2 position, float width, float height){

        this.textura = textura;
        this.world = world;


        textura.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        texturaRegion = new TextureRegion(textura,0,0,width,height);

        BodyDef def = new BodyDef();
        def.position.set(position.x+(width/2),position.y+(height/2));
        def.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2,height/2);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("suelo");
        shape.dispose();


        setSize(width*Constants.PIXELS_IN_METERS,height*Constants.PIXELS_IN_METERS);
        setPosition(position.x*Constants.PIXELS_IN_METERS,position.y*Constants.PIXELS_IN_METERS);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texturaRegion,getX(),getY(),getWidth(),getHeight());


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
