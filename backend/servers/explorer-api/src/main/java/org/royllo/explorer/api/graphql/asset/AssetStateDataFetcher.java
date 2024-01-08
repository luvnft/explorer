package org.royllo.explorer.api.graphql.asset;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import lombok.RequiredArgsConstructor;
import org.royllo.explorer.api.util.base.BaseDataFetcher;
import org.royllo.explorer.core.dto.asset.AssetStateDTO;
import org.royllo.explorer.core.service.asset.AssetStateService;
import org.springframework.data.domain.Page;

import java.util.Objects;

import static org.royllo.explorer.api.configuration.APIConfiguration.DEFAULT_PAGE_SIZE;
import static org.royllo.explorer.api.configuration.APIConfiguration.FIRST_PAGE;
import static org.royllo.explorer.api.configuration.APIConfiguration.MAXIMUM_PAGE_SIZE;

/**
 * Asset state data fetcher.
 */
@DgsComponent
@RequiredArgsConstructor
public class AssetStateDataFetcher extends BaseDataFetcher {

    /** Asset state service. */
    private final AssetStateService assetStateService;

    /**
     * Get asset state by asset state id.
     *
     * @param assetId  asset id
     * @param page     the page number you want
     * @param pageSize the page size you want
     * @return list of assets corresponding to the search
     */
    @SuppressWarnings("DuplicatedCode")
    @DgsQuery
    public final Page<AssetStateDTO> assetStatesByAssetId(final @InputArgument String assetId,
                                                          final @InputArgument Integer page,
                                                          final @InputArgument Integer pageSize) {
        // Value we will use.
        final int finalPage = Objects.requireNonNullElse(page, FIRST_PAGE);
        final int finalPageSize = Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE);

        // Checking page parameters.
        if (finalPageSize > MAXIMUM_PAGE_SIZE) {
            throw new DgsInvalidInputArgumentException("Page size can't be superior to " + MAXIMUM_PAGE_SIZE, null);
        }
        if (finalPage < FIRST_PAGE) {
            throw new DgsInvalidInputArgumentException("Page number starts at page " + FIRST_PAGE, null);
        }

        return assetStateService.getAssetStatesByAssetId(assetId,
                finalPage,
                finalPageSize);
    }

}
