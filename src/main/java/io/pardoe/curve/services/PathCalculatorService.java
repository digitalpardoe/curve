package io.pardoe.curve.services;

import io.pardoe.curve.models.Repository;
import io.pardoe.curve.models.User;
import io.pardoe.curve.models.interfaces.Graphable;
import io.pardoe.curve.responses.ContributionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathCalculatorService {

    @Autowired
    private GitHubService gitHubService;

    public ContributionPath calculate(String fromUsername, String toUsername) {

        User fromUser = gitHubService.getUser(fromUsername);
        User toUser = gitHubService.getUser(toUsername);

        ArrayList<Repository> repositoriesPath;

        Repository initialCalculation = this.initialCalculation(fromUser, toUser);

        if (initialCalculation != null) {
            repositoriesPath = new ArrayList<>() {{ add(initialCalculation); }};
        } else {
            repositoriesPath = fullCalculation(fromUser, toUser);
        }

        return new ContributionPath(fromUser, toUser, repositoriesPath);
    }

    /*
     * This is premature optimisation but it seems a shame not to gain a little
     * efficiency when the opportunity presents itself in such a clear way.
     */
    private Repository initialCalculation(User fromUser, User toUser) {

        ArrayList<Repository> fromList = new ArrayList<>(fromUser.getRepositories());
        ArrayList<Repository> toList = toUser.getRepositories();

        fromList.retainAll(toList);

        if (fromList.size() > 0) {
            return fromList.get(0);
        } else {
            return null;
        }
    }

    /*
     * Fairly basic BFS algorithm implementation with path tracking, there are
     * a few things I'd think about doing to improve this under a full six
     * degrees of Kevin Bacon situation:
     *
     *  -   Traverse from start and end nodes in an attempt to meet in the middle
     *  -   Lazily load data from the GitHub API as it's required
     *  -   Pull data from the API and use Neo4j instead of this algorithm
     *  -   Plan more and model the data with repositories as edges between the
     *      users as vertices
     *  -   Set a maximum search depth as this thing could run forever
     */
    private ArrayList<Repository> fullCalculation(User fromUser, User toUser) {

        LinkedList<Graphable> toVisit = new LinkedList<>();
        HashSet<Graphable> visited = new HashSet<>();
        HashMap<Graphable, Graphable> parents = new HashMap<>();

        visited.add(fromUser);
        toVisit.add(fromUser);

        while (!toVisit.isEmpty()) {
            Graphable next = toVisit.remove();

            next.getNeighbours().forEach(neighbour -> {
                if (!visited.contains(neighbour)) {
                    toVisit.add(neighbour);
                    visited.add(neighbour);
                    parents.put(neighbour, next);
                }
            });

            if (next.equals(toUser)) break;
        }

        ArrayList<Repository> contributionPath = new ArrayList<>();
        Graphable node = toUser;

        while (node != null) {
            if (node instanceof Repository) {
                contributionPath.add((Repository) node);
            }
            node = parents.get(node);
        }

        Collections.reverse(contributionPath);

        return contributionPath;
    }
}
