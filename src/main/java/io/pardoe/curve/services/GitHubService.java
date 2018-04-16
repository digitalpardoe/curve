package io.pardoe.curve.services;

import io.pardoe.curve.models.Repository;
import io.pardoe.curve.models.User;
import org.springframework.stereotype.Service;

/*
 * I'm leaving this implementation vague as there are really two ways of
 * getting this information from the GitHub API depending on the requirements.
 *
 * We can either use their standard API to get the repositories that the user is
 * currently working on (or at least are present on their account) and proceed
 * from there.
 *
 * The more likely solution is to use GitHub's GraphQL API to combine
 * information on about the user's commits, issues, pull requests and repos.
 *
 */
@Service
public class GitHubService {

    public User getUser(String username) {
        throw new UnsupportedOperationException();
    }

    /*
     * For the purposes of this demo application we don't need this method
     * but realistically we'd probably use it to continue the loading of
     * repositories as we traverse the search graph.
     */
    public Repository getRepository(String name) {
        throw new UnsupportedOperationException();
    }
}
