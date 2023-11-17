package org.royllo.explorer.api.test.graphql;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import com.netflix.graphql.dgs.exceptions.QueryException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.api.graphql.generated.DgsConstants;
import org.royllo.explorer.api.graphql.generated.client.AssetStatesByAssetIdGraphQLQuery;
import org.royllo.explorer.api.graphql.generated.client.AssetStatesByAssetIdProjectionRoot;
import org.royllo.explorer.api.graphql.generated.types.AssetStatePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.royllo.explorer.api.configuration.APIConfiguration.MAXIMUM_PAGE_SIZE;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_ASSET_ID;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_FROM_TEST;

@SpringBootTest
@DisplayName("AssetStateDataFetcher tests")
public class AssetStateDataFetcherTest {

    private static final String TRICKY_ROYLLO_COIN_ASSET_STATE_ID_1 = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofResponse(0).getAsset().getAssetStateId();
    private static final String TRICKY_ROYLLO_COIN_ASSET_STATE_ID_2 = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofResponse(1).getAsset().getAssetStateId();
    private static final String TRICKY_ROYLLO_COIN_ASSET_STATE_ID_3 = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofResponse(3).getAsset().getAssetStateId();
    private static final String TRICKY_ROYLLO_COIN_ASSET_STATE_ID_4 = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofResponse(4).getAsset().getAssetStateId();

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    @DisplayName("assetStatesByAssetId()")
    public void assetStatesByAssetId() {
        // Getting one page.
        AssetStatePage assetStatePage = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        AssetStatesByAssetIdGraphQLQuery.newRequest().assetId(TRICKY_ROYLLO_COIN_ASSET_ID).page(1).build(),
                        new AssetStatesByAssetIdProjectionRoot<>().content()
                                .assetStateId()
                                .creator().userId().username().getParent()
                                .asset().assetId().name().getParent()
                                .anchorBlockHash()
                                .anchorOutpoint().txId().vout().getParent()
                                .internalKey()
                                .merkleRoot()
                                .tapscriptSibling()
                                .scriptVersion()
                                .scriptKey()
                                .getParent()
                                .totalElements()
                                .totalPages()
                ).serialize(),
                "data." + DgsConstants.QUERY.AssetStatesByAssetId,
                new TypeRef<>() {
                });

        // Testing the results.
        assertEquals(4, assetStatePage.getTotalElements());
        assertEquals(1, assetStatePage.getTotalPages());
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_1)));
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_2)));
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_3)));
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_4)));
    }

    @Test
    @DisplayName("assetStatesByAssetId() with page size")
    public void assetStatesByAssetIdWithPageSize() {
        // Getting page 2.
        AssetStatePage assetStatePage = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        AssetStatesByAssetIdGraphQLQuery.newRequest().assetId(TRICKY_ROYLLO_COIN_ASSET_ID).page(2).pageSize(1).build(),
                        new AssetStatesByAssetIdProjectionRoot<>().content()
                                .assetStateId()
                                .creator().userId().username().getParent()
                                .asset().assetId().name().getParent()
                                .anchorBlockHash()
                                .anchorOutpoint().txId().vout().getParent()
                                .internalKey()
                                .merkleRoot()
                                .tapscriptSibling()
                                .scriptVersion()
                                .scriptKey()
                                .getParent()
                                .totalElements()
                                .totalPages()
                ).serialize(),
                "data." + DgsConstants.QUERY.AssetStatesByAssetId,
                new TypeRef<>() {
                });

        // Testing the results.
        assertEquals(4, assetStatePage.getTotalElements());
        assertEquals(4, assetStatePage.getTotalPages());
        assertFalse(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_1)));
        // This one is assertTrue because we asked for page 2.
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_2)));
        assertFalse(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_3)));
        assertFalse(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_4)));
    }

    @Test
    @DisplayName("assetStatesByAssetId() without page number")
    public void assetStatesByAssetIdWithoutPageNumber() {
        // Getting a page without setting page number.
        AssetStatePage assetStatePage = dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        AssetStatesByAssetIdGraphQLQuery.newRequest().assetId(TRICKY_ROYLLO_COIN_ASSET_ID).build(),
                        new AssetStatesByAssetIdProjectionRoot<>().content()
                                .assetStateId()
                                .creator().userId().username().getParent()
                                .asset().assetId().name().getParent()
                                .anchorBlockHash()
                                .anchorOutpoint().txId().vout().getParent()
                                .internalKey()
                                .merkleRoot()
                                .tapscriptSibling()
                                .scriptVersion()
                                .scriptKey()
                                .getParent()
                                .totalElements()
                                .totalPages()
                ).serialize(),
                "data." + DgsConstants.QUERY.AssetStatesByAssetId,
                new TypeRef<>() {
                });

        // Testing the results.
        assertEquals(4, assetStatePage.getTotalElements());
        assertEquals(1, assetStatePage.getTotalPages());

        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_1)));
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_2)));
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_3)));
        assertTrue(assetStatePage.getContent().stream().anyMatch(assetState -> assetState.getAssetStateId().equals(TRICKY_ROYLLO_COIN_ASSET_STATE_ID_4)));
    }

    @Test
    @DisplayName("assetStatesByAssetId() with invalid page size")
    public void assetStatesByAssetIdWithInvalidPageSize() {
        QueryException e = assertThrows(QueryException.class, () -> dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        AssetStatesByAssetIdGraphQLQuery.newRequest().assetId("asset_id_0").pageSize(MAXIMUM_PAGE_SIZE + 1).build(),
                        new AssetStatesByAssetIdProjectionRoot<>().content()
                                .assetStateId()
                ).serialize(),
                "data." + DgsConstants.QUERY.AssetStatesByAssetId,
                new TypeRef<>() {
                }));
        assertEquals("Page size can't be superior to " + MAXIMUM_PAGE_SIZE, e.getMessage());
    }

    @Test
    @DisplayName("assetStatesByAssetId() with negative number")
    public void assetStatesByAssetIdWithNegativePageNumber() {
        QueryException e = assertThrows(QueryException.class, () -> dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        AssetStatesByAssetIdGraphQLQuery.newRequest().assetId("ANY_ASSET").page(-1).build(),
                        new AssetStatesByAssetIdProjectionRoot<>().content()
                                .assetStateId()
                ).serialize(),
                "data." + DgsConstants.QUERY.AssetStatesByAssetId,
                new TypeRef<>() {
                }));
        assertEquals("Page number starts at page 1", e.getMessage());
    }

}
