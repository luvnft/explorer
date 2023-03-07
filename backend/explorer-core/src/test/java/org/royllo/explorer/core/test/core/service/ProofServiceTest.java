package org.royllo.explorer.core.test.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.royllo.explorer.core.dto.asset.AssetDTO;
import org.royllo.explorer.core.dto.proof.ProofDTO;
import org.royllo.explorer.core.provider.mempool.MempoolTransactionService;
import org.royllo.explorer.core.provider.tarod.DecodedProofResponse;
import org.royllo.explorer.core.provider.tarod.TarodProofService;
import org.royllo.explorer.core.service.asset.AssetService;
import org.royllo.explorer.core.service.proof.ProofService;
import org.royllo.explorer.core.test.util.BaseTest;
import org.royllo.explorer.core.util.exceptions.proof.ProofCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.royllo.explorer.core.util.constants.UserConstants.ANONYMOUS_ID;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ActiveProfiles({"tarodProofServiceMock", "mempoolTransactionServiceMock"})
@DisplayName("ProofService tests")
public class ProofServiceTest extends BaseTest {

    @Autowired
    private MempoolTransactionService mempoolTransactionService;

    @Autowired
    private TarodProofService tarodProofService;

    @Autowired
    private ProofService proofService;

    @Autowired
    private AssetService assetService;

    @Test
    @DisplayName("addProof()")
    public void addProof() {
        // =============================================================================================================
        // Unknown Royllo coin.
        DecodedProofResponse unknownRoylloCoinDecodedProof = tarodProofService.decode(UNKNOWN_ROYLLO_COIN_RAW_PROOF, 0).block();
        assertNotNull(unknownRoylloCoinDecodedProof);

        // We add our proof but our an asset doesn't exist yet --> an error must occur.
        assertFalse(assetService.getAssetByAssetId(UNKNOWN_ROYLLO_COIN_ASSET_ID).isPresent());
        try {
            proofService.addProof(UNKNOWN_ROYLLO_COIN_RAW_PROOF, 0, unknownRoylloCoinDecodedProof);
            fail("An exception should have occurred");
        } catch (ProofCreationException e) {
            assertEquals(e.getMessage(), "Asset " + UNKNOWN_ROYLLO_COIN_ASSET_ID + " is not registered in our database");
        }

        // We add the asset of our proof, and then, our proof --> No error, proof should be added.
        final AssetDTO unknownRoylloCoin = assetService.addAsset(ASSET_MAPPER.mapToAssetDTO(unknownRoylloCoinDecodedProof.getDecodedProof()));
        assertNotNull(unknownRoylloCoin);
        assertNotNull(unknownRoylloCoin.getId());
        assertNotNull(unknownRoylloCoin.getCreator());
        assertEquals(0, unknownRoylloCoin.getCreator().getId());
        assertEquals(0, unknownRoylloCoin.getVersion());
        assertEquals(UNKNOWN_ROYLLO_COIN_GENESIS_POINT_TXID, unknownRoylloCoin.getGenesisPoint().getTxId());
        assertEquals(UNKNOWN_ROYLLO_COIN_GENESIS_POINT_VOUT, unknownRoylloCoin.getGenesisPoint().getVout());
        assertEquals(UNKNOWN_ROYLLO_COIN_NAME, unknownRoylloCoin.getName());
        assertEquals(UNKNOWN_ROYLLO_COIN_META, unknownRoylloCoin.getMetaData());
        assertEquals(UNKNOWN_ROYLLO_COIN_ASSET_ID, unknownRoylloCoin.getAssetId());
        assertEquals(UNKNOWN_ROYLLO_COIN_OUTPUT_INDEX, unknownRoylloCoin.getOutputIndex());
        assertEquals(UNKNOWN_ROYLLO_COIN_GENESIS_BOOTSTRAP_INFORMATION, unknownRoylloCoin.getGenesisBootstrapInformation());
        assertEquals(UNKNOWN_ROYLLO_COIN_GENESIS_VERSION, unknownRoylloCoin.getGenesisVersion());
        assertEquals(UNKNOWN_ROYLLO_COIN_ASSET_TYPE, unknownRoylloCoin.getType());
        assertEquals(0, unknownRoylloCoin.getAmount().compareTo(UNKNOWN_ROYLLO_COIN_AMOUNT));
        assertEquals(UNKNOWN_ROYLLO_COIN_LOCK_TIME, unknownRoylloCoin.getLockTime());
        assertEquals(UNKNOWN_ROYLLO_COIN_RELATIVE_LOCK_TIME, unknownRoylloCoin.getRelativeLockTime());
        assertEquals(UNKNOWN_ROYLLO_COIN_SCRIPT_VERSION, unknownRoylloCoin.getScriptVersion());
        assertEquals(UNKNOWN_ROYLLO_COIN_SCRIPT_KEY, unknownRoylloCoin.getScriptKey());
        assertEquals(UNKNOWN_ROYLLO_COIN_ANCHOR_TX, unknownRoylloCoin.getAnchorTx());
        assertEquals(UNKNOWN_ROYLLO_COIN_ANCHOR_TX_ID, unknownRoylloCoin.getAnchorTxId());
        assertEquals(UNKNOWN_ROYLLO_COIN_ANCHOR_BLOCK_HASH, unknownRoylloCoin.getAnchorBlockHash());
        assertEquals(UNKNOWN_ROYLLO_COIN_ANCHOR_OUTPOINT, unknownRoylloCoin.getAnchorOutpoint());
        assertEquals(UNKNOWN_ROYLLO_COIN_ANCHOR_INTERNAL_KEY, unknownRoylloCoin.getAnchorInternalKey());
        assertTrue(assetService.getAssetByAssetId(UNKNOWN_ROYLLO_COIN_ASSET_ID).isPresent());

        // Then, our proof that should be added without any problem.
        final ProofDTO proofAdded = proofService.addProof(UNKNOWN_ROYLLO_COIN_RAW_PROOF, 0, unknownRoylloCoinDecodedProof);
        assertNotNull(proofAdded);
        assertNotNull(proofAdded.getId());
        assertEquals("8d8176924f625ba627689608bc0f5e73ea233e471e78e1d333dee4c4cee7d623", proofAdded.getProofId());
        assertEquals(UNKNOWN_ROYLLO_COIN_ASSET_ID, proofAdded.getAsset().getAssetId());
        assertEquals(ANONYMOUS_ID, proofAdded.getCreator().getId());
        assertEquals(UNKNOWN_ROYLLO_COIN_RAW_PROOF, proofAdded.getRawProof());
        assertEquals(0, proofAdded.getProofIndex());
        assertEquals(UNKNOWN_ROYLLO_COIN_TX_MERKLE_PROOF, proofAdded.getTxMerkleProof());
        assertEquals(UNKNOWN_ROYLLO_COIN_INCLUSION_PROOF, proofAdded.getInclusionProof());
        assertEquals(0, proofAdded.getExclusionProofs().size());

        // We add again our proof as it's already in our database --> an error must occur.
        try {
            proofService.addProof(UNKNOWN_ROYLLO_COIN_RAW_PROOF, 0, unknownRoylloCoinDecodedProof);
            fail("An exception should have occurred");
        } catch (ProofCreationException e) {
            assertEquals(e.getMessage(), "This proof is already registered with proof id: 8d8176924f625ba627689608bc0f5e73ea233e471e78e1d333dee4c4cee7d623");
        }
    }

    @Test
    @DisplayName("getProofByProofId()")
    public void getProofByProofId() {
        final Optional<ProofDTO> myRoylloCoinProof = proofService.getProofByProofId("986990b5cf583d5a9e2f86da9e3df7f191b8000a42b811728f2efd1d9371653f");
        assertTrue(myRoylloCoinProof.isPresent());

        // Checking asset
        assertEquals(MY_ROYLLO_COIN_ID, myRoylloCoinProof.get().getAsset().getId());
        assertEquals(MY_ROYLLO_COIN_VERSION, myRoylloCoinProof.get().getAsset().getVersion());
        // Genesis point.
        assertEquals(MY_ROYLLO_COIN_GENESIS_POINT_TXID, myRoylloCoinProof.get().getAsset().getGenesisPoint().getTxId());
        assertEquals(MY_ROYLLO_COIN_GENESIS_POINT_VOUT, myRoylloCoinProof.get().getAsset().getGenesisPoint().getVout());
        assertEquals(MY_ROYLLO_COIN_NAME, myRoylloCoinProof.get().getAsset().getName());
        assertEquals(MY_ROYLLO_COIN_META, myRoylloCoinProof.get().getAsset().getMetaData());
        assertEquals(MY_ROYLLO_COIN_ASSET_ID, myRoylloCoinProof.get().getAsset().getAssetId());
        assertEquals(MY_ROYLLO_COIN_OUTPUT_INDEX, myRoylloCoinProof.get().getAsset().getOutputIndex());
        assertEquals(MY_ROYLLO_COIN_GENESIS_BOOTSTRAP_INFORMATION, myRoylloCoinProof.get().getAsset().getGenesisBootstrapInformation());
        assertEquals(MY_ROYLLO_COIN_GENESIS_VERSION, myRoylloCoinProof.get().getAsset().getGenesisVersion());
        assertEquals(MY_ROYLLO_COIN_ASSET_TYPE, myRoylloCoinProof.get().getAsset().getType());
        assertEquals(0, MY_ROYLLO_COIN_AMOUNT.compareTo(myRoylloCoinProof.get().getAsset().getAmount()));
        assertEquals(MY_ROYLLO_COIN_LOCK_TIME, myRoylloCoinProof.get().getAsset().getLockTime());
        assertEquals(MY_ROYLLO_COIN_RELATIVE_LOCK_TIME, myRoylloCoinProof.get().getAsset().getRelativeLockTime());
        assertEquals(MY_ROYLLO_COIN_SCRIPT_VERSION, myRoylloCoinProof.get().getAsset().getScriptVersion());
        assertEquals(MY_ROYLLO_COIN_SCRIPT_KEY, myRoylloCoinProof.get().getAsset().getScriptKey());
        // Chain anchor.
        assertEquals(MY_ROYLLO_COIN_ANCHOR_TX, myRoylloCoinProof.get().getAsset().getAnchorTx());
        assertEquals(MY_ROYLLO_COIN_ANCHOR_TX_ID, myRoylloCoinProof.get().getAsset().getAnchorTxId());
        assertEquals(MY_ROYLLO_COIN_ANCHOR_BLOCK_HASH, myRoylloCoinProof.get().getAsset().getAnchorBlockHash());
        assertEquals(MY_ROYLLO_COIN_ANCHOR_OUTPOINT, myRoylloCoinProof.get().getAsset().getAnchorOutpoint());
        assertEquals(MY_ROYLLO_COIN_ANCHOR_INTERNAL_KEY, myRoylloCoinProof.get().getAsset().getAnchorInternalKey());

        // Check proof.
        assertEquals(MY_ROYLLO_COIN_TX_MERKLE_PROOF, myRoylloCoinProof.get().getTxMerkleProof());
        assertEquals(MY_ROYLLO_COIN_INCLUSION_PROOF, myRoylloCoinProof.get().getInclusionProof());
        assertEquals(0, myRoylloCoinProof.get().getExclusionProofs().size());
    }

}
