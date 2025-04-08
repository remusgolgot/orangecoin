package com.btc.api.services;

import com.btc.api.dao.SummaryDAO;
import com.btc.api.dao.BlockDAO;
import com.btc.api.model.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryService implements Cloneable {

    @Autowired
    protected SummaryDAO summaryDAO;

    @Autowired
    protected UtxoService utxoService;

    @Autowired
    protected BlockDAO blockDAO;

    @Autowired
    protected AddressService addressService;

    public Summary getSummary() {
        return summaryDAO.getSummary();
    }

    public void updateSummary() {
        Summary summary = getSummary();

        summary.setMaxHeight(blockDAO.maxHeight());
        summary.setUnspendable(utxoService.getUnspendableBalanceSum() / 100000000.0);
        summary.setNrUnspentUtxos(utxoService.countUnspent());
        summary.setNrUtxos(utxoService.count());
        summary.setUtxoTotal(utxoService.getTotal());

        summary.setNrAddresses(addressService.getTotalAddresses());
        summary.setTotalBalance(addressService.getTotalBalanceSum() / 100000000.0);
        summary.setNonEmptyAddresses(addressService.getAddressesNotEmpty());
        summary.setLowSpentBalance(addressService.getLowSpentAddressesSum() / 100000000.0);
        summary.setSpentZeroBalance(addressService.getSpentZeroAddressesSum() / 100000000.0);

        summaryDAO.update(summary);
    }

}
