package org.royllo.explorer.core.service.request;

import org.royllo.explorer.core.dto.request.AddAssetMetaDataRequestDTO;
import org.royllo.explorer.core.dto.request.AddProofRequestDTO;
import org.royllo.explorer.core.dto.request.RequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Request service.
 */
public interface RequestService {

    /**
     * Returns the list of opened requests.
     *
     * @return opened requests
     */
    List<RequestDTO> getOpenedRequests();

    /**
     * Get a request.
     *
     * @param id id in database
     * @return request
     */
    Optional<RequestDTO> getRequest(long id);

    /**
     * Add a request to add a Raw proof.
     *
     * @param rawProof Raw proof that validates the asset information
     * @return id The request created
     */
    AddProofRequestDTO addProof(String rawProof);

    /**
     * Add a request to add an asset meta data.
     *
     * @param taroAssetId Taro asset id
     * @param metaData    Metadata corresponding to the meta hash stored in the genesis information
     * @return id of the request created
     */
    AddAssetMetaDataRequestDTO addAssetMetaData(String taroAssetId,
                                                String metaData);

}
