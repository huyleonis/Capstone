package fpt.capstone.ats.activities;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fpt.capstone.ats.R;
import fpt.capstone.ats.fragments.CardInputFragment;
import fpt.capstone.ats.fragments.ChooseCardFragment;
import fpt.capstone.ats.fragments.ChoosePaymentMethodFragment;

public class TopupActivity extends AppCompatActivity {

    TextView textMethod;
    Button btnBack;
    int step = 0;

    FragmentManager fm;
    ChoosePaymentMethodFragment fgmChooseMethod = ChoosePaymentMethodFragment.newInstance();

    ChooseCardFragment fgmChooseCard;
    CardInputFragment fgmCardInputFragment;

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
        step = 1;
        if (fgmChooseMethod != null && fgmChooseMethod.isVisible()) {
            switch(method) {
                case "mobicard":
                    textMethod.setText("Nạp tiền bằng thẻ cào");

                    fgmChooseCard = ChooseCardFragment.newInstance();
                    fm.beginTransaction().replace(R.id.layoutTopup, fgmChooseCard).commit();
                    break;
                case "banking":
                    textMethod.setText("Nạp tiền qua ngân hàng");
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
                step = 0;
                break;
            case 4:
                step = 3;
                break;
        }
    }

    public void clickTotopupByMobicard(View view) {
        if (fgmCardInputFragment != null && fgmCardInputFragment.isVisible()) {
            fgmCardInputFragment.requestTopup();
        }
    }
}
