package mmt.source.com.babitafuels.Model;

import java.util.ArrayList;

public class FuelMnt {
    private static FuelMnt fm_instance = null;

    private User usrInfo;
    private User creditUsr;
    private ArrayList<HomeIcons> myPages;
    private CreditTxn creditTxn;
//    private Rolls myAccess;
    private Rolls setAccess;
    private Bunk bunk;
    private Pump pump;
    private Price todayPrice;
    private Expenses expenses;
    private ArrayList<Access> accessList;
    private ArrayList<Access> myAccess;
    private ArrayList<User> allUsrInfo;
    private ArrayList<Bunk> allBunk;
    private ArrayList<Pump> allPump;
    private ArrayList<Expenses> getMyExpenses;
    private ArrayList<CreditTxn> allCreditTxn;
    private ArrayList<Price> allPrice;
    public static FuelMnt getInstance() {
        if (fm_instance == null) {
            fm_instance = new FuelMnt();
        }

        return fm_instance;
    }

    FuelMnt() {
        accessList = new ArrayList<Access>();
        usrInfo = new User();
        creditUsr = new User();
        creditTxn = new CreditTxn();
        setAccess = new Rolls();
        myAccess = new ArrayList<Access>();
        bunk = new Bunk();
        allUsrInfo = new ArrayList<User>();
        allBunk = new ArrayList<Bunk>();
        allPump = new ArrayList<Pump>();
        fillAccess();
        myPages = new ArrayList<HomeIcons>();
        pump = new Pump();
        todayPrice = new Price();
        expenses = new Expenses();
        getMyExpenses = new ArrayList<Expenses>();
        allCreditTxn = new ArrayList<CreditTxn>();
        allPrice = new ArrayList<Price>();
    }
    public User getUsrInfo() {
        return usrInfo;
    }

    public void setUsrInfo(User usrInfo) {
        this.usrInfo = usrInfo;
    }

    public ArrayList<User> getAllUsrInfo() {
        return allUsrInfo;
    }

    public void setAllUsrInfo(ArrayList<User> allUsrInfo) {
        this.allUsrInfo = allUsrInfo;
    }

    public ArrayList<Bunk> getAllBunk() {
        return allBunk;
    }

    public void setAllBunk(ArrayList<Bunk> allBunk) {
        this.allBunk = allBunk;
    }

    public Bunk getBunk() {
        return bunk;
    }

    public void setBunk(Bunk bunk) {
        this.bunk = bunk;
    }

    public ArrayList<Access> getAccessList() {
        return accessList;
    }

    public void setAccessList(ArrayList<Access> accessList) {
        this.accessList = accessList;
    }

    public void fillAccess() {

        Access access = new Access();

        access.setAccessName("PRICE");
        accessList.add(access);

        access = new Access();
        access.setAccessName("STOCK");
        accessList.add(access);

        access = new Access();
        access.setAccessName("COLLECTION");
        accessList.add(access);

        access = new Access();
        access.setAccessName("EXPENSES");
        accessList.add(access);

        access = new Access();
        access.setAccessName("CREDIT_TXN");
        accessList.add(access);

        access = new Access();
        access.setAccessName("CREDIT_SETUP");
        accessList.add(access);

        access = new Access();
        access.setAccessName("REGISTRATION");
        accessList.add(access);

        access = new Access();
        access.setAccessName("ACCESS");
        accessList.add(access);

        access = new Access();
        access.setAccessName("EMPLOYMENT");
        accessList.add(access);
    }

    public Rolls getSetAccess() {
        return setAccess;
    }

    public void setSetAccess(Rolls setAccess) {
        this.setAccess = setAccess;
    }

    public ArrayList<Access> getMyAccess() {
        return myAccess;
    }

    public void setMyAccess(ArrayList<Access> myAccess) {
        this.myAccess = myAccess;
    }

    public CreditTxn getCreditTxn() {
        return creditTxn;
    }

    public void setCreditTxn(CreditTxn creditTxn) {
        this.creditTxn = creditTxn;
    }

    public User getCreditUsr() {
        return creditUsr;
    }

    public void setCreditUsr(User creditUsr) {
        this.creditUsr = creditUsr;
    }

    public ArrayList<HomeIcons> getMyPages() {
        return myPages;
    }

    public void setMyPages(ArrayList<HomeIcons> myPages) {
        this.myPages = myPages;
    }

    public Pump getPump() {
        return pump;
    }

    public void setPump(Pump pump) {
        this.pump = pump;
    }

    public ArrayList<Pump> getAllPump() {
        return allPump;
    }

    public void setAllPump(ArrayList<Pump> allPump) {
        this.allPump = allPump;
    }

    public Expenses getExpenses() {
        return expenses;
    }

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }


    public ArrayList<Expenses> getGetMyExpenses() {
        return getMyExpenses;
    }

    public void setGetMyExpenses(ArrayList<Expenses> getMyExpenses) {
        this.getMyExpenses = getMyExpenses;
    }

    public ArrayList<CreditTxn> getAllCreditTxn() {
        return allCreditTxn;
    }

    public void setAllCreditTxn(ArrayList<CreditTxn> allCreditTxn) {
        this.allCreditTxn = allCreditTxn;
    }

    public Price getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(Price todayPrice) {
        this.todayPrice = todayPrice;
    }

    public ArrayList<Price> getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(ArrayList<Price> allPrice) {
        this.allPrice = allPrice;
    }
}
