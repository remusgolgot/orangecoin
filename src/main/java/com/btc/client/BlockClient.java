package com.btc.client;

import com.btc.model.Address;

public interface BlockClient {
    Address callAddressAPI(String addressString, long timeout);
}
