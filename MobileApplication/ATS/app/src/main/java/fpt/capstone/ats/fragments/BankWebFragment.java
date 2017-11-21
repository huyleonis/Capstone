package fpt.capstone.ats.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fpt.capstone.ats.R;
import fpt.capstone.ats.utils.ConstantValues;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BankWebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BankWebFragment extends Fragment {
    private static final String AMOUNT_PARAM = "amount";
    private static final String TOKEN_CODE_PARAM = "token";
    private static final String CHECKOUT_URL_PARAM = "checkout_url";

    private String amount;
    private String tokenCode;
    private String checkoutUrl;

    WebView webData;
    View rootView;


    public BankWebFragment() {
        // Required empty public constructor
    }


    public static BankWebFragment newInstance(String amount, String tokenCode, String checkoutUrl) {
        BankWebFragment fragment = new BankWebFragment();
        Bundle args = new Bundle();
        args.putString(AMOUNT_PARAM, amount);
        args.putString(TOKEN_CODE_PARAM, tokenCode);
        args.putString(CHECKOUT_URL_PARAM, checkoutUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            amount = getArguments().getString(AMOUNT_PARAM);
            tokenCode = getArguments().getString(TOKEN_CODE_PARAM);
            checkoutUrl = getArguments().getString(CHECKOUT_URL_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bank_web, container, false);

        webData = (WebView) rootView.findViewById(R.id.webViewTopup);
        initView();

        return rootView;
    }

    private void initView() {
        webData.getSettings().setJavaScriptEnabled(true);
        webData.setWebChromeClient(new WebChromeClient());
        webData.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("BANKWEB_FGM", "url: " + url);
                if (url.equalsIgnoreCase(ConstantValues.RETURN_URL)) {

                    deletgate.onBankWebFinish(tokenCode);
//                    Intent intentCheckOut = new Intent(getActivity(), CheckOrderActivity.class);
//                    intentCheckOut.putExtra(CheckOrderActivity.TOKEN_CODE, mTokenCode);
//                    startActivity(intentCheckOut);
//                    finish();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        if (!checkoutUrl.equalsIgnoreCase("")) {
            webData.loadUrl(checkoutUrl);
        }
    }

    private BankWebInteractionListener deletgate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BankWebInteractionListener) {
            deletgate = (BankWebInteractionListener) context;
        }
    }

    public interface BankWebInteractionListener {
        void onBankWebFinish(String tokenCode);
    }
}
