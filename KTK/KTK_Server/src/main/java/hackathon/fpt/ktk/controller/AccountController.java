package hackathon.fpt.ktk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import hackathon.fpt.ktk.dto.AccountDTO;
import hackathon.fpt.ktk.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = "/account")
public class AccountController extends AbstractController {

    @Autowired
    private ServletContext context;

    /**
     * @return web page account
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView viewAccount() {
        ModelAndView m = new ModelAndView("account");
        return m;
    }

    /**
     * @return list of account
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/getListAccount", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String getListAccount() throws JsonProcessingException {
        // ObjectMapper mapper = new ObjectMapper();
        List<AccountDTO> list = accountService.getAllAccount();

        if (list != null) {
            return new Gson().toJson(list);
        }

        return "No records found";
    }

    /**
     * Get account info by username
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/get/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO getAccountByUsername(@PathVariable String username) {
        AccountDTO acc = accountService.getAccountByUsername(username);

        return acc;
    }

    /**
     * tao tai khoan moi
     *
     * @param account
     * @return trang thai thanh cong hay that bai
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody Account account) {

        boolean isSuccessful = false;

        AccountDTO dto = accountService.insert(account);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * cap nhat tai khoan
     *
     * @param account
     * @return trang thai thanh cong hay that bai
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Account account) {

        boolean isSuccessful = false;

        AccountDTO dto = accountService.update(account);

        if (dto != null) {
            isSuccessful = true;
        }

        return (isSuccessful) ? "success" : "fail";
    }

    /**
     * dang nhap
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object checkLogin(@RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password) {

        Map<String, String> map = new HashMap<>();
        String result = accountService.checkLogin(username, password);
        map.put("result", result);

        String basePath = context.getRealPath(".");

        if (result.equals("Success")) {
            AccountDTO account = accountService.getAccountByUsername(username);

            String randomToken = randomToken();
            boolean isSuccessful = accountService.updateToken(username, randomToken);
            if (isSuccessful) {
                logger.debug("Update Token Successfully");
            }

            map.put("username", username);
            map.put("fullname", account.getFullname());
            map.put("token", randomToken);
        }

        return map;
    }

    /**
     * @param username
     * @param token
     * @return
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    public String checkToken(@RequestParam String username,
                             @RequestParam String token) {
        System.out.println("Check token for " + username + " token = " + token);
        AccountDTO dto = accountService.getAccountByUsername(username);
        String accToken = dto.getToken();
        System.out.println("accToken = " + accToken);

        if (accToken != null && accToken.equals(token)) {
            System.out.println("Return trueeeee");
            return "true";
        }

        return "false";
    }
    
    /**
     * Tạo mã token 16 kí tự
     *
     * @return
     */
    public static String randomToken() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < 19) {

            int index = (int) (random.nextFloat() * alphabet.length());
            if (sb.length() % 5 == 4 && sb.length() != 0) {
                sb.append("-");
            } else {
                sb.append(alphabet.charAt(index));
            }
        }
        System.out.println("token:" + sb.toString());

        return sb.toString();
    }
}
