package client;

import model.Address;

public interface BlockClient {
    Address callAddressAPI(String addressString, long timeout);
}
