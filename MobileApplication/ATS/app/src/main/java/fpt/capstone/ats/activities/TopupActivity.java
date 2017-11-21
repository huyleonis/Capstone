package fpt.capstone.ats.activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fpt.capstone.ats.R;
import fpt.capstone.ats.fragments.BankResultFragment;
import fpt.capstone.ats.fragments.BankWebFragment;
import fpt.capstone.ats.fragments.CardInputFragment;
import fpt.capstone.ats.fragments.ChooseAmountFragment;
import fpt.capstone.ats.fragments.ChooseCardFragment;
import fpt.capstone.ats.fragments.ChoosePaymentMethodFragment;
import fpt.capstone.ats.utils.Commons;
import fpt.capstone.ats.utils.RequestServer;

public class TopupActivity extends AppCompatActivity implements BankWebFragment.BankWebInteractionListener {

    TextView textMethod;
    Button btnBack;
    int step = 0;

    FragmentManager fm;
    ChoosePaymentMethodFragment fgmChooseMethod = ChoosePaymentMethodFragment.newInstance();

    ChooseCardFragment fgmChooseCard;
    CardInputFragment fgmCardInputFragment;

    ChooseAmountFragment fgmChooseAmount;
    BankWebFragment fgmBankWeb;
    BankResultFragment fgmBankResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);

        textMethod = (TextView) findViewById(R.id.textMethod);
        btnBack = (Button) findViewById(R.id.btnBack);

        fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseMethod).commit();
    }

    @Override
    public void onBackPressed() {
        clickToBack(null);
    }

    public void clickToChooseMethod(View view) {
        String method = (String) view.getTag();

        if (fgmChooseMethod != null && fgmChooseMethod.isVisible()) {
            switch(method) {
                case "mobicard":
                    textMethod.setText("Nạp tiền bằng thẻ cào");
                    step = 1;

                    fgmChooseCard = ChooseCardFragment.newInstance();
                    fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseCard).commit();
                    break;
                case "banking":
                    textMethod.setText("Nạp tiền qua ngân hàng");
                    step = 3;

                    fgmChooseAmount = ChooseAmountFragment.newInstance();
                    fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseAmount).commit();
                    break;
            }

        }
    }

    public void clickToChooseCard(View view) {
        step = 2;
        String card = (String) view.getTag();
        fgmCardInputFragment = CardInputFragment.newInstance(card);
        fm.beginTransaction().replace(R.id.layoutTopup, fgmCardInputFragment).commit();
    }

    public void clickToBack(View view) {
        switch(step) {
            case 0:
                TopupActivity.this.finish();
                break;
            case 1:
                fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseMethod).commit();
                textMethod.setText("Nạp tiền");
                step = 0;
                break;
            case 2:
                fgmChooseCard = ChooseCardFragment.newInstance();
                fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseCard).commit();
                step = 1;
                break;
            case 3:
                fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseMethod).commit();
                textMethod.setText("Nạp tiền");
                step = 0;
                break;
            case 4:
                fgmChooseAmount = ChooseAmountFragment.newInstance();
                fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseAmount).commit();
                step = 3;
                break;
            case 5:
                fgmChooseAmount = ChooseAmountFragment.newInstance();
                fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseAmount).commit();
                step = 3;
                break;
        }
    }

    public void clickTotopupByMobicard(View view) {
        if (fgmCardInputFragment != null && fgmCardInputFragment.isVisible()) {
            fgmCardInputFragment.requestTopup();
        }
    }

    public void clickToChooseAmount(View view) {
        final String amount = view.getTag() + "000";

        RequestServer rs = new RequestServer();
        rs.delegate = new RequestServer.RequestResult() {
            @Override
            public void processFinish(String result) {
                try {
                    JSONObject json = new JSONObject(result);

                    String res = json.getString("result");
                    if (res.equals("true")) {
                        String response = json.getString("message");
                        JSONObject objResult = new JSONObject(response);
                        String responseCode = objResult.getString("response_code");
                        if (responseCode.equalsIgnoreCase("00")) {
                            String tokenCode = objResult.getString("token_code");
                            String checkoutUrl = objResult.getString("checkout_url");

                            fgmBankWeb = BankWebFragment.newInstance(amount, tokenCode, checkoutUrl);
                            fm.beginTransaction().replace(R.id.layoutTopup, fgmBankWeb).commit();
                            step = 4;

                        } else {
                            new AlertDialog.Builder(TopupActivity.this)
                                    .setTitle("Lỗi server")
                                    .setMessage(responseCode)
                                    .setPositiveButton("Tắt thông báo", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create().show();
                        }

                    } else {
                        Toast.makeText(TopupActivity.this, json.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException ex) {
                    Toast.makeText(TopupActivity.this, ex.getMessage() + " - " + result, Toast.LENGTH_LONG).show();
                }


            }
        };

        Map<String, String> params = new HashMap<>();
        params.put("username", Commons.getUsername(this));
        params.put("amount", amount);
        rs.execute(params, "topup", "bank", "POST");
    }

    @Override
    public void onBankWebFinish(String tokenCode) {
        fgmBankResult = BankResultFragment.newInstance(tokenCode);
        fm.beginTransaction().replace(R.id.layoutTopup, fgmBankResult).commit();
    }
}
