package org.royllo.explorer.api.test.graphql;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.api.graphql.generated.DgsConstants;
import org.royllo.explorer.api.graphql.generated.client.ProofsByAssetIdGraphQLQuery;
import org.royllo.explorer.api.graphql.generated.client.ProofsByAssetIdProjectionRoot;
import org.royllo.explorer.api.graphql.generated.types.Proof;
import org.royllo.explorer.api.graphql.generated.types.ProofPage;
import org.royllo.explorer.core.util.base.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.royllo.explorer.core.dto.proof.ProofDTO.PROOF_FILE_NAME_EXTENSION;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_USER_ID;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_ASSET_ID;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_FROM_TEST;

@SpringBootTest
@DisplayName("ProofDataFetcher tests")
public class ProofDataFetcherTest extends Base {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @Test
    @DisplayName("proofsByAssetId()")
    public void proofsByAssetId() {

        // Proof 1.
        final String TRICKY_ROYLLO_COIN_1_RAW_PROOF = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofRequest(0).getRawProof();
        final String TRICKY_ROYLLO_COIN_1_PROOF_ID = sha256(TRICKY_ROYLLO_COIN_1_RAW_PROOF);

        // Proof 2.
        final String TRICKY_ROYLLO_COIN_2_RAW_PROOF = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofRequest(1).getRawProof();
        final String TRICKY_ROYLLO_COIN_2_PROOF_ID = sha256(TRICKY_ROYLLO_COIN_2_RAW_PROOF);

        // Proof 3.
        final String TRICKY_ROYLLO_COIN_3_RAW_PROOF = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofRequest(3).getRawProof();
        final String TRICKY_ROYLLO_COIN_3_PROOF_ID = sha256(TRICKY_ROYLLO_COIN_3_RAW_PROOF);

        // Retrieving tricky royllo coin proof (page 1 of 10 elements).
        assertThat(dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        ProofsByAssetIdGraphQLQuery.newRequest().assetId(TRICKY_ROYLLO_COIN_ASSET_ID).pageNumber(1).pageSize(10).build(),
                        new ProofsByAssetIdProjectionRoot<>().content()
                                .creator().userId().username().parent()
                                .asset().assetId().parent()
                                .proofId()
                                .proofFileName()
                                .parent()
                                .totalElements()
                                .totalPages()
                ).serialize(),
                "data." + DgsConstants.QUERY.ProofsByAssetId,
                new TypeRef<ProofPage>() {
                }))
                .isNotNull()
                .satisfies(proofPage -> {
                    assertEquals(3, proofPage.getTotalElements());
                    assertEquals(1, proofPage.getTotalPages());

                    // Testing proof 1.
                    Proof proof1 = proofPage.getContent()
                            .stream()
                            .filter(proof -> proof.getProofId().equals(TRICKY_ROYLLO_COIN_1_PROOF_ID))
                            .findFirst()
                            .orElseThrow(() -> new AssertionError("Proof 1 not found"));
                    assertEquals(ANONYMOUS_USER_ID, proof1.getCreator().getUserId());
                    assertEquals(TRICKY_ROYLLO_COIN_ASSET_ID, proof1.getAsset().getAssetId());
                    assertEquals(TRICKY_ROYLLO_COIN_1_PROOF_ID, proof1.getProofId());
                    assertEquals(sha256(TRICKY_ROYLLO_COIN_1_RAW_PROOF) + PROOF_FILE_NAME_EXTENSION, proof1.getProofFileName());

                    // Testing proof 2.
                    Proof proof2 = proofPage.getContent()
                            .stream()
                            .filter(proof -> proof.getProofId().equals(TRICKY_ROYLLO_COIN_2_PROOF_ID))
                            .findFirst()
                            .orElseThrow(() -> new AssertionError("Proof 2 not found"));
                    assertEquals(ANONYMOUS_USER_ID, proof2.getCreator().getUserId());
                    assertEquals(TRICKY_ROYLLO_COIN_ASSET_ID, proof2.getAsset().getAssetId());
                    assertEquals(TRICKY_ROYLLO_COIN_2_PROOF_ID, proof2.getProofId());
                    assertEquals(sha256(TRICKY_ROYLLO_COIN_2_RAW_PROOF) + PROOF_FILE_NAME_EXTENSION, proof2.getProofFileName());

                    // Testing proof 3.
                    Proof proof3 = proofPage.getContent()
                            .stream()
                            .filter(proof -> proof.getProofId().equals(TRICKY_ROYLLO_COIN_3_PROOF_ID))
                            .findFirst()
                            .orElseThrow(() -> new AssertionError("Proof 3 not found"));
                    assertEquals(ANONYMOUS_USER_ID, proof3.getCreator().getUserId());
                    assertEquals(TRICKY_ROYLLO_COIN_ASSET_ID, proof3.getAsset().getAssetId());
                    assertEquals(TRICKY_ROYLLO_COIN_3_PROOF_ID, proof3.getProofId());
                    assertEquals(sha256(TRICKY_ROYLLO_COIN_3_RAW_PROOF) + PROOF_FILE_NAME_EXTENSION, proof3.getProofFileName());
                });

        // Checking page management results.
        assertThat(dgsQueryExecutor.executeAndExtractJsonPathAsObject(
                new GraphQLQueryRequest(
                        ProofsByAssetIdGraphQLQuery.newRequest().assetId(TRICKY_ROYLLO_COIN_ASSET_ID).pageNumber(1).pageSize(1).build(),
                        new ProofsByAssetIdProjectionRoot<>().content()
                                .creator().userId().username().parent()
                                .asset().assetId().parent()
                                .proofId()
                                .proofFileName()
                                .parent()
                                .totalElements()
                                .totalPages()
                ).serialize(),
                "data." + DgsConstants.QUERY.ProofsByAssetId,
                new TypeRef<ProofPage>() {
                }))
                .isNotNull()
                .satisfies(proofPage -> {
                    assertEquals(3, proofPage.getTotalElements());
                    assertEquals(3, proofPage.getTotalPages());
                });
    }

}
