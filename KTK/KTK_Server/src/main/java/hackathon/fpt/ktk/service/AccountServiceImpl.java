package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.dto.AccountDTO;
import hackathon.fpt.ktk.entity.Account;
import hackathon.fpt.ktk.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl extends AbstractServiceImpl implements AccountService {

    @Override
    public List<AccountDTO> getAllAccount() {

        List<Account> accounts = accountRepos.findAll();
        if (!accounts.isEmpty()) {
            List<AccountDTO> dtos = new ArrayList<>();
            for (Account account : accounts) {
                AccountDTO dto = AccountDTO.convertFromEntity(account);
                dtos.add(dto);
            }

            return dtos;
        }

        logger.error("getAllAccount: Khong co du lieu");
        return null;
    }

    @Override
    public AccountDTO getAccountByUsername(String username) {

        Account account = accountRepos.findByUsername(username);
        if (account != null) {
            AccountDTO dto = AccountDTO.convertFromEntity(account);
            return dto;
        }

        logger.debug("getAccountByUsername: Khong co du lieu");
        return null;
    }

    @Override
    public AccountDTO insert(Account account) {

        AccountDTO dto = null;

        try {
            // neu user dang ki xe ma xe da co trong he thong
            if (account.getVehicle() != null) {
                Vehicle vehicle = vehicleRepos.findByLicensePlate(account.getVehicle().getLicensePlate());
                if (vehicle != null) {
                    account.setVehicle(vehicle);
                }
            }

            // system will generate password automatically
            account.setPassword("1234");

            Account processedAccount = accountRepos.save(account);
            if (processedAccount != null) {
                dto = AccountDTO.convertFromEntity(processedAccount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public AccountDTO update(Account account) {
        AccountDTO dto = null;

        try {
            Account existingAccount = accountRepos.findOne(account.getId());

            if (existingAccount != null) {
                account.setPassword(existingAccount.getPassword());

                // neu user dang ki xe ma xe da co trong he thong
                if (account.getVehicle() != null) {
                    Vehicle vehicle = vehicleRepos
                            .findByLicensePlate(account.getVehicle().getLicensePlate());
                    if (vehicle != null) {
                        account.setVehicle(vehicle);
                    }
                }

                Account processedAccount = accountRepos.save(account);
                dto = AccountDTO.convertFromEntity(processedAccount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return dto;
    }

    @Override
    public String checkLogin(String username, String password) {
        Account account = accountRepos.findByUsername(username);

        if (account == null) {
            return "Account does not exist";
        }

        if (!account.getPassword().equals(password)) {
            return "Password is invalid";
        }

        return "Success";
    }

    @Override
    public boolean updateToken(String username, String token) {

        Account account = accountRepos.findByUsername(username);
        if (account != null) {
            account.setToken(token);
            Account processedAccount = accountRepos.save(account);
            if (processedAccount != null) {
                return true;
            }
        }

        return false;
    }
}
