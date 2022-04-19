package io.bit.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Currency implements Serializable {
    private String currency;//币种标识
    private String name;//名称

    private int canWithdraw;// 0 不能提币  1 可以提币
    private int canDeposit;//  0 不能充值  1 能充值

    private BigDecimal minDeposit;//最小充值金额
    private BigDecimal minWithdraw;//最小提币金额
    private BigDecimal maxWithdraw;//最大提币金额

    private String exCurrency;//用于交易对中的标识 比如： trxusdt eusdt > 交易对中都是 usdt
    private int type;//1 主链  2 token
    private String chain;//主链币种标识
    private BigDecimal feeRate;//提币费率
    private int precision;//精度 提币支持的小数位
    private BigDecimal price;//币价 单位 QC/coin

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

    public int getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(int canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public int getCanDeposit() {
        return canDeposit;
    }

    public void setCanDeposit(int canDeposit) {
        this.canDeposit = canDeposit;
    }

    public BigDecimal getMinDeposit() {
        return minDeposit;
    }

    public void setMinDeposit(BigDecimal minDeposit) {
        this.minDeposit = minDeposit;
    }

    public String getExCurrency() {
        return exCurrency;
    }

    public void setExCurrency(String exCurrency) {
        this.exCurrency = exCurrency;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public BigDecimal getMinWithdraw() {
        return minWithdraw;
    }

    public void setMinWithdraw(BigDecimal minWithdraw) {
        this.minWithdraw = minWithdraw;
    }

    public BigDecimal getMaxWithdraw() {
        return maxWithdraw;
    }

    public void setMaxWithdraw(BigDecimal maxWithdraw) {
        this.maxWithdraw = maxWithdraw;
    }
}
