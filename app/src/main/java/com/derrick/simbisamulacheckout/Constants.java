package com.derrick.simbisamulacheckout;

import java.util.Locale;

public class Constants {
    /**
     * Change the below params to map to your sandbox details
     */
    public static String CHECKOUT_URL = "https://beep2.cellulant.com:9212/checkout/v2/express/";

    public static String IV_KEY = BuildConfig.IV_KEY;

    public static String ENCRYPTION_KEY = BuildConfig.ENCRYPTION_KEY;

    public static String SERVICE_CODE = BuildConfig.SERVICE_CODE;

    public static String ACCESS_KEY = BuildConfig.ACCESS_KEY;


    public static String SUCCESS_REDIRECT = "http://localhost/mula/success.php";


    public static String FAILED_REDIRECT = "http://localhost/mula/failed.php";

    public static String PAYMENT_WEBHOOK_URL = "http://localhost/mula/payment.php";

    public static String LANGUAGE = Locale.getDefault().getLanguage();

}
