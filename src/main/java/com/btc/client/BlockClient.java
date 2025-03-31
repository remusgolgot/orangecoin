package com.btc.client;

import com.btc.model.AddressDto;
import com.btc.api.model.Block;
import com.btc.api.model.Transaction;
import com.btc.model.AddressDto;

public interface BlockClient {
    AddressDto callAddressAPI(String addressString, long timeout);

    Transaction callTransactionAPI(String transactionId, long timeout);

    Block callBlockHeightAPI(int height, long timeout);

    Block callBlockAPI(String blockHash, long timeout);
}
