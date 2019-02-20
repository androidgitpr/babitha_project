package mmt.source.com.babitafuels.Model;

public class User {

    private String usrId;
    private String usrName;
    private String createDate;
    private String doJ;
    private String doL;
    private String grade;
    private String emailId;
    private String mobileNum;
    private String addr;
    private String emgEmailId;
    private String emgMobileNum;
    private String emgAddr;
    private String bloodG;
    private String idProof;
    private String regType;
    private String busName;
    private String notes;
    private String action;
    private String pay;
    private String password;
    private String bunkId;
    private Boolean status;
    private String creditLmt;
    private String fuelType;
    private String advPaid;
    private String startDate;
    private String endDate;
    private String empType;
    private String designation;

    public User() {

    }

    public User(User inParam) {

        this.usrId = inParam.usrId;
        this.usrName = inParam.usrName;
        this.createDate = inParam.createDate;
        this.doJ = inParam.doJ;
        this.doL = inParam.getDoL();
        this.grade = inParam.grade;
        this.emailId = inParam.emailId;
        this.mobileNum = inParam.mobileNum;
        this.addr = inParam.addr;
        this.emgEmailId = inParam.emgEmailId;
        this.emgMobileNum = inParam.emgMobileNum;
        this.emgAddr = inParam.emgAddr;
        this.bloodG = inParam.bloodG;
        this.idProof = inParam.idProof;
        this.regType = inParam.regType;
        this.busName = inParam.busName;
        this.notes = inParam.notes;
        this.action = inParam.action;
        this.pay = inParam.pay;
        this.password = inParam.password;
        this.bunkId = inParam.bunkId;
        this.status = inParam.status;
    }
    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDoJ() {
        return doJ;
    }

    public void setDoJ(String doJ) {
        this.doJ = doJ;
    }

    public String getDoL() {
        return doL;
    }

    public void setDoL(String doL) {
        this.doL = doL;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmgEmailId() {
        return emgEmailId;
    }

    public void setEmgEmailId(String emgEmailId) {
        this.emgEmailId = emgEmailId;
    }

    public String getEmgMobileNum() {
        return emgMobileNum;
    }

    public void setEmgMobileNum(String emgMobileNum) {
        this.emgMobileNum = emgMobileNum;
    }

    public String getEmgAddr() {
        return emgAddr;
    }

    public void setEmgAddr(String emgAddr) {
        this.emgAddr = emgAddr;
    }

    public String getBloodG() {
        return bloodG;
    }

    public void setBloodG(String bloodG) {
        this.bloodG = bloodG;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBunkId() {
        return bunkId;
    }

    public void setBunkId(String bunkId) {
        this.bunkId = bunkId;
    }

    public String getCreditLmt() {
        return creditLmt;
    }

    public void setCreditLmt(String creditLmt) {
        this.creditLmt = creditLmt;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getAdvPaid() {
        return advPaid;
    }

    public void setAdvPaid(String advPaid) {
        this.advPaid = advPaid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
