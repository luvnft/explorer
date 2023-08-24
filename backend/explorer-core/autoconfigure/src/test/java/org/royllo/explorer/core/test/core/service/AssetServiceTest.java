package org.royllo.explorer.core.test.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.dto.asset.AssetDTO;
import org.royllo.explorer.core.dto.bitcoin.BitcoinTransactionOutputDTO;
import org.royllo.explorer.core.service.asset.AssetService;
import org.royllo.explorer.core.service.bitcoin.BitcoinService;
import org.royllo.explorer.core.test.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("mempoolTransactionServiceMock")
@DisplayName("AssetService tests")
public class AssetServiceTest extends BaseTest {

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private AssetService assetService;

    @Test
    @DisplayName("queryAssets()")
    public void queryAssets() {
        // Searching for an asset that doesn't exist.
        Page<AssetDTO> results = assetService.queryAssets("NON-EXISTING", 1, 5);
        assertEquals(0, results.getTotalElements());
        assertEquals(0, results.getTotalPages());

        // Searching for an asset with its asset id.
        results = assetService.queryAssets(ROYLLO_COIN_ASSET_ID, 1, 5);
        assertEquals(1, results.getTotalElements());
        assertEquals(1, results.getTotalPages());
        assertEquals(1, results.getContent().get(0).getId());

        // Searching for an asset with its partial name (activeRoylloCoin) - only 1 result.
        results = assetService.queryAssets("veR", 1, 5);
        assertEquals(1, results.getTotalElements());
        assertEquals(1, results.getTotalPages());
        assertEquals(ACTIVE_ROYLLO_COIN_ID, results.getContent().get(0).getId());

        // Searching for an asset with its partial name uppercase (activeRoylloCoin) - only 1 result.
        results = assetService.queryAssets("VER", 1, 5);
        assertEquals(1, results.getTotalElements());
        assertEquals(1, results.getTotalPages());
        assertEquals(ACTIVE_ROYLLO_COIN_ID, results.getContent().get(0).getId());

        // Searching for an asset with its partial name corresponding to two assets.
        // The last coin we insert in database has "TestPaginationCoin0" as name, so it appears first in results.
        results = assetService.queryAssets("PaginationCoin", 1, 5);
        assertEquals(9, results.getTotalElements());
        assertEquals(2, results.getTotalPages());
        Set<Long> ids = results.stream()
                .map(AssetDTO::getId)
                .collect(Collectors.toSet());
        assertTrue(ids.contains(1009L));
        assertTrue(ids.contains(1001L));
        assertTrue(ids.contains(1002L));
        assertTrue(ids.contains(1003L));
        assertTrue(ids.contains(1004L));

        // We have 9 assets to tests pagination.

        // Searching for the 9 assets with a page size of 4.
        results = assetService.queryAssets("TestPaginationCoin", 1, 4);
        assertEquals(9, results.getTotalElements());
        assertEquals(3, results.getTotalPages());

        // Searching for the 9 assets with a page size of 5 - Page 0.
        results = assetService.queryAssets("TestPaginationCoin", 1, 5);
        assertEquals(5, results.getNumberOfElements());
        assertEquals(9, results.getTotalElements());
        assertEquals(2, results.getTotalPages());
        assertEquals(1009, results.getContent().get(0).getId());
        assertEquals(1001, results.getContent().get(1).getId());
        assertEquals(1002, results.getContent().get(2).getId());
        assertEquals(1003, results.getContent().get(3).getId());
        assertEquals(1004, results.getContent().get(4).getId());

        // Searching for the 9 assets with a page size of 5 - Page 1.
        results = assetService.queryAssets("TestPaginationCoin", 2, 5);
        assertEquals(4, results.getNumberOfElements());
        assertEquals(9, results.getTotalElements());
        assertEquals(2, results.getTotalPages());
        assertEquals(1005, results.getContent().get(0).getId());
        assertEquals(1006, results.getContent().get(1).getId());
        assertEquals(1007, results.getContent().get(2).getId());
        assertEquals(1008, results.getContent().get(3).getId());
    }

    @Test
    @DisplayName("addAsset()")
    public void addAsset() {
        // =============================================================================================================
        // First test - Trying to save an existing asset.
        try {
            assetService.addAsset(AssetDTO.builder()
                    .id(1L)
                    .build());
        } catch (AssertionError e) {
            assertEquals("Asset already exists", e.getMessage());
        }

        // =============================================================================================================
        // Second test - Bitcoin transaction is null.
        try {
            assetService.addAsset(AssetDTO.builder()
                    .build());
        } catch (AssertionError e) {
            assertEquals("Bitcoin transaction is required", e.getMessage());
        }

        // =============================================================================================================
        // Third test - AssetId is already in the database.
        final Optional<BitcoinTransactionOutputDTO> bto = bitcoinService.getBitcoinTransactionOutput(BITCOIN_TRANSACTION_1_TXID, 0);
        assertTrue(bto.isPresent());

/*        // TODO Make this test work.
        try {
            assetService.addAssetDTO(AssetDTO.builder()
                    .creator(ANONYMOUS_USER_DTO)
                    .version(0)
                    .genesisPoint(bto.get())
                    .name("name")
                    .metaDataHash("metadata")
                    .assetId("b34b05956d828a7f7a0df598771c9f6df0378680c432480837852bcb94a8f21e")
                    .outputIndex(0)
                    .genesisVersion(0)
                    .type(NORMAL)
                    .amount(new BigInteger("1"))
                    .lockTime(0)
                    .relativeLockTime(0)
                    .scriptVersion(0)
                    .scriptKey("Script key")
                    .anchorTx("Anchor tx")
                    .anchorTxId("Anchor tx id")
                    .anchorBlockHash("Anchor block hash")
                    .anchorOutpoint("Anchor outpoint")
                    .internalKey("Anchor internal key")
                    .build());
        } catch (AssertionError e) {
            assertTrue(e.getMessage().endsWith("already registered"));
        }

        // =============================================================================================================
        // Now adding a real asset - WE DIT NOT SEARCH FOR THE TRANSACTION OUTPUT IN DATABASE.
        final AssetDTO asset1 = assetService.addAssetDTO(AssetDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .version(0)
                // Genesis.
                .genesisPoint(BitcoinTransactionOutputDTO.builder()
                        .txId(BITCOIN_TRANSACTION_3_TXID)
                        .vout(0)
                        .build())
                .name("testCoin")
                .metaDataHash("my meta data hash")
                .assetId("my asset id")
                .outputIndex(8)
                .genesisVersion(1)
                // Other.
                .type(NORMAL)
                .amount(new BigInteger("1"))
                .lockTime(2)
                .relativeLockTime(3)
                .scriptVersion(4)
                .scriptKey("Script key")
                // Asset group.
                .rawGroupKey("Raw group key")
                .tweakedGroupKey("Tweaked group key")
                .assetIdSig("Asset id sig")
                // Anchor.
                .anchorTx("Anchor tx")
                .anchorTxId("Anchor tx id")
                .anchorBlockHash("Anchor block hash")
                .anchorOutpoint("Anchor outpoint")
                .internalKey("Anchor internal key")
                .merkleRoot("Merkle root")
                .tapscriptSibling("Tapscript sibling")
                .build());

        // Testing asset value.
        assertNotNull(asset1.getId());
        assertEquals(ANONYMOUS_USER_DTO.getId(), asset1.getCreator().getId());
        assertEquals(0, asset1.getVersion());

        // Genesis.
        assertNotNull(asset1.getGenesisPoint());
        assertNotNull(asset1.getGenesisPoint().getId());
        assertEquals(BITCOIN_TRANSACTION_3_TXID, asset1.getGenesisPoint().getTxId());
        assertEquals(0, asset1.getGenesisPoint().getVout());
        assertEquals("76a9140aa7e954ae2c972225309f0992e3ecd698a90f5f88ac", asset1.getGenesisPoint().getScriptPubKey());
        assertEquals("OP_DUP OP_HASH160 OP_PUSHBYTES_20 0aa7e954ae2c972225309f0992e3ecd698a90f5f OP_EQUALVERIFY OP_CHECKSIG", asset1.getGenesisPoint().getScriptPubKeyAsm());
        assertEquals("p2pkh", asset1.getGenesisPoint().getScriptPubKeyType());
        assertEquals("1yLucPwZwVuNxMFTXyyX5qTk3YFNpAGEz", asset1.getGenesisPoint().getScriptPubKeyAddress());
        assertEquals(0, new BigDecimal("2036308").compareTo(asset1.getGenesisPoint().getValue()));

        assertEquals("testCoin", asset1.getName());
        assertEquals("my meta data hash", asset1.getMetaDataHash());
        assertEquals("my asset id", asset1.getAssetId());
        assertEquals(8, asset1.getOutputIndex());
        assertEquals(1, asset1.getGenesisVersion());

        assertEquals(NORMAL, asset1.getType());
        assertEquals(0, asset1.getAmount().compareTo(new BigInteger("1")));
        assertEquals(2, asset1.getLockTime());
        assertEquals(3, asset1.getRelativeLockTime());

        assertEquals(4, asset1.getScriptVersion());
        assertEquals("Script key", asset1.getScriptKey());

        // Asset group.
        assertEquals("Raw group key", asset1.getRawGroupKey());
        assertEquals("Tweaked group key", asset1.getTweakedGroupKey());
        assertEquals("Asset id sig", asset1.getAssetIdSig());

        // Anchor.
        assertEquals("Anchor tx", asset1.getAnchorTx());
        assertEquals("Anchor tx id", asset1.getAnchorTxId());
        assertEquals("Anchor block hash", asset1.getAnchorBlockHash());
        assertEquals("Anchor outpoint", asset1.getAnchorOutpoint());
        assertEquals("Anchor internal key", asset1.getInternalKey());
        assertEquals("Merkle root", asset1.getMerkleRoot());
        assertEquals("Tapscript sibling", asset1.getTapscriptSibling());

        // =============================================================================================================
        // Now adding a real asset with an existing transaction output.
        final AssetDTO asset2 = assetService.addAssetDTO(AssetDTO.builder()
                .genesisPoint(bto.get())
                .name("testCoin2")
                .metaDataHash("metaData2")
                .assetId("assetId2")
                .outputIndex(9)
                .genesisVersion(0)
                .type(NORMAL)
                .amount(new BigInteger("1"))
                .lockTime(0)
                .relativeLockTime(0)
                .scriptVersion(0)
                .scriptKey("Script key")
                .anchorTx("Anchor tx")
                .anchorTxId("Anchor tx id")
                .anchorBlockHash("Anchor block hash")
                .anchorOutpoint("Anchor outpoint")
                .internalKey("Anchor internal key")
                .build());

        // Testing asset value.
        assertNotNull(asset2.getId());
        assertEquals(5, asset2.getGenesisPoint().getId());*/
    }

    @Test
    @DisplayName("getAsset()")
    public void getAsset() {
        // Non-existing asset.
        Optional<AssetDTO> asset = assetService.getAsset(0);
        assertFalse(asset.isPresent());

        // Existing asset on testnet and in our database initialization script ("My Royllo coin") .
        asset = assetService.getAsset(ROYLLO_COIN_ID);
        assertTrue(asset.isPresent());
        assertEquals(ROYLLO_COIN_ID, asset.get().getId());
        assertEquals(ROYLLO_COIN_VERSION, asset.get().getVersion());

        // Genesis point.
/*        // TODO Make this test work.
        assertEquals(ROYLLO_COIN_GENESIS_POINT_TXID, asset.get().getGenesisPoint().getTxId());
        assertEquals(ROYLLO_COIN_GENESIS_POINT_VOUT, asset.get().getGenesisPoint().getVout());
        assertEquals(ROYLLO_COIN_NAME, asset.get().getName());
        assertEquals(ROYLLO_COIN_META_DATA_HASH, asset.get().getMetaDataHash());
        assertEquals(ROYLLO_COIN_ASSET_ID, asset.get().getAssetId());
        assertEquals(ROYLLO_COIN_OUTPUT_INDEX, asset.get().getOutputIndex());
        assertEquals(ROYLLO_COIN_GENESIS_VERSION, asset.get().getGenesisVersion());

        assertEquals(ROYLLO_COIN_ASSET_TYPE, asset.get().getType());
        assertEquals(0, ROYLLO_COIN_AMOUNT.compareTo(asset.get().getAmount()));
        assertEquals(ROYLLO_COIN_LOCK_TIME, asset.get().getLockTime());
        assertEquals(ROYLLO_COIN_RELATIVE_LOCK_TIME, asset.get().getRelativeLockTime());

        assertEquals(ROYLLO_COIN_SCRIPT_KEY, asset.get().getScriptKey());
        assertEquals(ROYLLO_COIN_SCRIPT_VERSION, asset.get().getScriptVersion());

        // Asset group.
        assertEquals(ROYLLO_COIN_RAW_GROUP_KEY, asset.get().getRawGroupKey());
        assertEquals(ROYLLO_COIN_TWEAKED_GROUP_KEY, asset.get().getTweakedGroupKey());
        assertEquals(ROYLLO_COIN_ASSET_ID_SIG, asset.get().getAssetIdSig());

        // Chain anchor.
        assertEquals(ROYLLO_COIN_ANCHOR_TX, asset.get().getAnchorTx());
        assertEquals(ROYLLO_COIN_ANCHOR_TX_ID, asset.get().getAnchorTxId());
        assertEquals(ROYLLO_COIN_ANCHOR_BLOCK_HASH, asset.get().getAnchorBlockHash());
        assertEquals(ROYLLO_COIN_ANCHOR_OUTPOINT, asset.get().getAnchorOutpoint());
        assertEquals(ROYLLO_COIN_INTERNAL_KEY, asset.get().getInternalKey());
        assertEquals(ROYLLO_COIN_MERKLE_ROOT, asset.get().getMerkleRoot());
        assertEquals(ROYLLO_COIN_TAPSCRIPT_SIBLING, asset.get().getTapscriptSibling());*/
    }

    @Test
    @DisplayName("getAssetByAssetId()")
    public void getAssetByAssetId() {
        // Non-existing asset.
        Optional<AssetDTO> asset = assetService.getAssetByAssetId("NON-EXISTING");
        assertFalse(asset.isPresent());

        // Existing asset on testnet and in our database initialization script ("roylloCoin") .
        asset = assetService.getAssetByAssetId(ROYLLO_COIN_ASSET_ID);
        assertTrue(asset.isPresent());
        assertEquals(ROYLLO_COIN_ID, asset.get().getId());
        assertEquals(ROYLLO_COIN_VERSION, asset.get().getVersion());

/*        // Genesis point.
        // TODO Make this test work.
        assertEquals(ROYLLO_COIN_GENESIS_POINT_TXID, asset.get().getGenesisPoint().getTxId());
        assertEquals(ROYLLO_COIN_GENESIS_POINT_VOUT, asset.get().getGenesisPoint().getVout());
        assertEquals(ROYLLO_COIN_NAME, asset.get().getName());
        assertEquals(ROYLLO_COIN_META_DATA_HASH, asset.get().getMetaDataHash());
        assertEquals(ROYLLO_COIN_ASSET_ID, asset.get().getAssetId());
        assertEquals(ROYLLO_COIN_OUTPUT_INDEX, asset.get().getOutputIndex());
        assertEquals(ROYLLO_COIN_GENESIS_VERSION, asset.get().getGenesisVersion());

        assertEquals(ROYLLO_COIN_ASSET_TYPE, asset.get().getType());
        assertEquals(0, ROYLLO_COIN_AMOUNT.compareTo(asset.get().getAmount()));
        assertEquals(ROYLLO_COIN_LOCK_TIME, asset.get().getLockTime());
        assertEquals(ROYLLO_COIN_RELATIVE_LOCK_TIME, asset.get().getRelativeLockTime());

        assertEquals(ROYLLO_COIN_SCRIPT_KEY, asset.get().getScriptKey());
        assertEquals(ROYLLO_COIN_SCRIPT_VERSION, asset.get().getScriptVersion());

        // Asset group.
        assertEquals(ROYLLO_COIN_RAW_GROUP_KEY, asset.get().getRawGroupKey());
        assertEquals(ROYLLO_COIN_TWEAKED_GROUP_KEY, asset.get().getTweakedGroupKey());
        assertEquals(ROYLLO_COIN_ASSET_ID_SIG, asset.get().getAssetIdSig());

        // Chain anchor.
        assertEquals(ROYLLO_COIN_ANCHOR_TX, asset.get().getAnchorTx());
        assertEquals(ROYLLO_COIN_ANCHOR_TX_ID, asset.get().getAnchorTxId());
        assertEquals(ROYLLO_COIN_ANCHOR_BLOCK_HASH, asset.get().getAnchorBlockHash());
        assertEquals(ROYLLO_COIN_ANCHOR_OUTPOINT, asset.get().getAnchorOutpoint());
        assertEquals(ROYLLO_COIN_INTERNAL_KEY, asset.get().getInternalKey());
        assertEquals(ROYLLO_COIN_MERKLE_ROOT, asset.get().getMerkleRoot());
        assertEquals(ROYLLO_COIN_TAPSCRIPT_SIBLING, asset.get().getTapscriptSibling());*/
    }

}
