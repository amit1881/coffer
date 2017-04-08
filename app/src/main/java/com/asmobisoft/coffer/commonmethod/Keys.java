package com.asmobisoft.coffer.commonmethod;

/**
 * Created by root on 2/2/16.
 */
public class Keys {

    /*PuExtra Keys*/
    public static final String PUTKEY_EMAIL = "EMAIL_PUT";
    public static final String RECHARGE_CONSTANT = "recharge";
    // TODO - insert your API KEY obtained from pay2all.com here
    public static final String API_KEY = "PPqNCFK6DCncHEvLzza4qBmQLS8IbNT60kl0loMVfp6x5h6cXRi3HztFt4Z2";
    /*PREFRENCE Keys*/
    public static final String PREFRENCE_EMAIL = "PREFRENCE_EMAIL";

    /*WEBSERVICE Keys*/
    public static final String BASE_URL = "http://webservice.cofferwallet.com/cofferdb.php?";
    public static final String REGISTERATION = "registration";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String PAASSWORD = "password";
    public static final String LOGIN = "applogin";
    public static final String SIGNUP_URL = "http://cofferwallet.com/webservice/cofferdb.php?task=registration";
    public static final String OTP_URL = "http://cofferwallet.com/webservice/cofferdb.php?task=CofferWalletRegistrationOTP";
    public static final String LOGIN_URL = "http://cofferwallet.com/webservice/cofferdb.php?task=applogin";
    public static final String FORGOT_PASS_URL = "http://webservice.cofferwallet.com/cofferdb.php?task=forgetpassword";
    public static final String UPDATE_PROFILE = "http://webservice.cofferwallet.com/cofferdb.php?task=updateprofile";
    public static final String USER_LIST = "http://webservice.cofferwallet.com/cofferdb.php?task=getcofferusersforchat";

    public static final String USER_LIST_CHAT =  "http://cofferwallet.com/webservice/cofferdb.php?task=getSuggestedFriends&mobile=9015848667";

    //Pay2All API
    public static final String ALL_PROVIDERS = "https://www.pay2all.in/web-api/get-provider?api_token=";
    public static final String MOBILE_RECHARGE ="https://www.pay2all.in/web-api/paynow?api_token=";
    public static final String ELECTRICITY_BILL ="https://www.pay2all.in/web-api/paynow?api_token=";
    public static final String GAS_BILL ="https://www.pay2all.in/web-api/paynow?api_token=";
    public static final String GET_LAST_50_TRANSACTION = "https://www.pay2all.in/web-api/get-report?api_token=PPqNCFK6DCncHEvLzza4qBmQLS8IbNT60kl0loMVfp6x5h6cXRi3HztFt4Z2";
    //public static final String GET_BALENCE = "https://www.pay2all.in/web-api/get-balance?api_token=PPqNCFK6DCncHEvLzza4qBmQLS8IbNT60kl0loMVfp6x5h6cXRi3HztFt4Z2";
    public static final String GET_Beneficiary_DETAIL = "https://www.pay2all.in/dmr/api/v1/get-all-beneficiary";
    public static final String GET_BALENCE = "http://cofferwallet.com/webservice/cofferdb.php?task=getUpdatedWalletBalance";
    public  static  final String WALLET_TO_WALLET_TRANSFER ="http://cofferwallet.com/webservice/cofferdb.php?task=transferMoneyToWallet";
    public  static final String WALLET_TRANSFER_HISTORY ="http://cofferwallet.com/webservice/cofferdb.php?task=getMoneyTransferHistory";
    public static final String GET_CUSTOMER = "https://www.pay2all.in/dmr/api/v1/get-customer";
    public static final String GENRATE_OTP = "https://www.pay2all.in/dmr/api/v1/generate-otp";
    public static final String CREATE_CUSTOMER = "https://www.pay2all.in/dmr/api/v1/add-customer";
    public static final String GET_ACCOUNT_NAME = "https://www.pay2all.in/dmr/api/v1/get-name";
    public static final String ADD_BENI = "https://www.pay2all.in/dmr/api/v1/add-beneficiary";
    public static final String TRANSECTION = "https://www.pay2all.in//dmr/api/v1/transaction";

    public static final String DELETE_BENI = "https://www.pay2all.in/dmr/api/v1/delete-beneficiary";
    public static final String DELETE_BENI_CONFORM = "https://www.pay2all.in/dmr/api/v1/delete-beneficiary-confirm";

    public static final String MOBILE_SERVICE ="MOBILE";
    public static final String DTH ="DTH";
    public static final String DATACARD ="DATACARD";
    public static final String POSTPAID ="POSTPAID";
    public static final String ELECTRICITY ="ELECTRICITY";
    public static final String GAS ="GAS";
    //Responce Messages
    public static final String OOPS ="OOPS !";
    public static final String CONG ="Congratulation !";
    public static final String IMPORTENT ="Important !";
    public static final String OHOO ="Ohoo !";
    public static final String SUCCESS_MESSGAE ="Your Transaction Submitted Successfully Done, Check Status in Transaction Report, Thanks.";
    public static final String PENDING_MESSGAE ="Your Transaction is pending, Please try again.";
    public static final String INITIATED_MESSGAE ="Your Transaction is Initiated";
    public static final String FAILED_MESSGAE ="Your Transaction is Failed,Please try again.";


    public static final String API_TOKEN ="api_token";
    public static final String MOBILE_NUMBER ="mobile_number";
    public static final String F_NAME ="fname";
    public static final String L_NAME ="lname";
    public static final String OTP ="otp";
    public static final String BANK_CODE ="bank_code";
    public static final String ACCOUNT_NUMBER ="account_number";
    public static final String IFSC ="ifsc";
    public static final String NAME ="name";
    public static final String BENIFICIARY_CODE ="BeneficiaryCode";
    public static final String CHANNEL ="channel";
    public static final String CLIENT_ID ="client_id";
    public static final String PAY_ID ="payid";

    public static final String STATUS ="status";
    public static final String RECIPIENT_ID ="recipient_id";
    public static final String RECIPIENT_NAME ="recipient_name";
    public static final String BANK_NAME ="BankName";
    public static final String ACCOUNT ="account";
    public static final String ORG_ACK_NO ="OrgAckNo";
    public static final String ORG_TRANS_REF_NUM ="OrgTransRefNum";

    public static final String MESSAGE ="message";
    public static final String TRANSECTION_REF_NO ="TransactionRefNo";
    public static final String ACK_NO ="AckNo";
    public static final String BANK_REF ="bank_ref";




}
