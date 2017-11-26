/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon.fpt.ktk.paymentmodule;

import hackathon.fpt.ktk.util.RequestServer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chi Hieu
 */
public class MobileCard {
    public MobileCardResult cardPay(String pinCard, String cardSerial, String typeCard,
            String refCode, String clientFullname, String clientMobile, String clientEmail) throws MalformedURLException, IOException {
        
        Map<String, String> params = new HashMap<>();
        params.put("func", NganLuongConfig.FUNCTION);
        params.put("version", NganLuongConfig.VERSION);
        params.put("merchant_id", NganLuongConfig.MERCHANT_ID);
        params.put("merchant_account", NganLuongConfig.EMAIL_RECEIVE_MONEY);
        params.put("merchant_password", NganLuongConfig.MERCHANT_PASSWORD_ENCODED);
        params.put("pin_card", pinCard);
        params.put("card_serial", cardSerial);
        params.put("type_card", typeCard);
        params.put("ref_code", refCode);
        params.put("client_fullname", clientFullname);
        params.put("client_email", clientEmail);
        params.put("client_mobile", clientMobile);

        
        System.out.println("passowrd: " + NganLuongConfig.MERCHANT_PASSWORD_ENCODED);
        
        String sUrl = NganLuongConfig.NGANLUONG_URL_CARD_POST;

        String response = RequestServer.sendPostRequest(sUrl, params);

        String[] data = response.split("\\|", -1);
        System.out.println("Response from Ngan Luong: ("+ data.length +") " + response);
        
        MobileCardResult result = processResponse(data);

        return result;
    }
    
    private MobileCardResult processResponse(String[] data) {
        if (data.length == 13) {
            MobileCardResult result = new MobileCardResult();
            result.setErrorCode(data[0]);
            result.setMerchantId(data[1]);
            result.setMerchantAccount(data[2]);
            result.setPinCard(data[3]);
            result.setCardSerial(data[4]);
            result.setTypeCard(data[5]);
            result.setOrderId(data[6]);
            result.setClientFullname(data[7]);
            result.setClientEmail(data[8]);
            result.setClientMobile(data[9]);
            result.setCardAmount(data[10]);
            result.setAmount(data[11]);
            result.setTransactionId(data[12]);

            if (result.getErrorCode().equals("00")) {
                result.setErrorMessage("Nạp tiền thành công " + result.getCardAmount() + " đồng");
            } else {
                result.setErrorMessage(getErrorMessage(result.getErrorCode()));
            }

            return result;
        }

        return null;
    }

    private String getErrorMessage(String errorCode) {
        int code = Integer.parseInt(errorCode);

        switch (code) {
            case 99:
                return "Lỗi, tuy nhiên lỗi chưa được định nghĩa hoặc chưa xác định được nguyên nhân";
            case 1:
                return "Lỗi, địa chỉ IP truy cập API của NgânLượng.vn bị từ chối";
            case 2:
                return "Lỗi, tham số gửi từ merchant tới NgânLượng.vn chưa chính xác (thường sai tên tham số hoặc thiếu tham số)";
            case 3:
                return "Lỗi, Mã merchant không tồn tại hoặc merchant đang bị khóa kết nối tới NgânLượng.vn";
            case 4:
                return "Lỗi, Mã checksum không chính xác (lỗi này thường xảy ra khi mật khẩu giao tiếp giữa merchant và NgânLượng.vn không chính xác, hoặc cách sắp xếp các tham số trong biến params không đúng)";
            case 5:
                return "Tài khoản nhận tiền nạp của merchant không tồn tại";
            case 6:
                return "Tài khoản nhận tiền nạp của merchant đang bị khóa hoặc bị phong tỏa; không thể thực hiện được giao dịch nạp tiền";
            case 7:
                return "Thẻ đã được sử dụng ";
            case 8:
                return "Thẻ bị khóa";
            case 9:
                return "Thẻ hết hạn sử dụng";
            case 10:
                return "Thẻ chưa được kích hoạt hoặc không tồn tại";
            case 11:
                return "Mã thẻ sai định dạng";
            case 12:
                return "Sai số serial của thẻ";
            case 13:
                return "Mã thẻ và số serial không khớp";
            case 14:
                return "Thẻ không tồn tại";
            case 15:
                return "Thẻ không sử dụng được";
            case 16:
                return "Số lần thử (nhập sai liên tiếp) của thẻ vượt quá giới hạn cho phép";
            case 17:
                return "Hệ thống Telco bị lỗi hoặc quá tải; thẻ chưa bị trừ";
            case 18:
                return "Hệ thống Telco bị lỗi hoặc quá tải; thẻ có thể bị trừ; cần phối hợp với NgânLượng.vn để tra soát";
            case 19:
                return "Kết nối từ NgânLượng.vn tới hệ thống Telco bị lỗi; thẻ chưa bị trừ (thường do lỗi kết nối giữa NgânLượng.vn với Telco; ví dụ sai tham số kết nối; mà không liên quan đến merchant)";
            case 20:
                return "Kết nối tới telco thành công; thẻ bị trừ nhưng chưa cộng tiền trên NgânLượng.vn";
        }
        return null;
    }
}
