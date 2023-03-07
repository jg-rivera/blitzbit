package com.blitzbit.core.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsComponent implements Component {
    public Body body;

    public PhysicsComponent() {

    }

    public PhysicsComponent(Body body) {
        this.body = body;
    }
}
