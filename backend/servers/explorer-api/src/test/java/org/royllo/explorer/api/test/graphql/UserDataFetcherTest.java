package org.royllo.explorer.api.test.graphql;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.api.graphql.generated.DgsConstants;
import org.royllo.explorer.api.graphql.generated.client.UserByUserIdGraphQLQuery;
import org.royllo.explorer.api.graphql.generated.client.UserByUserIdProjectionRoot;
import org.royllo.explorer.api.graphql.generated.client.UserByUsernameGraphQLQuery;
import org.royllo.explorer.api.graphql.generated.client.UserByUsernameProjectionRoot;
import org.royllo.explorer.api.graphql.generated.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("UserDataFetcher tests")
public class UserDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    @DisplayName("userByUserId()")
    public void userByUserId() {
        User user = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        UserByUserIdGraphQLQuery.newRequest().userId("22222222-2222-2222-2222-222222222222").build(),
                        new UserByUserIdProjectionRoot<>().userId().username())
                        .serialize(),
                "data." + DgsConstants.QUERY.UserByUserId,
                new TypeRef<>() {
                });

        // Testing the results.
        assertNotNull(user);
        assertEquals("22222222-2222-2222-2222-222222222222", user.getUserId());
        assertEquals("straumat", user.getUsername());
    }

    @Test
    @DisplayName("userByUsername()")
    public void userByUsername() {
        User user = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        UserByUsernameGraphQLQuery.newRequest().username("straumat").build(),
                        new UserByUsernameProjectionRoot<>().userId().username())
                        .serialize(),
                "data." + DgsConstants.QUERY.UserByUsername,
                new TypeRef<>() {
                });

        // Testing the results.
        assertNotNull(user);
        assertEquals("22222222-2222-2222-2222-222222222222", user.getUserId());
        assertEquals("straumat", user.getUsername());
    }

}
