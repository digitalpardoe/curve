package io.pardoe.curve;

import io.pardoe.curve.responses.ContributionPath;
import io.pardoe.curve.services.PathCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UsersController {

    @Autowired
    private PathCalculatorService pathCalculatorService;

    @RequestMapping(
            value = "/users/{fromUsername}/contributionpath/{toUsername}",
            method = GET,
            produces = { "application/json" })
    public ContributionPath contributionPath(@PathVariable String fromUsername,
                                             @PathVariable String toUsername) {

        return pathCalculatorService.calculate(fromUsername, toUsername);
    }
}
