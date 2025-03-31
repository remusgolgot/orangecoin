package api.controllers;

import com.btc.api.controllers.AddressController;
import com.btc.api.model.ResponseModel;
import com.btc.api.services.AddressService;
import com.btc.api.services.UtxoService;
import com.btc.model.AddressDto;
import com.btc.model.SummaryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.btc.api.messages.Responses.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @Mock
    private UtxoService utxoService;

    @InjectMocks
    private AddressController addressController;

    @Test
    public void addressControllerCount() {

        when(addressService.count()).thenReturn(5149L);
        Long result = addressController.count();
        assertEquals(5149, result);
    }

    @Test
    public void addressControllerAddressExists() {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setBalance(148.0);
        when(addressService.getAddress("test")).thenReturn(address);
        ResponseModel<AddressDto> response = addressController.getAddress("test");
        assertEquals(1, response.getCount());
        assertEquals("test", response.getData().getAddress());
        assertEquals(148.0, response.getData().getBalance());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void addressControllerAddressDoesNotExist() {

        when(addressService.getAddress("test")).thenReturn(null);
        ResponseModel<AddressDto> response = addressController.getAddress("test");
        assertEquals(0, response.getCount());
        assertNull(response.getData());
        assertEquals("No result found", response.getMessage());
        assertEquals(false, response.getStatus());
    }

    @Test
    public void addressControllerAddressFullExists() {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setBalance(148.0);
        address.setToxicity(1112L);
        when(addressService.getAddressFull("test")).thenReturn(address);
        ResponseModel<AddressDto> response = addressController.getAddressFull("test");
        assertEquals(1, response.getCount());
        assertEquals("test", response.getData().getAddress());
        assertEquals(148.0, response.getData().getBalance());
        assertEquals(1112L, response.getData().getToxicity());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void addressControllerAddressFullDoesNotExist() {

        when(addressService.getAddressFull("test")).thenReturn(null);
        ResponseModel<AddressDto> response = addressController.getAddressFull("test");
        assertEquals(0, response.getCount());
        assertNull(response.getData());
        assertEquals("No result found", response.getMessage());
        assertEquals(false, response.getStatus());
    }

    @Test
    public void addressControllerAddressMeta() {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setMeta("BlackRock");
        address.setBalance(148.0);
        List<AddressDto> metaAddresses = new ArrayList<>();
        metaAddresses.add(address);
        when(addressService.getAddressesMeta()).thenReturn(metaAddresses);
        ResponseModel<List<AddressDto>> response = addressController.getAddressesMeta();
        assertEquals(1, response.getCount());
        assertEquals(1, response.getData().size());
        assertEquals("BlackRock", response.getData().get(0).getMeta());
        assertEquals(148.0, response.getData().get(0).getBalance());
        assertEquals(true, response.getStatus());
    }

    // top

    @Test
    public void addressControllerAddressTop() {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setMeta("BlackRock");
        address.setBalance(148.0);
        List<AddressDto> topAddresses = new ArrayList<>();
        topAddresses.add(address);
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        when(addressService.getAddressesTop(100)).thenReturn(topAddresses);
        ResponseModel<List<AddressDto>> response = addressController.getAddressesTop(100);
        assertEquals(5, response.getCount());
        assertEquals(5, response.getData().size());
        assertEquals("BlackRock", response.getData().get(0).getMeta());
        assertEquals(148.0, response.getData().get(0).getBalance());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void addressControllerAddressTop100() {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setMeta("BlackRock");
        address.setBalance(148.0);
        List<AddressDto> topAddresses = new ArrayList<>();
        topAddresses.add(address);
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        when(addressService.getAddressesTop(100)).thenReturn(topAddresses);
        ResponseModel<List<AddressDto>> response = addressController.getAddressesTop();
        assertEquals(5, response.getCount());
        assertEquals(5, response.getData().size());
        assertEquals("BlackRock", response.getData().get(0).getMeta());
        assertEquals(148.0, response.getData().get(0).getBalance());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void addressControllerAddressGreater() throws Exception {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setMeta("BlackRock");
        address.setBalance(148.0);
        List<AddressDto> topAddresses = new ArrayList<>();
        topAddresses.add(address);
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        when(addressService.getAddressesGreater(15)).thenReturn(topAddresses);
        ResponseModel<List<AddressDto>> response = addressController.getAddressesGreater(15);
        assertEquals(5, response.getCount());
        assertEquals(5, response.getData().size());
        assertEquals("BlackRock", response.getData().get(0).getMeta());
        assertEquals(148.0, response.getData().get(0).getBalance());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void addressControllerAddressGreaterThrowsException() throws Exception {

        when(addressService.getAddressesGreater(21000001)).thenThrow(new Exception(AMOUNT_IS_GREATER_THAN_MAX_SUPPLY));
        ResponseModel<List<AddressDto>> response = addressController.getAddressesGreater(21000001);

        assertEquals(0, response.getCount());
        assertFalse(response.getStatus());
        assertNull(response.getData());
        assertEquals(AMOUNT_IS_GREATER_THAN_MAX_SUPPLY, response.getError());
        assertEquals(SYSTEM_SERVICE_NO_RESULT_LIST, response.getMessage());
    }

    @Test
    public void addressControllerAddressLike() {

        AddressDto address = new AddressDto();
        address.setAddress("test");
        address.setMeta("BlackRock");
        address.setBalance(148.0);
        List<AddressDto> topAddresses = new ArrayList<>();
        topAddresses.add(address);
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        topAddresses.add(new AddressDto());
        when(addressService.getAddressesLike("test")).thenReturn(topAddresses);
        ResponseModel<List<AddressDto>> response = addressController.getAddressesLike("test");
        assertEquals(5, response.getCount());
        assertEquals(5, response.getData().size());
        assertEquals("BlackRock", response.getData().get(0).getMeta());
        assertEquals(148.0, response.getData().get(0).getBalance());
        assertEquals(true, response.getStatus());
    }

    @Test
    public void addressControllerAddressSummary() {

        when(addressService.getTotalAddresses()).thenReturn(1000L);
        when(addressService.getAddressesNotEmpty()).thenReturn(500L);
        when(addressService.getTotalBalanceSum()).thenReturn(100000000000.0);
        when(addressService.getLowSpentAddressesSum()).thenReturn(55000000000.0);
        when(addressService.getSpentZeroAddressesSum()).thenReturn(45000000000.0);
        when(addressService.getMaxHeight()).thenReturn(100);

        when(utxoService.count()).thenReturn(5000L);
        when(utxoService.countUnspent()).thenReturn(3000L);

        ResponseModel<SummaryDto> response = addressController.getSummary();
        assertEquals(1, response.getCount());
        assertEquals(true, response.getStatus());
        assertEquals(1000L, response.getData().getTotalAddresses());
        assertEquals(500L, response.getData().getNonEmptyAddresses());
        assertEquals(1000.0, response.getData().getTotalBalance());
        assertEquals(550.0, response.getData().getLowSpentBalance());
        assertEquals(450.0, response.getData().getSpentZeroBalance());
        assertEquals(100, response.getData().getMaxHeight());

        assertEquals(5000L, response.getData().getNrUtxos());
        assertEquals(3000L, response.getData().getNrUnspentUtxos());


    }

}
