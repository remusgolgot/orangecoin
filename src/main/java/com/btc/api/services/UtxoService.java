package com.btc.api.services;

import com.btc.api.model.TransactionOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btc.api.dao.UtxoDAO;

@Service
public class UtxoService implements Cloneable {

    @Autowired
    protected UtxoDAO utxoDAO;

    public long count() {
        return utxoDAO.count(TransactionOutput.class);
    }

    public long countUnspent() {
        return utxoDAO.countUnspentUtxo();
    }

    public long getTotal() {
        return utxoDAO.getTotal();
    }

    public long getUnspendableBalanceSum() {
        return utxoDAO.getUnspendableBalanceSum();
    }


}
