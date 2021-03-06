package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.AccountDTO;
import hackathon.fpt.ktk.entity.Account;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAllAccount();

    AccountDTO getAccountByUsername(String username);

    AccountDTO insert(Account account);

    AccountDTO update(Account account);

    String checkLogin(String username, String password);

    boolean updateToken(String username, String token);

    String makePayment(String username, int stationId);
    
    AccountDTO getAccountById(int id);
    
    boolean topupBalance(String username, double amount);
    
    boolean checkLicensePlate(String username, String licensePlate);
    
    
}
