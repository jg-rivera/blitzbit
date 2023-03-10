package com.blitzbit.core.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blitzbit.core.entity.components.*;
import com.blitzbit.core.world.GameWorld;

import java.util.Comparator;

public class SpriteSystem extends SortedIteratingSystem {
    private final GameWorld world;
    private final SpriteBatch batch;

    private final BitmapFont font;

    public SpriteSystem(GameWorld world, SpriteBatch batch) {
        super(Family.all(SpriteComponent.class, PositionComponent.class, SizeComponent.class, ColorComponent.class).get(), new SpriteComparator());
        this.world = world;
        this.batch = batch;
        font = new BitmapFont();
        font.getData().setScale(0.5f);
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = EntityComponentMappers.position.get(entity);
        ColorComponent color = EntityComponentMappers.color.get(entity);
        SpriteComponent sprite = EntityComponentMappers.sprite.get(entity);
        SizeComponent size = EntityComponentMappers.size.get(entity);

        Texture texture = world.getAssetManager().getTexture(sprite.textureFilename);

        float x = position.x;
        float y = position.y;
        float width = size.width;
        float height = size.height;
        float originX = width / 2.0f;
        float originY = height / 2.0f;

        font.draw(batch, String.format("x=%.2f,y=%.2f", x, y), x - originX, y - originY);
        batch.draw(texture, x - originX, y - originY, width, height);
    }

    private static class SpriteComparator implements Comparator<Entity> {
        private final ComponentMapper<SpriteComponent> spriteComponentMap = ComponentMapper.getFor(SpriteComponent.class);

        @Override
        public int compare(Entity e1, Entity e2) {
            return spriteComponentMap.get(e1).zIndex - spriteComponentMap.get(e2).zIndex;
        }
    }
}
