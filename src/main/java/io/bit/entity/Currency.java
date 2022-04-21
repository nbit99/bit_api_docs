package io.bit.entity;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.math.BigDecimal;

public class Currency implements Serializable {
    private boolean canWithdraw;// 能否提币
    private boolean canCharge;//  能否充值
    private BigDecimal chargeMin;//最低充值金额(0则不生效)
    private int confirmTimes;//建议的，确认充值到账确认次数
    private int canWithdrawTimes;//建议的，可提现充值确认次数
    private BigDecimal defaultFee;
    private String type;//币种类型(erc20为tokenCoins) //old
    private String changeTo;//换链到哪个 如：eos -> zeos
    private int decimals;//erc20的小数位
    private String contractAddress;//合约地址
    private JSONArray otherChain;//其他关联币种
    private BigDecimal chargeFee;//充值手续费


    private String currency;//币种标识
    private String name;//名称
    private BigDecimal withdrawMin;//最小提币金额
    private BigDecimal withdrawMax;//最大提币金额
    private String exCurrency;//用于交易对中的标识 比如： trxusdt eusdt > 交易对中都是 usdt
    private int nType;//1 主链  2 token
    private String chain;//主链币种标识
    private BigDecimal feeRate;//提币费率
    private int precision;//精度 提币支持的小数位
    private BigDecimal price;//币价 单位 QC/coin


    public boolean isCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public boolean isCanCharge() {
        return canCharge;
    }

    public void setCanCharge(boolean canCharge) {
        this.canCharge = canCharge;
    }

    public BigDecimal getChargeMin() {
        return chargeMin;
    }

    public void setChargeMin(BigDecimal chargeMin) {
        this.chargeMin = chargeMin;
    }

    public int getConfirmTimes() {
        return confirmTimes;
    }

    public void setConfirmTimes(int confirmTimes) {
        this.confirmTimes = confirmTimes;
    }

    public int getCanWithdrawTimes() {
        return canWithdrawTimes;
    }

    public void setCanWithdrawTimes(int canWithdrawTimes) {
        this.canWithdrawTimes = canWithdrawTimes;
    }

    public BigDecimal getDefaultFee() {
        return defaultFee;
    }

    public void setDefaultFee(BigDecimal defaultFee) {
        this.defaultFee = defaultFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChangeTo() {
        return changeTo;
    }

    public void setChangeTo(String changeTo) {
        this.changeTo = changeTo;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public JSONArray getOtherChain() {
        return otherChain;
    }

    public void setOtherChain(JSONArray otherChain) {
        this.otherChain = otherChain;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(BigDecimal withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public String getExCurrency() {
        return exCurrency;
    }

    public void setExCurrency(String exCurrency) {
        this.exCurrency = exCurrency;
    }

    public int getnType() {
        return nType;
    }

    public void setnType(int nType) {
        this.nType = nType;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
