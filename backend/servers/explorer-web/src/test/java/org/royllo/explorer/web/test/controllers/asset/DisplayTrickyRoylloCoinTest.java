package org.royllo.explorer.web.test.controllers.asset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.royllo.explorer.web.test.util.BaseTest;
import org.royllo.test.tapd.asset.DecodedProofValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.royllo.explorer.core.util.constants.UserConstants.ANONYMOUS_USER_ID;
import static org.royllo.explorer.core.util.constants.UserConstants.ANONYMOUS_USER_USERNAME;
import static org.royllo.explorer.web.util.constants.PagesConstants.ASSET_PAGE;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_ASSET_ID;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_FROM_TEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@DisplayName("Display trickyRoylloCoin page tests")
@AutoConfigureMockMvc
@PropertySource("classpath:messages.properties")
public class DisplayTrickyRoylloCoinTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Environment environment;

    @ParameterizedTest
    @MethodSource("headers")
    @DisplayName("Check trickyRoylloCoin asset page")
    void assetPage(final HttpHeaders headers) throws Exception {
        // Test data.
        final DecodedProofValueResponse.DecodedProof assetFromTest = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProof(0);
        final DecodedProofValueResponse.DecodedProof.Asset TRICKY_ROYLLO_COIN_1_ASSET_STATE = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProof(0).getAsset();
        final DecodedProofValueResponse.DecodedProof.Asset TRICKY_ROYLLO_COIN_2_ASSET_STATE = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProof(1).getAsset();
        final DecodedProofValueResponse.DecodedProof.Asset TRICKY_ROYLLO_COIN_3_ASSET_STATE = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProof(3).getAsset();
        final DecodedProofValueResponse.DecodedProof.Asset TRICKY_ROYLLO_COIN_4_ASSET_STATE = TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProof(4).getAsset();

        mockMvc.perform(get("/asset/" + TRICKY_ROYLLO_COIN_ASSET_ID).headers(headers))
                .andExpect(status().isOk())
                .andExpect(view().name(containsString(ASSET_PAGE)))
                // Checking tab header.
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getName() + "<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getAssetId() + "<")))
                // Asset definition.
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getAssetId() + "<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getGenesisPoint() + "<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getName() + "<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getMetaDataHash() + "<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getOutputIndex() + "<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAssetGenesis().getVersion() + "<")))
                .andExpect(content().string(containsString(">Normal<")))
                .andExpect(content().string(containsString(">" + assetFromTest.getAsset().getAmount() + "<")))
                // No Asset group.
                // Four asset states.
                // First asset state.
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getChainAnchor().getAnchorTx() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getChainAnchor().getAnchorBlockHash() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getChainAnchor().getAnchorOutpoint() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getChainAnchor().getInternalKey() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getChainAnchor().getMerkleRoot() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getChainAnchor().getTapscriptSibling() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getScriptVersion() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_1_ASSET_STATE.getScriptKey() + "<")))
                // Second asset state.
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getChainAnchor().getAnchorTx() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getChainAnchor().getAnchorBlockHash() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getChainAnchor().getAnchorOutpoint() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getChainAnchor().getInternalKey() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getChainAnchor().getMerkleRoot() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getChainAnchor().getTapscriptSibling() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getScriptVersion() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_2_ASSET_STATE.getScriptKey() + "<")))
                // Third asset state.
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getChainAnchor().getAnchorTx() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getChainAnchor().getAnchorBlockHash() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getChainAnchor().getAnchorOutpoint() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getChainAnchor().getInternalKey() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getChainAnchor().getMerkleRoot() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getChainAnchor().getTapscriptSibling() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getScriptVersion() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_3_ASSET_STATE.getScriptKey() + "<")))
                // Fourth asset state.
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getChainAnchor().getAnchorTx() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getChainAnchor().getAnchorBlockHash() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getChainAnchor().getAnchorOutpoint() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getChainAnchor().getInternalKey() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getChainAnchor().getMerkleRoot() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getChainAnchor().getTapscriptSibling() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getScriptVersion() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_4_ASSET_STATE.getScriptKey() + "<")))
                // Owner.
                .andExpect(content().string(containsString(">" + ANONYMOUS_USER_ID + "<")))
                .andExpect(content().string(containsString(">" + ANONYMOUS_USER_USERNAME + "<")))
                // Proof files.
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofRequest(0).getRawProof() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofRequest(1).getRawProof() + "<")))
                .andExpect(content().string(containsString(">" + TRICKY_ROYLLO_COIN_FROM_TEST.getDecodedProofRequest(3).getRawProof() + "<")))
                // Error messages.
                .andExpect(content().string(not(containsString(environment.getProperty("asset.view.error.noAssetId")))))
                .andExpect(content().string(not(containsString(Objects.requireNonNull(
                                environment.getProperty("asset.view.error.assetNotFound"))
                        .replace("\"{0}\"", "&quot;" + TRICKY_ROYLLO_COIN_ASSET_ID + "&quot;")))));
    }

}
