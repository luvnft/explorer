package org.royllo.explorer.api.graphql.user;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.royllo.explorer.api.util.base.BaseDataFetcher;
import org.royllo.explorer.core.dto.user.UserDTO;
import org.royllo.explorer.core.service.user.UserService;

/**
 * User data fetcher.
 */
@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher extends BaseDataFetcher {

    /** User service. */
    private final UserService userService;

    /**
     * Get user by its userId.
     *
     * @param userId user id
     * @return user if found or null
     */
    @DgsQuery
    public final UserDTO userByUserId(final @InputArgument String userId) {
        return userService.getUserByUserId(userId)
                .orElseThrow(DgsEntityNotFoundException::new);
    }

    /**
     * Get user by username.
     *
     * @param username username
     * @return user if found or null
     */
    @DgsQuery
    public final UserDTO userByUsername(final @InputArgument String username) {
        return userService.getUserByUsername(username)
                .orElseThrow(DgsEntityNotFoundException::new);
    }

}
