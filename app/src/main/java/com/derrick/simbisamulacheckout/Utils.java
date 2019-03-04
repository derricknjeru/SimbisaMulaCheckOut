package com.derrick.simbisamulacheckout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.derrick.simbisamulacheckout.Constants.FAILED_REDIRECT;
import static com.derrick.simbisamulacheckout.Constants.LANGUAGE;
import static com.derrick.simbisamulacheckout.Constants.PAYMENT_WEBHOOK_URL;
import static com.derrick.simbisamulacheckout.Constants.SUCCESS_REDIRECT;

public class Utils {
    private interface Params_key {
        String key_amount = "amount";
        String key_msisdn = "MSISDN";
        String key_currency = "currency";
    }

    public static String getDueDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //adding 1 hour to the current date
        calendar.add(Calendar.HOUR, 1);

        String dueDate = sdf.format(calendar.getTime());

        return dueDate;
    }

    public static int generateTrxID() {
        final int min = 1000;
        final int max = 100000;
        final int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    public static String getEncryptedParams(JSONObject parametersObject) throws Exception {
        String requestParams;//------ add other necessary parameters and do the necessary safety precautions like
        byte[] encryptParams = Encrypt.encryptString(
                Constants.IV_KEY,
                Constants.ENCRYPTION_KEY,
                parametersObject.toString()
        );
        requestParams = Encrypt.bytesToHex(encryptParams);
        return requestParams;
    }

    public static JSONObject createParams(String amount, String phone_number, String currency
            , String service_description, String email, String first_name, String shop, String country_code)
            throws JSONException {
        JSONObject parametersObject = new JSONObject();
        int merchantTransactionID = generateTrxID();
        //add the parameters
        parametersObject.put(Params_key.key_amount, amount);
        parametersObject.put(Params_key.key_msisdn, phone_number);
        parametersObject.put(Params_key.key_currency, currency);
        parametersObject.put("serviceDescription", service_description);
        parametersObject.put("countryCode", country_code);
        parametersObject.put("customerEmail", email);
        parametersObject.put("customerFirstName", first_name);
        parametersObject.put("language", LANGUAGE);
        parametersObject.put("merchantTransactionID", merchantTransactionID);
        /**
         *  This is a combination of center and shop example Pizza inn westlands
         */
        parametersObject.put("accountNumber", shop);
        parametersObject.put("successRedirectUrl", SUCCESS_REDIRECT);
        parametersObject.put("failRedirectUrl", FAILED_REDIRECT);
        parametersObject.put("paymentWebhookUrl", PAYMENT_WEBHOOK_URL);
        parametersObject.put("serviceCode", Constants.SERVICE_CODE);
        parametersObject.put("accessKey", Constants.ACCESS_KEY);
        //can be empty
        parametersObject.put("payerClientCode", "");
        /**
         * This is a future date and it is the date that we expect the payment to expire
         */
        parametersObject.put("dueDate", getDueDate());
        return parametersObject;
    }

}
