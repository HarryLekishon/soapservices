package org.example.testkra.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaxpayerValidationService extends Remote {
    String validateTaxpayerID(String loginId, String password, String typeOfTaxpayer, String taxpayerID) throws RemoteException;
}
