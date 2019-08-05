package net.zulu.StartApp;

public class Api {

    private static final String ROOT_URL = "http://192.168.1.101/startappphp/v1/Api.php?apicall=";
    public static final String IP = "192.168.1.101";

    public static final String URL_CREATE_RECORD = ROOT_URL + "createRecord";
    public static final String URL_READ_RECORDS = ROOT_URL + "getRecords";
    public static final String URL_UPDATE_RECORD = ROOT_URL + "updateRecord";
    public static final String URL_DELETE_RECORD = ROOT_URL + "deleteRecord&id=";
    public static final String URL_LOGIN = ROOT_URL + "login";

    public static final String URL_CREATE_TRANSACTION = ROOT_URL + "createTransaction";
    public static final String URL_READ_TRANSACTIONS = ROOT_URL + "getTransactions";
    public static final String URL_UPDATE_TRANSACTION = ROOT_URL + "updateTransaction";
    public static final String URL_DELETE_TRANSACTION = ROOT_URL + "deleteTransaction&id=";
}
