package org.royllo.explorer.web.controller.user.asset;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static org.royllo.explorer.core.dto.asset.AssetDTO.ASSET_ID_ALIAS_MAX_SIZE;
import static org.royllo.explorer.core.dto.asset.AssetDTO.ASSET_ID_ALIAS_MIN_SIZE;
import static org.royllo.explorer.core.dto.asset.AssetDTO.README_MAX_SIZE;

/**
 * User asset form.
 */
@Data
@SuppressWarnings("checkstyle:VisibilityModifier")
public class UserAssetForm {

    /** Asset id. */
    @NotEmpty
    String assetId;

    /** Asset id alias. */
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "{validation.asset.assetIdAlias.invalid}")
    @Size(min = ASSET_ID_ALIAS_MIN_SIZE, max = ASSET_ID_ALIAS_MAX_SIZE, message = "{validation.asset.assetIdAlias.size}")
    String assetIdAlias;

    /** Readme. */
    @Size(max = README_MAX_SIZE, message = "{validation.asset.readme.size}")
    String readme;

}
