package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public abstract class Trainer {
    String name;
    Pokemon active;
    ArrayList<Pokemon> arsenal;

    public void recoverAll() {
        for (Pokemon p : arsenal) p.recharge();
    }

    public abstract Response attack();

    public abstract Response retreat();

    public abstract Response pass();

    public abstract Response choosePokemon();

}
