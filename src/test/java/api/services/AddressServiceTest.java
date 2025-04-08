package api.services;

import com.btc.api.dao.AddressDAO;
import com.btc.api.dao.BlockDAO;
import com.btc.api.dao.UtxoDAO;
import com.btc.api.model.Address;
import com.btc.api.services.AddressService;
import com.btc.model.AddressDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.btc.api.messages.Responses.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressDAO addressDAO;

    @Mock
    private UtxoDAO utxoDAO;

    @Mock
    private BlockDAO blockDAO;

    @Test
    public void getAddressFull() {

        Address address = new Address();
        address.setBalance(100000000.0);
        address.setReceived(200000000.0);
        address.setMeta("MSTR");
        address.setFirstInput(50000L);
        when(addressDAO.getAddress(any())).thenReturn(address);
        when(utxoDAO.countUnspentUtxo(any())).thenReturn(100L);
        when(utxoDAO.countSpentUtxo(any())).thenReturn(200L);

        AddressDto addressDto = addressService.getAddressFull("testAddress");

        assertNotNull(addressDto);
        assertEquals(1, addressDto.getBalance());
        assertEquals("MSTR", addressDto.getMeta());

        assertEquals(100, addressDto.getUxtoUnSpent());
        assertEquals(200, addressDto.getUxtoSpent());
        assertEquals(300, addressDto.getUxto());
    }

    @Test
    public void getAddress() {

        Address address = new Address();
        address.setBalance(100000000.0);
        address.setReceived(210000000.0);
        when(addressDAO.getAddress(any())).thenReturn(address);

        AddressDto addressDto = addressService.getAddress("testAddress2");

        assertNotNull(addressDto);
        assertEquals(1, addressDto.getBalance());
        assertEquals(2.1, addressDto.getReceived());
    }

    @Test
    public void getAddressLike() {

        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        when(addressDAO.getAddressesLike(any())).thenReturn(addressList);

        List<AddressDto> addressDtoList = addressService.getAddressesLike("testAddress2");

        assertNotNull(addressDtoList);
        assertEquals(4, addressDtoList.size());
    }

    @Test
    public void getAddressTop() {

        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        when(addressDAO.getAddressesTop(6)).thenReturn(addressList);

        List<AddressDto> addressDtoList = addressService.getAddressesTop(6);

        assertNotNull(addressDtoList);
        assertEquals(4, addressDtoList.size());
    }

    @Test
    public void getAddressMeta() {

        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        when(addressDAO.getAddressesMeta()).thenReturn(addressList);

        List<AddressDto> addressDtoList = addressService.getAddressesMeta();

        assertNotNull(addressDtoList);
        assertEquals(4, addressDtoList.size());
    }

    @Test
    public void getAddressGreater() throws Exception {

        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        addressList.add(new Address());
        when(addressDAO.getAddressesGreater(500000000)).thenReturn(addressList);

        List<AddressDto> addressDtoList = addressService.getAddressesGreater(5);

        assertNotNull(addressDtoList);
        assertEquals(4, addressDtoList.size());
    }

    @Test
    public void getAddressGreaterWithExceptionLowerThanZero() {

        Exception exception = assertThrows(Exception.class, () -> addressService.getAddressesGreater(-1));
        assertEquals(AMOUNT_IS_LOWER_THAN_ZERO, exception.getMessage());
    }

    @Test
    public void getAddressGreaterWithExceptionHigherThanLimit() {

        Exception exception = assertThrows(Exception.class, () -> addressService.getAddressesGreater(21000001));
        assertEquals(AMOUNT_IS_GREATER_THAN_MAX_SUPPLY, exception.getMessage());
    }

    @Test
    public void getMaxHeight() {

        when(blockDAO.maxHeight()).thenReturn(22000);

        int maxHeight = addressService.getMaxHeight();
        assertEquals(22000, maxHeight);
    }

}
