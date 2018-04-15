package io.pardoe.curve.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.pardoe.curve.models.Repository;
import io.pardoe.curve.models.User;

import java.util.ArrayList;

public class ContributionPath {

    @JsonIgnoreProperties({"repositories", "neighbours"})
    private final User fromUser;

    @JsonIgnoreProperties({"repositories", "neighbours"})
    private final User toUser;

    @JsonIgnoreProperties({"users", "neighbours"})
    private final ArrayList<Repository> repositories;

    public ContributionPath(User fromUser, User toUser, ArrayList<Repository> repositories) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.repositories = repositories;
    }

    public User getFromUser() {
        return this.fromUser;
    }

    public User getToUser() {
        return this.toUser;
    }

    public ArrayList<Repository> getRepositories() {
        return this.repositories;
    }

    public int getPathLength() {
        return this.repositories.size();
    }
}
