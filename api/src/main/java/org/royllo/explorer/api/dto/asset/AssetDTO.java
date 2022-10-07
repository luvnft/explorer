package org.royllo.explorer.api.dto.asset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.royllo.explorer.api.dto.bitcoin.TransactionOutputDTO;
import org.royllo.explorer.api.dto.user.UserDTO;

import static lombok.AccessLevel.PRIVATE;

/**
 * Taro asset.
 */
@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
@SuppressWarnings("checkstyle:VisibilityModifier")
public class AssetDTO {

    /** Unique identifier. */
    Long id;

    /** The first outpoint of the transaction that created the asset (txid:vout). */
    TransactionOutputDTO genesisPoint;

    /** The asset creator. */
    UserDTO creator;

    /** The name of the asset. */
    String name;

    /** The hashed metadata of the asset. */
    String meta;

    /** The asset ID that uniquely identifies the asset. */
    String assetId;

    /** The index of the output that carries the unique Taro commitment in the genesis transaction. */
    int outputIndex;

}
