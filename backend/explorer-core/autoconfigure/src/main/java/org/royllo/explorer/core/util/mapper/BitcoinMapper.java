package org.royllo.explorer.core.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.royllo.explorer.core.domain.bitcoin.BitcoinTransactionOutput;
import org.royllo.explorer.core.dto.bitcoin.BitcoinTransactionOutputDTO;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Bitcoin mapper.
 */
@Mapper(nullValuePropertyMappingStrategy = IGNORE)
public interface BitcoinMapper {

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    BitcoinTransactionOutput mapToBitcoinTransactionOutput(BitcoinTransactionOutputDTO source);

    BitcoinTransactionOutputDTO mapToBitcoinTransactionOutputDTO(BitcoinTransactionOutput source);

}
