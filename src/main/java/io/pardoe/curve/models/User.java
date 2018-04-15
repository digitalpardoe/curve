package io.pardoe.curve.models;

import io.pardoe.curve.models.interfaces.Graphable;

import java.util.ArrayList;

public class User implements Graphable {

    private final String username;
    private ArrayList<Repository> repositories;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<Repository> getRepositories() {
        return this.repositories;
    }

    public void setRepositories(ArrayList<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ArrayList<Graphable> getNeighbours() {

        // This is messy, but as we're mocking data for demo purposes we know
        // it will work as we're expecting it to.
        return (ArrayList<Graphable>)(ArrayList<? extends Graphable>) this.repositories;
    }
}
