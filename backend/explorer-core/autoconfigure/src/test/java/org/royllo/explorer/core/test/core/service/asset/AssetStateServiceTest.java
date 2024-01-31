package org.royllo.explorer.core.test.core.service.asset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.dto.asset.AssetDTO;
import org.royllo.explorer.core.dto.asset.AssetStateDTO;
import org.royllo.explorer.core.dto.bitcoin.BitcoinTransactionOutputDTO;
import org.royllo.explorer.core.repository.asset.AssetGroupRepository;
import org.royllo.explorer.core.repository.asset.AssetRepository;
import org.royllo.explorer.core.repository.asset.AssetStateRepository;
import org.royllo.explorer.core.service.asset.AssetService;
import org.royllo.explorer.core.service.asset.AssetStateService;
import org.royllo.explorer.core.service.bitcoin.BitcoinService;
import org.royllo.explorer.core.test.util.TestWithMockServers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_ID;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_USER_DTO;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_USER_ID;
import static org.royllo.test.MempoolData.ROYLLO_COIN_ANCHOR_1_TXID;
import static org.royllo.test.TapdData.ROYLLO_COIN_ASSET_ID;
import static org.royllo.test.TapdData.ROYLLO_COIN_FROM_TEST;
import static org.royllo.test.TapdData.TRICKY_ROYLLO_COIN_ASSET_ID;

@SpringBootTest
@DirtiesContext
@DisplayName("AssetStateService tests")
public class AssetStateServiceTest extends TestWithMockServers {

    @Autowired
    private AssetGroupRepository assetGroupRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetStateRepository assetStateRepository;

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private AssetStateService assetStateService;

    @Test
    @DisplayName("addAssetState()")
    public void addAssetState() {
        // We retrieve a bitcoin transaction output from database for our test.
        final Optional<BitcoinTransactionOutputDTO> bto = bitcoinService.getBitcoinTransactionOutput(ROYLLO_COIN_ANCHOR_1_TXID, 0);
        assertTrue(bto.isPresent());

        // =============================================================================================================
        // First constraint test - Trying to save an asset state with an ID.
        AssertionError e = assertThrows(AssertionError.class, () -> assetStateService.addAssetState(AssetStateDTO.builder().id(1L).build()));
        assertEquals("Asset state already exists", e.getMessage());

        // =============================================================================================================
        // Second constraint test - Trying to save an asset state without an asset.
        e = assertThrows(AssertionError.class, () -> assetStateService.addAssetState(AssetStateDTO.builder().build()));
        assertEquals("Linked asset is required", e.getMessage());

        // =============================================================================================================
        // Third constraint test - Asset id is required.
        e = assertThrows(AssertionError.class, () -> assetStateService.addAssetState(AssetStateDTO.builder().asset(AssetDTO.builder().build()).build()));
        assertEquals("Asset id is required", e.getMessage());

        // =============================================================================================================
        // Fourth constraint test - Asset id is required.
        e = assertThrows(AssertionError.class, () -> assetStateService.addAssetState(AssetStateDTO.builder()
                .asset(AssetDTO.builder().assetId("TEST").build())
                .build()));
        assertEquals("Bitcoin transaction is required", e.getMessage());

        // =============================================================================================================
        // We create an asset state from scratch (The asset doesn't exist in database).
        int assetGroupCount = assetGroupRepository.findAll().size();
        int assetCount = assetRepository.findAll().size();
        int assetStateCount = assetStateRepository.findAll().size();

        final AssetStateDTO firstAssetStateCreated = assetStateService.addAssetState(AssetStateDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .asset(AssetDTO.builder()
                        .assetId("TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000")
                        .genesisPoint(bto.get())
                        .build())
                .anchorBlockHash("TEST_ANCHOR_BLOCK_HASH")
                .anchorOutpoint(bto.get())
                .anchorTx("TEST_ANCHOR_TX")
                .internalKey("TEST_INTERNAL_KEY")
                .merkleRoot("TEST_MERKLE_ROOT")
                .tapscriptSibling("TEST_TAPSCRIPT_SIBLING")
                .scriptVersion(0)
                .scriptKey("TEST_SCRIPT_KEY")
                .build());

        // We check what has been created.
        assertEquals(assetGroupCount, assetGroupRepository.findAll().size());
        assertEquals(assetCount + 1, assetRepository.findAll().size());
        assertEquals(assetStateCount + 1, assetStateRepository.findAll().size());

        // We check what was created.
        assertNotNull(firstAssetStateCreated.getId());
        // Asset state id is calculated from the asset state data.
        // TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000_ca8d2eb13b25fd0b363d92de2655988b49bc5b519f282d41e10ce117beb97558:0_TEST_SCRIPT_KEY
        assertEquals("cbac989304d353d8c8bbe7b19b1e1e352f317a7545e342584a30aa8875510e2f", firstAssetStateCreated.getAssetStateId());
        // User.
        assertNotNull(firstAssetStateCreated.getCreator());
        assertEquals(ANONYMOUS_USER_ID, firstAssetStateCreated.getCreator().getUserId());
        // Asset.
        assertNotNull(firstAssetStateCreated.getAsset());
        assertNotNull(firstAssetStateCreated.getAsset().getId());
        assertEquals("TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000", firstAssetStateCreated.getAsset().getAssetId());
        // Asset group.
        assertNull(firstAssetStateCreated.getAsset().getAssetGroup());
        // Asset state data.
        assertEquals("TEST_ANCHOR_BLOCK_HASH", firstAssetStateCreated.getAnchorBlockHash());
        assertEquals("ca8d2eb13b25fd0b363d92de2655988b49bc5b519f282d41e10ce117beb97558", firstAssetStateCreated.getAnchorOutpoint().getTxId());
        assertEquals(0, firstAssetStateCreated.getAnchorOutpoint().getVout());
        assertEquals("TEST_ANCHOR_TX", firstAssetStateCreated.getAnchorTx());
        assertEquals("TEST_INTERNAL_KEY", firstAssetStateCreated.getInternalKey());
        assertEquals("TEST_MERKLE_ROOT", firstAssetStateCreated.getMerkleRoot());
        assertEquals("TEST_TAPSCRIPT_SIBLING", firstAssetStateCreated.getTapscriptSibling());
        assertEquals(0, firstAssetStateCreated.getScriptVersion());
        assertEquals("TEST_SCRIPT_KEY", firstAssetStateCreated.getScriptKey());

        // Test if the asset exists in database.
        final Optional<AssetDTO> assetCreated = assetService.getAssetByAssetId("TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000");
        assertTrue(assetCreated.isPresent());
        assertNotNull(assetCreated.get().getId());

        // =============================================================================================================
        // We try to create the same asset state again.
        e = assertThrows(AssertionError.class, () -> assetStateService.addAssetState(AssetStateDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .asset(AssetDTO.builder()
                        .assetId("TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000")
                        .genesisPoint(bto.get())
                        .build())
                .anchorBlockHash("TEST_ANCHOR_BLOCK_HASH")
                .anchorOutpoint(bto.get())
                .anchorTx("TEST_ANCHOR_TX")
                .internalKey("TEST_INTERNAL_KEY")
                .merkleRoot("TEST_MERKLE_ROOT")
                .tapscriptSibling("TEST_TAPSCRIPT_SIBLING")
                .scriptVersion(0)
                .scriptKey("TEST_SCRIPT_KEY")
                .build()));
        assertEquals("Asset state already exists", e.getMessage());

        // We check that nothing has been created.
        assertEquals(assetGroupCount, assetGroupRepository.findAll().size());
        assertEquals(assetCount + 1, assetRepository.findAll().size());
        assertEquals(assetStateCount + 1, assetStateRepository.findAll().size());

        // =============================================================================================================
        // We create a second asset state from scratch (on the asset we created previously).
        assetStateService.addAssetState(AssetStateDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .asset(AssetDTO.builder()
                        .assetId("TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000")
                        .genesisPoint(bto.get())
                        .build())
                .anchorBlockHash("TEST_ANCHOR_BLOCK_HASH_2")
                .anchorOutpoint(bto.get())
                .anchorTx("TEST_ANCHOR_TX_2")
                .internalKey("TEST_INTERNAL_KEY_2")
                .merkleRoot("TEST_MERKLE_ROOT_2")
                .tapscriptSibling("TEST_TAPSCRIPT_SIBLING_2")
                .scriptVersion(1)
                .scriptKey("TEST_SCRIPT_KEY_2")
                .build());

        // We check what has been created.
        assertEquals(assetGroupCount, assetGroupRepository.findAll().size());
        assertEquals(assetCount + 1, assetRepository.findAll().size());
        assertEquals(assetStateCount + 2, assetStateRepository.findAll().size());

        // We check what was created (this time we used the getByAssetId() method).
        final Optional<AssetStateDTO> secondAssetStateCreated = assetStateService.getAssetStateByAssetStateId("941d2c356c616893f716376f79b820092e03f697c4b4eebf62f3ceb5cd01b72c");
        assertTrue(secondAssetStateCreated.isPresent());
        assertNotNull(secondAssetStateCreated.get().getId());
        // Asset state id is calculated from the asset state data.
        // TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000_ca8d2eb13b25fd0b363d92de2655988b49bc5b519f282d41e10ce117beb97558:0_TEST_SCRIPT_KEY_2
        assertEquals("941d2c356c616893f716376f79b820092e03f697c4b4eebf62f3ceb5cd01b72c", secondAssetStateCreated.get().getAssetStateId());
        // User.
        assertNotNull(secondAssetStateCreated.get().getCreator());
        assertEquals(ANONYMOUS_USER_ID, secondAssetStateCreated.get().getCreator().getUserId());
        // Asset.
        assertNotNull(secondAssetStateCreated.get().getAsset());
        assertNotNull(secondAssetStateCreated.get().getAsset().getId());
        assertEquals("TEST_COIN_ASSET_ID_000000000000000000000000000000000000000000000", secondAssetStateCreated.get().getAsset().getAssetId());
        // Asset group.
        assertNull(secondAssetStateCreated.get().getAsset().getAssetGroup());
        // Asset state data.
        assertEquals("TEST_ANCHOR_BLOCK_HASH_2", secondAssetStateCreated.get().getAnchorBlockHash());
        assertEquals("ca8d2eb13b25fd0b363d92de2655988b49bc5b519f282d41e10ce117beb97558", secondAssetStateCreated.get().getAnchorOutpoint().getTxId());
        assertEquals(0, secondAssetStateCreated.get().getAnchorOutpoint().getVout());
        assertEquals("TEST_ANCHOR_TX_2", secondAssetStateCreated.get().getAnchorTx());
        assertEquals("TEST_INTERNAL_KEY_2", secondAssetStateCreated.get().getInternalKey());
        assertEquals("TEST_MERKLE_ROOT_2", secondAssetStateCreated.get().getMerkleRoot());
        assertEquals("TEST_TAPSCRIPT_SIBLING_2", secondAssetStateCreated.get().getTapscriptSibling());
        assertEquals(1, secondAssetStateCreated.get().getScriptVersion());
        assertEquals("TEST_SCRIPT_KEY_2", secondAssetStateCreated.get().getScriptKey());
    }

    @Test
    @DisplayName("getAssetStateByAssetStateId()")
    public void getAssetStateByAssetStateId() {
        // =============================================================================================================
        // Non-existing asset group.
        Optional<AssetStateDTO> assetState = assetStateService.getAssetStateByAssetStateId("NON_EXISTING_ASSET_STATE_ID");
        assertFalse(assetState.isPresent());

        // =============================================================================================================
        // Existing asset state on testnet and in our database initialization script ("roylloCoin").
        assetState = assetStateService.getAssetStateByAssetStateId(ROYLLO_COIN_FROM_TEST.getDecodedProofResponse(0).getAsset().getAssetStateId());
        assertTrue(assetState.isPresent());
        assertEquals(1, assetState.get().getId());
        assertEquals(ROYLLO_COIN_FROM_TEST.getDecodedProofResponse(0).getAsset().getAssetStateId(), assetState.get().getAssetStateId());
        // User.
        assertNotNull(assetState.get().getCreator());
        assertEquals(ANONYMOUS_ID, assetState.get().getCreator().getId());
        // Asset & asset group.
        verifyAsset(assetState.get().getAsset(), ROYLLO_COIN_ASSET_ID);
        // Asset state data.
        verifyAssetState(assetState.get(),
                ROYLLO_COIN_ASSET_ID,
                assetState.get().getAnchorOutpoint().getTxId(),
                assetState.get().getAnchorOutpoint().getVout(),
                assetState.get().getScriptKey());
    }

    @Test
    @DisplayName("getAssetStatesByAssetId()")
    public void getAssetStatesByAssetId() {
        // Searching for asset states for an asset state id that doesn't exist.
        Page<AssetStateDTO> results = assetStateService.getAssetStatesByAssetId("NON_EXISTING_ASSET_STATE_ID", 1, 5);
        assertEquals(0, results.getTotalElements());
        assertEquals(0, results.getTotalPages());

        // Searching for the asset states of an existing asset.
        results = assetStateService.getAssetStatesByAssetId(TRICKY_ROYLLO_COIN_ASSET_ID, 1, 2);
        assertEquals(4, results.getTotalElements());
        assertEquals(2, results.getSize());
        assertEquals(2, results.getTotalPages());
    }

}
