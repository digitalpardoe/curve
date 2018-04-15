package io.pardoe.curve.models;

import io.pardoe.curve.models.interfaces.Graphable;

import java.util.ArrayList;

public class Repository implements Graphable {

    private final String name;
    private ArrayList<User> users;

    public Repository(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public ArrayList<Graphable> getNeighbours() {

        // This is messy, but as we're mocking data for demo purposes we know
        // it will work as we're expecting it to.
        return (ArrayList<Graphable>)(ArrayList<? extends Graphable>) this.users;
    }
}
