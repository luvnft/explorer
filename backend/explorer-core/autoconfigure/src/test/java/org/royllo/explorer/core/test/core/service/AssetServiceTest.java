package org.royllo.explorer.core.test.core.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.dto.asset.AssetDTO;
import org.royllo.explorer.core.dto.asset.AssetGroupDTO;
import org.royllo.explorer.core.dto.bitcoin.BitcoinTransactionOutputDTO;
import org.royllo.explorer.core.repository.asset.AssetGroupRepository;
import org.royllo.explorer.core.service.asset.AssetService;
import org.royllo.explorer.core.service.bitcoin.BitcoinService;
import org.royllo.explorer.core.test.util.TestWithMockServers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static java.math.BigInteger.ONE;
import static java.util.Calendar.MARCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.royllo.explorer.core.provider.storage.LocalFileServiceImplementation.WEB_SERVER_HOST;
import static org.royllo.explorer.core.provider.storage.LocalFileServiceImplementation.WEB_SERVER_PORT;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_ID;
import static org.royllo.explorer.core.util.constants.AnonymousUserConstants.ANONYMOUS_USER_DTO;
import static org.royllo.explorer.core.util.constants.TaprootAssetsConstants.ASSET_ALIAS_LENGTH;
import static org.royllo.explorer.core.util.enums.AssetType.NORMAL;
import static org.royllo.explorer.core.util.enums.FileType.IMAGE;
import static org.royllo.explorer.core.util.enums.FileType.TEXT;
import static org.royllo.explorer.core.util.enums.FileType.UNKNOWN;
import static org.royllo.test.MempoolData.ROYLLO_COIN_GENESIS_TXID;
import static org.royllo.test.TapdData.ROYLLO_COIN_ASSET_ID;
import static org.royllo.test.TapdData.ROYLLO_NFT_ASSET_ID;
import static org.royllo.test.TapdData.ROYLLO_NFT_ASSET_ID_ALIAS;
import static org.royllo.test.TapdData.SET_OF_ROYLLO_NFT_1_FROM_TEST;
import static org.royllo.test.TapdData.SET_OF_ROYLLO_NFT_2_ASSET_ID;
import static org.royllo.test.TapdData.SET_OF_ROYLLO_NFT_2_ASSET_ID_ALIAS;

@SpringBootTest
@DirtiesContext
@DisplayName("AssetService tests")
public class AssetServiceTest extends TestWithMockServers {

    @Autowired
    private BitcoinService bitcoinService;

    @Autowired
    private AssetGroupRepository assetGroupRepository;

    @Autowired
    private AssetService assetService;

    @Test
    @DisplayName("addAsset()")
    public void addAsset() {
        // We retrieve a bitcoin transaction output from database for our test.
        final Optional<BitcoinTransactionOutputDTO> bto = bitcoinService.getBitcoinTransactionOutput(ROYLLO_COIN_GENESIS_TXID, 0);
        assertTrue(bto.isPresent());

        // =============================================================================================================
        // First test - Trying to save an existing asset.
        AssertionError e = assertThrows(AssertionError.class, () -> assetService.addAsset(AssetDTO.builder().id(1L).build()));
        assertEquals("Asset already exists", e.getMessage());

        // =============================================================================================================
        // Second test - Bitcoin transaction is null.
        e = assertThrows(AssertionError.class, () -> assetService.addAsset(AssetDTO.builder().build()));
        assertEquals("Bitcoin transaction is required", e.getMessage());

        // =============================================================================================================
        // Third test - AssetId is already in the database.
        e = assertThrows(AssertionError.class, () -> assetService.addAsset(AssetDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .assetId(ROYLLO_COIN_ASSET_ID)
                .genesisPoint(bto.get())
                .metaDataHash("metadata")
                .name("name")
                .outputIndex(0)
                .version(0)
                .type(NORMAL)
                .amount(ONE)
                .build()));
        assertTrue(e.getMessage().endsWith("already registered"));

        // =============================================================================================================
        // We add a first asset.
        // The bitcoin transaction output doesn't exist in database, the mock returns it and transaction is created.
        // There is no asset group, no one should be created.
        int assetGroupCount = assetGroupRepository.findAll().size();
        final AssetDTO asset1 = assetService.addAsset(AssetDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .assetId("my asset id")
                .genesisPoint(BitcoinTransactionOutputDTO.builder()
                        .txId(ROYLLO_COIN_GENESIS_TXID)
                        .vout(0)
                        .build())
                .metaDataHash("my meta data hash")
                .name("testCoin")
                .outputIndex(8)
                .version(0)
                .type(NORMAL)
                .amount(ONE)
                .build());

        // Testing asset value.
        assertNotNull(asset1.getId());
        assertEquals(ANONYMOUS_USER_DTO.getId(), asset1.getCreator().getId());
        assertEquals("my asset id", asset1.getAssetId());
        assertNotNull(asset1.getAssetIdAlias());
        assertEquals(ASSET_ALIAS_LENGTH, asset1.getAssetIdAlias().length());
        // Genesis.
        assertNotNull(asset1.getGenesisPoint());
        assertNotNull(asset1.getGenesisPoint().getId());
        verifyTransaction(asset1.getGenesisPoint(), ROYLLO_COIN_GENESIS_TXID);
        // Asset value data.
        assertEquals("my meta data hash", asset1.getMetaDataHash());
        assertEquals("testCoin", asset1.getName());
        assertEquals(8, asset1.getOutputIndex());
        assertEquals(0, asset1.getVersion());
        assertEquals(NORMAL, asset1.getType());
        assertEquals(0, asset1.getAmount().compareTo(ONE));
        // Asset group.
        assertNull(asset1.getAssetGroup());
        assertEquals(assetGroupCount, assetGroupRepository.findAll().size());

        // =============================================================================================================
        // We add a second asset.
        // Creator is not set.
        // The transaction already exists in database.
        // An asset group is set, but it doesn't exist in database for the moment.
        final AssetDTO asset2 = assetService.addAsset(AssetDTO.builder()
                .assetId("assetId2")
                .genesisPoint(bto.get())
                .metaDataHash("metaData2")
                .name("testCoin2")
                .outputIndex(9)
                .version(1)
                .type(NORMAL)
                .amount(new BigInteger("11"))
                .assetGroup(AssetGroupDTO.builder()
                        .assetGroupId("tweakedGroupKey-1")
                        .rawGroupKey("rawGroupKey-1")
                        .tweakedGroupKey("tweakedGroupKey-1")
                        .assetWitness("assetIdSig-1")
                        .build())
                .build());

        // Testing asset value.
        assertNotNull(asset2.getId());
        assertEquals(ANONYMOUS_USER_DTO.getId(), asset2.getCreator().getId());
        assertEquals("assetId2", asset2.getAssetId());
        assertNotNull(asset2.getAssetIdAlias());
        assertEquals(ASSET_ALIAS_LENGTH, asset2.getAssetIdAlias().length());
        // Genesis.
        assertNotNull(asset2.getGenesisPoint());
        assertNotNull(asset2.getGenesisPoint().getId());
        // Asset value data.
        assertEquals("metaData2", asset2.getMetaDataHash());
        assertEquals("testCoin2", asset2.getName());
        assertEquals(9, asset2.getOutputIndex());
        assertEquals(1, asset2.getVersion());
        assertEquals(NORMAL, asset2.getType());
        assertEquals(0, asset2.getAmount().compareTo(new BigInteger("11")));
        // Asset group.
        assertNotNull(asset2.getAssetGroup());
        assertEquals(assetGroupCount + 1, assetGroupRepository.findAll().size());
        assertNotNull(asset2.getAssetGroup().getId());
        assertEquals("tweakedGroupKey-1", asset2.getAssetGroup().getAssetGroupId());
        assertEquals("rawGroupKey-1", asset2.getAssetGroup().getRawGroupKey());
        assertEquals("tweakedGroupKey-1", asset2.getAssetGroup().getTweakedGroupKey());
        assertEquals("assetIdSig-1", asset2.getAssetGroup().getAssetWitness());

        // =============================================================================================================
        // We add a third asset.
        // The transaction already exists in database.
        // An asset group is set, but it already exists in the database.
        // We check that a new asset group is not created.
        final AssetDTO asset3 = assetService.addAsset(AssetDTO.builder()
                .assetId("assetId3")
                .genesisPoint(bto.get())
                .metaDataHash("metaData3")
                .name("testCoin3")
                .outputIndex(9)
                .version(1)
                .type(NORMAL)
                .amount(new BigInteger("111"))
                .assetGroup(AssetGroupDTO.builder()
                        .assetGroupId("tweakedGroupKey-1")
                        .tweakedGroupKey("tweakedGroupKey-1")
                        .rawGroupKey("rawGroupKey-1")
                        .assetWitness("assetIdSig-1")
                        .build())
                .build());
        // Asset group.
        assertNotNull(asset3.getAssetGroup());
        assertEquals(assetGroupCount + 1, assetGroupRepository.findAll().size());
        assertEquals("assetId3", asset3.getAssetId());
        assertNotNull(asset3.getAssetIdAlias());
        assertEquals(ASSET_ALIAS_LENGTH, asset3.getAssetIdAlias().length());
        assertEquals("tweakedGroupKey-1", asset3.getAssetGroup().getTweakedGroupKey());
        assertEquals("assetIdSig-1", asset3.getAssetGroup().getAssetWitness());
        assertEquals("rawGroupKey-1", asset3.getAssetGroup().getRawGroupKey());
        assertEquals("tweakedGroupKey-1", asset3.getAssetGroup().getTweakedGroupKey());
    }

    @Test
    @DisplayName("addAsset() with asset group")
    public void addAssetWithAssetGroup() {
        // We retrieve a bitcoin transaction output from database for our test.
        final Optional<BitcoinTransactionOutputDTO> bto = bitcoinService.getBitcoinTransactionOutput(ROYLLO_COIN_GENESIS_TXID, 0);
        assertTrue(bto.isPresent());

        // 4 assets : 1 with no asset group, 2 with the same asset group and 1 with another asset group.
        AssetDTO asset1 = AssetDTO.builder()
                .assetId("asset10000000000000000000000000000000000000000000000000000000000")
                .genesisPoint(bto.get())
                .build();
        AssetDTO asset2 = AssetDTO.builder()
                .assetId("asset20000000000000000000000000000000000000000000000000000000000")
                .genesisPoint(bto.get())
                .assetGroup(AssetGroupDTO.builder()
                        .assetGroupId("assetGroupId1")
                        .tweakedGroupKey("assetGroup1").build())
                .build();
        AssetDTO asset3 = AssetDTO.builder()
                .assetId("asset30000000000000000000000000000000000000000000000000000000000")
                .genesisPoint(bto.get())
                .assetGroup(AssetGroupDTO.builder()
                        .assetGroupId("assetGroupId1")
                        .tweakedGroupKey("assetGroup1").build())
                .build();
        AssetDTO asset4 = AssetDTO.builder()
                .assetId("asset40000000000000000000000000000000000000000000000000000000000")
                .genesisPoint(bto.get())
                .assetGroup(AssetGroupDTO.builder()
                        .assetGroupId("assetGroupId2")
                        .tweakedGroupKey("assetGroup2").build())
                .build();

        // Asset creation.
        assetService.addAsset(asset1);
        assetService.addAsset(asset2);
        assetService.addAsset(asset3);
        assetService.addAsset(asset4);

        // Asset retrieval.
        AssetDTO asset1Created = assetService.getAssetByAssetId("asset10000000000000000000000000000000000000000000000000000000000").orElse(null);
        AssetDTO asset2Created = assetService.getAssetByAssetId("asset20000000000000000000000000000000000000000000000000000000000").orElse(null);
        AssetDTO asset3Created = assetService.getAssetByAssetId("asset30000000000000000000000000000000000000000000000000000000000").orElse(null);
        AssetDTO asset4Created = assetService.getAssetByAssetId("asset40000000000000000000000000000000000000000000000000000000000").orElse(null);

        // Verification.
        assertNotNull(asset1Created);
        assertNotNull(asset1Created.getId());
        assertNull(asset1Created.getAssetGroup());

        assertNotNull(asset2Created);
        assertNotNull(asset2Created.getId());
        assertNotNull(asset2Created.getAssetGroup());
        assertNotNull(asset2Created.getAssetGroup().getId());
        assertEquals("assetGroup1", asset2Created.getAssetGroup().getTweakedGroupKey());

        assertNotNull(asset3Created);
        assertNotNull(asset3Created.getId());
        assertNotNull(asset3Created.getAssetGroup());
        assertNotNull(asset3Created.getAssetGroup().getId());
        assertEquals("assetGroup1", asset3Created.getAssetGroup().getTweakedGroupKey());

        assertNotNull(asset4Created);
        assertNotNull(asset4Created.getId());
        assertNotNull(asset4Created.getAssetGroup());
        assertNotNull(asset4Created.getAssetGroup().getId());
        assertEquals("assetGroup2", asset4Created.getAssetGroup().getTweakedGroupKey());
    }

    @Test
    @DisplayName("updateAsset()")
    public void updateAsset() {
        // Date used for test.
        LocalDate localDate = LocalDate.of(2019, MARCH, 12);
        LocalTime localTime = LocalTime.of(12, 44);
        ZoneId zoneId = ZoneId.of("GMT+05:30");
        ZonedDateTime testDate = ZonedDateTime.of(localDate, localTime, zoneId);

        // We create an asset.
        final AssetDTO asset1 = assetService.addAsset(AssetDTO.builder()
                .creator(ANONYMOUS_USER_DTO)
                .assetId("asset00000000000000000000000000000000000000000000000000000000001")
                .genesisPoint(BitcoinTransactionOutputDTO.builder()
                        .txId(ROYLLO_COIN_GENESIS_TXID)
                        .vout(0)
                        .build())
                .metaDataHash("my meta data hash")
                .name("testCoin")
                .outputIndex(8)
                .version(0)
                .type(NORMAL)
                .amount(ONE)
                .build());

        // We test the data of our created asset.
        assertNotNull(asset1.getId());
        assertNotNull(asset1.getAssetId());
        assertNull(asset1.getMetaDataFileName());
        assertEquals(0, ONE.compareTo(asset1.getAmount()));
        assertNull(asset1.getIssuanceDate());

        // Now, we update the asset with the metadata, the new amount, the new creation date.
        final String imageMeta = "89504e470d0a1a0a0000000d4948445200000018000000180806000000e0773df80000006e4944415478da63601805a360d880ff38304d0da78a25ff89c4941b5e682f8b81a9660136c3d12ca1cc029b0c75304636182646ae0528ae07d11ef9da182e078951c5025a04d1c059802e36b47c704451008e29cd07ff89f105552dc011f6d4f50135cb229c41448b229ba25403006cf5f5c3b61b973a0000000049454e44ae426082";
        assetService.updateAsset(asset1.getAssetId(), imageMeta, new BigInteger("100"), testDate);

        // We test the data.
        Optional<AssetDTO> assetUpdated = assetService.getAssetByAssetId(asset1.getAssetId());
        assertTrue(assetUpdated.isPresent());
        assertEquals(asset1.getAssetId() + ".png", assetUpdated.get().getMetaDataFileName());
        assertEquals(0, new BigInteger("100").compareTo(assetUpdated.get().getAmount()));
        assertTrue(testDate.isEqual(assetUpdated.get().getIssuanceDate()));

        // The file should be available.
        var client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://" + WEB_SERVER_HOST + ":" + WEB_SERVER_PORT + "/" + assetUpdated.get().getMetaDataFileName())
                .build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
        } catch (IOException e) {
            fail("Error while retrieving the file" + e.getMessage());
        }

        // We test that it gives a 404 error if you search for a non-existing file.
        request = new Request.Builder()
                .url("http://" + WEB_SERVER_HOST + ":" + WEB_SERVER_PORT + "/NON_EXISTING_FILE.png")
                .build();
        try (Response response = client.newCall(request).execute()) {
            assertEquals(404, response.code());
        } catch (IOException e) {
            fail("Error while retrieving the file" + e.getMessage());
        }

        // We update with nothing, nothing should change.
        assetService.updateAsset(asset1.getAssetId(), null, null, null);

        // We test the data.
        assetUpdated = assetService.getAssetByAssetId(asset1.getAssetId());
        assertTrue(assetUpdated.isPresent());
        assertEquals(asset1.getAssetId() + ".png", assetUpdated.get().getMetaDataFileName());
        assertEquals(0, new BigInteger("100").compareTo(assetUpdated.get().getAmount()));
        assertTrue(testDate.isEqual(assetUpdated.get().getIssuanceDate()));
    }

    @Test
    @DisplayName("getAsset()")
    public void getAsset() {
        // =============================================================================================================
        // Non-existing asset.
        Optional<AssetDTO> asset = assetService.getAsset(0);
        assertFalse(asset.isPresent());

        // =============================================================================================================
        // Existing asset on testnet and in our database initialization script ("My Royllo coin") .
        // Asset id is 1 as My Royllo Coin is the only coin inserted in default database.
        asset = assetService.getAsset(1);
        assertTrue(asset.isPresent());
        assertEquals(ROYLLO_COIN_ASSET_ID, asset.get().getAssetId());
        assertEquals("roylloCoin", asset.get().getAssetIdAlias());
        assertNotNull(asset.get().getCreator());
        assertEquals(ANONYMOUS_ID, asset.get().getCreator().getId());
        verifyAsset(asset.get(), ROYLLO_COIN_ASSET_ID);

        // getAsset() on an asset that has no asset group
        asset = assetService.getAsset(1);
        assertTrue(asset.isPresent());
        assertNull(asset.get().getAssetGroup());
    }

    @Test
    @DisplayName("getAssetByAssetId()")
    public void getAssetByAssetId() {
        // =============================================================================================================
        // Non-existing asset.
        Optional<AssetDTO> asset = assetService.getAssetByAssetId("NON_EXISTING_ASSET_ID");
        assertFalse(asset.isPresent());

        // =============================================================================================================
        // Existing asset on testnet and in our database initialization script ("roylloCoin") .
        asset = assetService.getAsset(1);
        assertTrue(asset.isPresent());
        assertEquals(ROYLLO_COIN_ASSET_ID, asset.get().getAssetId());
        assertNotNull(asset.get().getCreator());
        assertEquals(ANONYMOUS_ID, asset.get().getCreator().getId());
        verifyAsset(asset.get(), ROYLLO_COIN_ASSET_ID);
        assertEquals(TEXT, asset.get().getMetaDataFileType());

        // getAsset() on an asset that has no asset group
        asset = assetService.getAsset(1);
        assertTrue(asset.isPresent());
        assertNull(asset.get().getAssetGroup());

        // Testing another asset in test data.
        asset = assetService.getAssetByAssetId(ROYLLO_NFT_ASSET_ID);
        assertTrue(asset.isPresent());
        assertNotNull(asset.get().getAssetId());
        assertEquals(ROYLLO_NFT_ASSET_ID_ALIAS, asset.get().getAssetIdAlias());
        assertEquals(IMAGE, asset.get().getMetaDataFileType());

        // Testing with an asset id alias
        asset = assetService.getAssetByAssetId(SET_OF_ROYLLO_NFT_2_ASSET_ID_ALIAS);
        assertTrue(asset.isPresent());
        assertNotNull(asset.get().getAssetId());
        assertEquals(SET_OF_ROYLLO_NFT_2_ASSET_ID, asset.get().getAssetId());
        assertEquals(SET_OF_ROYLLO_NFT_2_ASSET_ID_ALIAS, asset.get().getAssetIdAlias());
        assertEquals(UNKNOWN, asset.get().getMetaDataFileType());
    }

    @Test
    @DisplayName("getAssetByAssetId()")
    public void getAssetsByAssetGroupId() {
        // Test with an asset group that doesn't exist.
        assertEquals(0, assetService.getAssetsByAssetGroupId("NON_EXISTING_ASSET_GROUP_ID", 1, 5).getTotalElements());

        // Test with an asset group with three assets.
        final String tweakedGroupKey = SET_OF_ROYLLO_NFT_1_FROM_TEST.getDecodedProofResponse(0).getAsset().getAssetGroup().getTweakedGroupKey();
        // with a page size containing everything.
        assertEquals(3, assetService.getAssetsByAssetGroupId(tweakedGroupKey, 1, 5).getTotalElements());
        assertEquals(1, assetService.getAssetsByAssetGroupId(tweakedGroupKey, 1, 5).getTotalPages());
    }

}
