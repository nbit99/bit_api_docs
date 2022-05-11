# api接口文档

## 1. 目录

[TOC]

## 2. 修订信息

|  版本   |     时间     | 修订人  | 修订内容 |
| :---: | :--------: | :--: | :--- |
| 1.0.0 | 2021-06-23 | ---  | 创建文档 |
---

## 3. 概述

此文档用以规范服务端API方法。
`**测试平台域名 : https://ttm.BIT.com**`
`**对接前，现在测试版商户平台注册账号**`

## 4. 接口定义

1.安全认证
　	1.1用户密钥获取
	   用户需要进入Bit商户版网站用户中心-api设置申请开通API交易资格，申请开通api需要通过实名认证，，并生成API交易的Access Key、Secret Key和Des Key三个密钥。
	   Access Key：签约的BIT账号对应的用户名API交易的唯一标识。
	   Secret Key：用于对请求参数签名加密的私钥。
	   Des Key： des加密的密钥，每一次调用接口都必须传输。
　　   请勿向任何人泄露这三个参数，这像您的密码一样重要。
	   注：重置密钥时，需要验证资金密码。
	1.2认证签名机制
　　　1.2.1用户需要请求参数列表中，除sign、reqTime, des_key外，其他需要使用到的参数都是要签名的参数。如请求BTC委托：
##### Example #####
method=order
&accesskey=your_accesskey_key
&price=1024
&amount=1
&tradeType=1
&currency=btc
    这串字符串便是待签名字符串。参数的顺序很重要，请不要篡改参数的顺序，可能会导致验证无法通过的后果。　　　　
     1.2.3 MD5签名
      在用户得到待签名的字符串时，需要执行MD5签名，这时需要用到用户的secret key。用户需向客服索取由BIT提供的相应的加密类工具，然后执行待签名串加密。
JAVA加密示例：
String params = 	"method=cancelOrder&accesskey=your_access_key&id=123456&currency=btc";
String your_secret_key = EncryDigestUtil.digest(your_secret_key);
String sign = EncryDigestUtil.hmacSign(params,your_secret_key);

	变量sign得到的值就是用户加密后请求的签名串，参数的顺序很重要，请不要篡改顺序。
加密方式是：先对your_secret_key进行sha1算法的digest hash获得secrete字符串，然后用这个字符串再对请求参数字符串进行md5算法加密获得sign。这里的请求参数是参数名的ascii码进行排序的。
参数是参数名的ascii码进行排序的。

### 4.1 验证虚拟货币地址是否正确
**描述**：
验证虚拟货币地址是否正确
正确返回代码1000
不正确返回6001

**请求地址**

> https://ttm.BIT.com/api/validAddress

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序,
注：memo类型的币种可以用address加下划线（_）MEMO的形式如：(bts01_122xx)
会同时验证地址和MEMO的有效性

**请求参数**


| 参数名       | 类型     | 是否必须 | 描述        |
| :-------- | :----- | :--- | :-------- |
| accesskey | String | 是    | accesskey |
| address   | String | 是    | 地址        |
| currency  | String | 是    | 币种        |
| sign      | String | 是    | 请求加密签名串   |
| reqTime   | String | 是    | 当前时间毫秒数   |


**返回结果**

| 参数名          | 类型     | 是否必须 | 示例   | 描述       |
| :----------- | :----- | :--- | :--- | :------- |
| code         | String | 是    | 1000 | 参考第5的返回码 |
| message      | String | 是    | 操作成功 | 返回消息     |
| merchantName | String | 是    |      | 地址所属商户名称 |

**返回示例**

```
{"code":6001,"message":"无效的提币地址"}
```

### 4.2 获取数字货币充值地址
**描述**：
获取属于该平台的数字货币充值地址

**请求地址**

> https://ttm.BIT.com/api/getNewAddress

**请求实例(POST)**

> https://ttm.BIT.com/api/getNewAddress?accesskey={accesskey}&currency=btc&number=10&sign=请求加密签名串&reqTime=当前时间毫秒数
> currency = 币种(如btc)

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**


| 参数名             | 类型     | 是否必须 | 描述                                    |
| :-------------- | :----- | :--- | :------------------------------------ |
| accesskey       | String | 是    | accesskey                             |
| currency        | String | 是    | 币种                                    |
| customerOrderId | String | 是    | 商户端获取记录的id，必须唯一，每次获取，给一个唯一的id号，以防重复获取 |
| number          | String | 是    | 请求要获取多少个充值地址                          |
| sign            | String | 是    | 请求加密签名串                               |
| reqTime         | String | 是    | 当前时间毫秒数                               |


**返回结果**

| 参数名         | 类型     | 是否必须 | 示例      | 描述         |
| :---------- | :----- | :--- | :------ | :--------- |
| code        | String | 是    | 1000    | 参考第5的返回码   |
| message     | String | 是    | 操作成功    | 返回消息       |
| address     | Array  | 是    |         | 地址列表       |
| orderNo     | String | 是    | 1231231 | 工单号        |
| wallet      | String | 是    | btc1    | 所属哪个钱包     |
| orderNo     | String | 是    | test    | 钱包名称       |
| addressSize | String | 是    | 100     | 商户在本平台的地址数 |
| keySize     | String | 是    | 100     | 商户平台剩余地址数  |

**返回示例**

```
{
    "code": "1000",
    "message": "操作成功。",
    "data": {
        "address": [
            {
                "address": "获取的地址",
                "wallet": "钱包名称"
            }…
        ],
        "addressSize": 100,
        "orderNo": "请求的工单号"
    }
}
```

### 4.3 提现虚拟币操作
**描述**：
提现虚拟币操作
返回5005的时候，提币是已经提交到商户平台了，然后商户重复提交才会出现这种情况，这时候，就要把提笔记录标记为提交到商户成功了，切记

**请求地址**

> https://ttm.BIT.com/api/btcDownload

**请求实例(POST)**

> https://ttm.BIT.com/api/btcDownload?accesskey=d68b4e66-879a-xxxx-9837-1d4deed3fa0c&cashAmount=1&currency=eth&customerOrderId=1234&itransfer=false&payFee=0.01&receiveAddress=0x2312946129461291&remark= &sUserName=test

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**


| 参数名             | 类型     | 是否必须 | 描述              |
| :-------------- | :----- | :--- | :-------------- |
| accesskey       | String | 是    | accesskey       |
| cashAmount      | String | 是    | 提现金额            |
| currency        | String | 是    | 币种缩写            |
| customerOrderId | String | 是    | 商户端提笔记录的id，必须唯一 |
| itransfer       | String | 是   | 是否商户间内部转账，直接给否       |
| payFee          | String | 是    | 提现矿工费           |
| receiveAddress  | String | 是    | 提现接收地址          |
| remark          | String | 是    | 留言              |
| sUserName       | String | 是    | 提现接收地址          |
| sign            | String | 是    | 请求加密签名串         |
| reqTime         | String | 是    | 当前时间毫秒数         |


**返回结果**

| 参数名     | 类型     | 是否必须 | 示例      | 描述       |
| :------ | :----- | :--- | :------ | :------- |
| code    | String | 是    | 1000    | 参考第5的返回码 |
| message | String | 是    | 操作成功    | 返回消息     |
| reason  | String | 是    |         | 请求失败原因   |
| orderNo | String | 是    | 1231231 | 工单号      |

**返回示例**

```
{
    "code": "1000",
    "message": "操作成功。",
    "data": {
       "orderNo": "请求的工单号"
    }
}
```

### 4.6 获取商户账户余额
**描述**：
获取商户账户余额

**请求地址**

> https://ttm.BIT.com/api/getAccount

**请求实例(POST)**

> https://ttm.BIT.com/api/getAccount?accesskey=d68b4e66-879a-xxxx-9837-1d4deed3fa0c

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**


| 参数名       | 类型     | 是否必须 | 描述        |
| :-------- | :----- | :--- | :-------- |
| accesskey | String | 是    | accesskey |
| sign      | String | 是    | 请求加密签名串   |
| reqTime   | String | 是    | 当前时间毫秒数   |


**返回结果，以btc为例子**

| 参数名         | 类型         | 是否必须 | 示例   | 描述                      |
| :---------- | :--------- | :--- | :--- | :---------------------- |
| code        | String     | 是    | 1000 | 参考第5的返回码                |
| btc         | BigDecimal | 是    | 100  | btc可用余额(币种英文，就是对应的余额)   |
| btc_total   | BigDecimal | 是    | 100  | btc总额(币种英文，就是对应的总额)     |
| btc_balance | BigDecimal | 是    | 100  | btc可用余额(币种英文，就是对应的可用余额) |
| btc_freez   | BigDecimal | 是    | 100  | btc冻结金额(币种英文，就是对应的冻结金额) |

**返回示例**

```
{
    "code": "1000",
    "message": "操作成功。",
    "data": {
       "btc": 100
       ...
    }
}
```
### 4.8 获取充值记录同步确认次数
**描述**：
获取商户账户配置

**请求地址**

> https://ttm.BIT.com/api/getConfirmTimesV2

**请求实例(POST)**

> https://ttm.BIT.com/api/getConfirmTimesV2?accesskey=d68b4e66-879a-xxxx-9837-1d4deed3fa0c&addHash=xxx&btcTo=xxxx&currency=usdt&id=1234

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**


| 参数名       | 类型     | 是否必须 | 描述          |
| :-------- | :----- | :--- | :---------- |
| accesskey | String | 是    | accesskey   |
| addHash   | String | 是    | 交易号         |
| btcTo     | String | 是    | 充值地址        |
| currency  | String | 是    | 币种          |
| id        | String | 是    | 充值记录商户平台的id |
| sign      | String | 是    | 请求加密签名串     |
| reqTime   | String | 是    | 当前时间毫秒数     |


**返回结果**

| 参数名         | 类型     | 是否必须 | 示例   | 描述           |
| :---------- | :----- | :--- | :--- | :----------- |
| code        | String | 是    | 1000 | 参考第5的返回码     |
| times       | int    | 是    | 10   | 确认次数         |
| successTime | int    | 否    | 10   | 成功时间戳        |
| status      | int    | 状态   | 0    | 0待确认，1失败，2成功 |

**返回示例**

```
{
    "code": "1000",
    "message": "操作成功。",
    "data": {
      
       ...
    }
}
```

### 4.8 内部划转虚拟币操作
**描述**：
提现虚拟币操作

**请求地址**

> https://ttm.BIT.com/api/transfer

**请求实例(POST)**

> https://merchants.BIT.com/api/transfer?accesskey=d68b4e66-879a-xxxx-9837-1d4deed3fa0c&cashAmount=1&currency=eth&customerOrderId=1234&toUserId=12345&remark= &sUserName=test

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**

| 参数名        | 类型    | 是否必须 | 描述      |
| :----------- | :----- | :---    | :------- |
| accesskey     | String | 是       | accesskey |
| sign   | String | 是       | 请求加密签名串 |
| reqTime   | String | 是       | 当前时间毫秒数 |
| cashAmount   | String | 是       | 划转金额 |
| toUserId   | String | 是       | 划转目标用户id |
| remark   | String | 是       | 留言 |
| currency   | String | 是       | 币种 |
| sUserName   | String | 是       | 划转出用户名 |
| customerOrderId   | String | 是       | 商户端的唯一id |
| dest   | String | 是       | BITXX或者BITZZ |

**返回结果**

| 参数名     | 类型      | 是否必须 | 示例      | 描述   |
| :------ | :------ | :--- | :------ | :--- |
| code  | String   | 是    | 1000 | 参考第5的返回码 |
| message| String| 是|操作成功| 返回消息|
| reason  | String| 是| | 请求失败原因|
| orderNo | String| 是|1231231| 工单号|

**返回示例**

```
{
    "code": "1000",
    "message": "操作成功。",
    "data": {
       "orderNo": "请求的工单号"
    }
}
```



### 4.9 获取单个币种信息 
**描述**：
获取币种信息，标识符、动态提币费率、充值提币限额、类型...
此接口的一些参数会动态变化，如：feeRate（单笔提币建议手续费），需要平台定时更新到系统内存，可作为显示给用户的提币费率，用户提交提币时需要
判断提交的费率是否小于等于从商户获取的费率。

**请求地址**

> https://ttm.BITXX.com/api/currency

**请求实例(POST)**

> https://ttm.BITXX.com/api/currency?accesskey=d68b4e66-879a-xxxx-9837-1d4deed3fa0c&currency=btc

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**


| 参数名       | 类型     | 是否必须 | 描述             |
| :-------- | :----- | :--- | :------------- |
| accesskey | String | 是    | accesskey      |
| currency  | String | 是    | 币种，可以为空，为空返回所有 |
| sign      | String | 是    | 请求加密签名串        |
| reqTime   | String | 是    | 当前时间毫秒数        |


**返回结果**

| 参数名              | 类型         | 是否必须 | 示例         | 描述                     |
| :--------------- | :--------- | :--- | :--------- | :--------------------- |
| code             | String     | 是    | 1000       | 参考第5的返回码               |
| canCharge        | bool       | 是    | true       | 能否充值                   |
| canWithdraw      | bool       | 是    | false      | 能否提现                   |
| chargeMin        | BigDecimal | 是    | 100        | 最低充值金额(0则不生效)          |
| confirmTimes     | int        | 是    | 12         | 建议的，确认充值到账确认次数         |
| canWithdrawTimes | int        | 是    | 36         | 建议的，可提现充值确认次数          |
| defaultFee       | BigDecimal | 是    | 0.1        | 建议矿工费（弃用，由feeRate代替）   |
| type             | String     | 是    | tokenCoins | 币种类型(erc20为tokenCoins) |
| changeTo         | String     | 否    | zeos       | 换链到哪个                  |
| decimals         | int        | 否    | 18         | erc20的小数位              |
| contractAddress  | String     | 否    | xxx        | erc20的合约地址             |
| otherChain       | JSON Array | 否    | []         | 其它链                     |
| currency         | JSON Array | 否    | btc        | 币种标识                    |
| name             | String     | 是    | Bitcoin    | 名称                     |
| withdrawMin      | BigDecimal | 是    | 0.001      | 小提币金额（0代表不限制）                     |
| withdrawMax      | BigDecimal | 是    | 1000.00    | 最大提币金额（0代表不限制）                     |
| exCurrency       | String     | 是    | zb         | 用于交易对中的标识 比如： trxusdt eusdt > 交易对中都是 usdt |
| nType            | int        | 是    | 1          | 新类型 1 主链  2 token                     |
| chain            | String     | 否    | ztrx       | token所在的主链币种标识                     |
| feeRate          | BigDecimal | 是    | 0.01       | 提币费率                     |
| precision        | int        | 是    | 8          | 精度 提币支持的小数位                     |
| price            | BigDecimal | 是    | 100.00     | 币价 单位 QC/coin                     |


**返回示例**

```
{
    "code":1000,
    "message":"操作成功",
    "data":[
        {
            "canCharge":true,
            "canWithdraw":true,
            "canWithdrawTimes":6,
            "chargeFee":0.0001,
            "chargeMin":0.0001,
            "confirmTimes":1,
            "decimals":0,
            "defaultFee":0.0002,
            "exCurrency":"BTC",
            "feeRate":0.00016,
            "nType":1,
            "precision":8,
            "price":298500,
            "type":"trustOpenWallet",
            "withdrawMax":0,
            "withdrawMin":0.0002
        }
        ......
}
```

### 4.10 获取所有币种信息
**描述**：
获取所有币种信息，标识符、动态提币费率、充值提币限额、类型...

**请求地址**

> https://ttm.BIT.com/api/currencies

**请求实例(GET)**

> https://merchants.BIT.com/api/currencies?accesskey=d68b4e66-879a-xxxx-9837-1d4deed3fa0c

**必要授权**

签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**请求参数**

| 参数名        | 类型    | 是否必须 | 描述      |
| :----------- | :----- | :---    | :------- |
| accesskey     | String | 是       | accesskey |
| sign   | String | 是       | 请求加密签名串 |
| reqTime   | String | 是       | 当前时间毫秒数 |

**返回结果**

| 参数名     | 类型      | 是否必须 | 示例      | 描述   |
| :------ | :------ | :--- | :------ | :--- |
| code  | String   | 是    | 1000 | 参考第5的返回码 |
| message| String| 是|操作成功| 返回消息|
| data  | json array| 是| [...] | 所有币种信息数组|

**返回示例**

```
{
    "code": "1000",
    "message": "操作成功。",
    "data": [
        { 
            "canDeposit":1, //充值是否开启 0(关闭) 1(开启)
            "canWithdraw":1,//提币是否开启 0(关闭) 1(开启)
            "currency":"btc", //币种标识符
            "exCurrency":"BTC", //用于交易对的币种名称
            "feeRate":0.00016, //动态提币费率，需要定时获取更新
            "maxWithdraw":0, //最大提币限额，0代表不限制
            "minDeposit":0.0001,//最小充值限额，0代表不限制
            "minWithdraw":0.0002,//最小提币限额，0代表不限制
            "name":"btc", //币种名称
            "precision":8, //当前币种支持的小数位位数，一般用于提币时限制小数位数
            "price":287322.88,//币价(CNY)
            "type":1, //类型 1(主链) 2(Token)
            "chain":"" //token所在的链，token类型才返回该子段
        },
        ......
    ]
}
```
**返回结果**
参考api/currency接口


##  5.回调接口
**注意**：
这里是指商户平台主动向商户进行通知的方法，采用的是http的post方法，请求的地址就是商户在设置api时，所填的回调地址。
根据传输参数中的method字段来判断不同的方法。

回调的签名验证机制：
利用deskey，把secrect加密出来，这两个均在api设置里面生成，把加密后的secrect进行sha1算法的digest hash获得一个字符串，然后再用这个字符串对参数字符串进行md5算法加密，获得sign，把这个sign和商户平台传递过来的sign进行比对，进行验证。

**补单机制**：
	如果调用的结果不是success，那个则定义为调用失败，反之调用成功，如果不成功会进行补单机制，
	目前补单的机制：
		失败20次之前，每5秒回调一次，失败20次之后，每小时回调一次，然后如果再失败次数超过44次，停止回调，需要登录商户版，执行回调。

### 5.1	提币补单回调
**描述**：
如果调用的结果不是success，那个则定义为调用失败，反之调用成功
前20次回调，如果不收到success，继续回调
失败20次后，每一小时回调一次，直至24小时过去
仍然失败，需要登录商户版，重新进行回调

**必要授权**
签名，除sign,reqTime,des_key,addHash不需要签名，其他都要，按照参数名的ascii排序

**补单参数**

| 参数名           | 类型         | 是否必须 | 描述         |
| :------------ | :--------- | :--- | :--------- |
| accesskey     | String     | 是    | accesskey  |
| amount        | BigDecimal | 是    | 提现金额       |
| btcDownloadId | String     | 是    | 提现记录id     |
| commandId     | String     | 是    | 无须理会       |
| currency       | String     | 是    | 币种       |
| confirm       | String     | 是    | 是否确认       |
| fees          | String     | 是    | 手续费        |
| freezeId      | String     | 是    | 冻结了金额的记录id |
| isDel         | String     | 是    | 此记录是否被删除   |
| managerName   | String     | 是    | 操作商户管理员    |
| managerId     | String     | 是    | 操作商户管理员的id |
| manageTime    | String     | 是    | 操作时间       |
| method        | String     | 是    | download   |
| orderNo       | String     | 是    | 此次提现操作的工单号 |
| payFee        | String     | 是    | 实际支付了的手续费  |
| partner_id    | String     | 是    | 商户id       |
| realFee       | String     | 是    | 真实的手续费     |
| remark        | String     | 是    | 提现的留言      |
| status        | String     | 是    | 2:已进行提现,   |
| submitTime    | String     | 是    | 商户请求提现的时间  |
| toAddress     | String     | 是    | 提现地址       |
| userName      | String     | 是    | 商户的用户名     |
| addHash       | String     | 是    | 提现交易号      |

### 5.2	充币记录补单回调
**描述**：
此接口，是补单通知充值记录，商户平台根据地址，发现有往这个地址充值的充值记录，就会通过此接口，通知到商户
如果调用的结果不是success，那个则定义为调用失败，反之调用成功
前20次回调，如果不收到success，继续回调
失败20次后，每一小时回调一次，直至24小时过去
仍然失败，需要登录商户版，重新进行回调

**必要授权**
签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**补单参数**

| 参数名        | 类型     | 是否必须 | 描述         |
| :--------- | :----- | :--- | :--------- |
| accesskey  | String | 是    | accesskey  |
| method     | String | 是    | charge固定值  |
| parameters | String | 是    | 充值回调内容参见下表 |

| 参数名          | 类型       | 是否必须 | 描述                                  |
| :----------- | :------- | :--- | :---------------------------------- |
| id           | String   | 是    | 充值记录id，根据这个做唯一                      |
| isIn         | int      | 是    | 1为充值，93为内部转账                        |
| btcFrom      | String   | 是    | 充值来源地址，可能会没有                        |
| btcTo        | String   | 是    | 充值到账地址                              |
| addHash      | String   | 是    | 充值交易号，并非每一笔唯一                       |
| number       | number   | 是    | 充值到账金额                              |
| sendimeTime  | datetime | 是    | 商户平台接收到充值的时间                        |
| status       | int      | 是    | 0未到账，1充值失败，2已到账                     |
| configTime   | datetime | 是    | 商户平台给充值入账的时间                        |
| banlance     | number   | 是    | 预留字段                                |
| entrustId    | number   | 是    | 预留字段                                |
| price        | number   | 是    | 预留字段                                |
| fees         | number   | 是    | 充值手续费                               |
| wallet       | String   | 是    | 充值钱包标识                              |
| sumBtc       | String   | 是    | 预留字段                                |
| confirmTimes | int      | 是    | 确认次数，如果通知的次数已经达到商户需要的确认入账次数，可以给用户入账 |
| remark       | String   | 是    | 预留字段                                |
| sucConfirm   | int      | 是    | 0充值未入账，1充值成功并入账                     |
| timestr      | String   | 是    | 预留字段                                |
| currency     | String   | 是    | 币种                                  |

### 5.3	充币记录确认次数补单回调
**描述**：
此接口，是补单通知充值记录，商户平台根据地址，发现有往这个地址充值的充值记录，就会通过此接口，通知到商户
如果调用的结果不是success，那个则定义为调用失败，反之调用成功
前20次回调，如果不收到success，继续回调
失败20次后，每一小时回调一次，直至24小时过去
仍然失败，需要登录商户版，重新进行回调

**必要授权**
签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**补单参数**

| 参数名          | 类型       | 是否必须 | 描述                                  |
| :----------- | :------- | :--- | :---------------------------------- |
| accesskey    | String   | 是    | accesskey                           |
| addHash      | String   | 是    | 充值交易号                               |
| btcTo        | String   | 是    | 充值到账地址                              |
| confirmTimes | int      | 是    | 确认次数，如果通知的次数已经达到商户需要的确认入账次数，可以给用户入账 |
| currency     | String   | 是    | 币种                                  |
| id           | String   | 是    | 充值记录id                              |
| method       | String   | 是    | syncConfirmTimes固定值                 |
| successTime  | datetime | 是    | 商户平台给充值入账的时间                        |

### 5.4	充币成功补单回调
**描述**：
此接口，只需返回success就可以了，什么都不用操作
如果调用的结果不是success，那个则定义为调用失败，反之调用成功
前20次回调，如果不收到success，继续回调
失败20次后，每一小时回调一次，直至24小时过去
仍然失败，需要登录商户版，重新进行回调

**必要授权**
签名，除sign,reqTime,des_key不需要签名，其他都要，按照参数名的ascii排序

**补单参数**

| 参数名        | 类型     | 是否必须 | 描述         |
| :--------- | :----- | :--- | :--------- |
| accesskey  | String | 是    | accesskey  |
| method     | String | 是    | charge固定值  |
| parameters | String | 是    | 充值回调内容参见下表 |

| 参数名          | 类型       | 是否必须 | 描述                                  |
| :----------- | :------- | :--- | :---------------------------------- |
| id           | String   | 是    | 充值记录id，根据这个做唯一                      |
| isIn         | int      | 是    | 1为充值，93为内部转账                        |
| btcFrom      | String   | 是    | 充值来源地址，可能会没有                        |
| btcTo        | String   | 是    | 充值到账地址                              |
| addHash      | String   | 是    | 充值交易号，并非每一笔唯一                       |
| number       | number   | 是    | 充值到账金额                              |
| sendimeTime  | datetime | 是    | 商户平台接收到充值的时间                        |
| status       | int      | 是    | 0未到账，1充值失败，2已到账                     |
| configTime   | datetime | 是    | 商户平台给充值入账的时间                        |
| banlance     | number   | 是    | 预留字段                                |
| entrustId    | number   | 是    | 预留字段                                |
| price        | number   | 是    | 预留字段                                |
| fees         | number   | 是    | 充值手续费                               |
| wallet       | String   | 是    | 充值钱包标识                              |
| sumBtc       | String   | 是    | 预留字段                                |
| confirmTimes | int      | 是    | 确认次数，如果通知的次数已经达到商户需要的确认入账次数，可以给用户入账 |
| remark       | String   | 是    | 预留字段                                |
| sucConfirm   | int      | 是    | 0充值未入账，1充值成功并入账                     |
| timestr      | String   | 是    | 预留字段                                |
| currency     | String   | 是    | 币种                                  |

## 6. 全局返回码
	code_1000(1000, "操作成功", "success"),
	code_1001(1001, "一般错误提示", "error tips"),
	code_1002(1002, "内部错误", "Internal Error"),
	code_1003(1003, "验证不通过", "Validate No Pass"),
	code_1004(1004, "资金密码锁定", "Safe Password Locked"),
	code_1005(1005, "资金密码错误", "Safe Password Error"),
	code_1006(1006, "实名认证等待审核或审核不通过", "Audit Or Audit No Pass"),
	code_1007(1007 , "IP验证出现错误，用户设定ip规则有问题", "IP validation errors, you set the rules in question ip"),
	
	code_2001(2001 , "账户余额不足" , "Insufficient Balance"),
	code_2002(2002 , "货币参数currency出错" , "Insufficient Balance"),
	
	code_3001(3001 , "挂单没有找到" , "Not Found Order"),
	code_3002(3002 , "无效的金额" , "Invalid Money"),
	code_3003(3003 , "无效的数量" , "Invalid Amount"),
	code_3004(3004 , "用户不存在" , "No Such User"),
	code_3005(3005 , "无效的参数" , "Invalid Arguments"),
	code_3006(3006 , "无效的IP或与绑定的IP不一致", "Invalid Ip Address"),
	code_3007(3007 , "请求时间已失效", "Invalid Ip Request Time"),
	code_3008(3008 , "本项API请求已经被商户关闭，请联系管理员", "This item merchant API request has been closed, please contact the administrator"),
	code_3009(3009 , "此接口已停用", "This interface is disabled"),
	
	code_4001(4001 , "API接口被锁定或未启用", "API Locked Or Not Enabled"),
	code_4002(4002 , "请求过于频繁", "Request Too Frequently"),
	
	code_4003(4003 , "用户交易已被锁定，当前不能进行交易", "The state has been locked, the current can not be traded"),
	code_4005(4005 , "有借贷，不能买入BTQ", "Borrowing, can't buy BTQ"),
	
	code_5001(5001 , "禁止BIT资金转出", "Disable BIT money out"),
	code_5002(5002 , "获取地址数不足", "Get addresses inadequate"),
	code_5003(5003 , "地址不存在", "Address not exist"),
	code_5004(5004 , "提现已经通知过，请勿重复通知", "Withdrawals have been notified, do not repeat notification"),
	code_5005(5005 , "重复的客户端订单号", "Withdrawals have been notified, do not repeat notification"),
	code_5006(5006, "汇款单不存在，或已被取消", "Money order does not exist or has been canceled"),
	code_5007(5007, "充值方式已经关闭", "Remittance has been closed"),
	code_5008(5008, "汇款单已被处理，不能取消", "Money order does not exist or has been canceled"),
	
	code_6001(6001, "无效的提币地址", "coin address is invalid"),
	code_6002(6002, "无效的币种", "coin currency is invalid"),
	code_6003(6003, "连续验证签名失败，锁定一小时", "check fail,locked 1 hour"),
	
	code_7001(7001, "请输入移动设备上生成的验证码。", "请输入移动设备上生成的验证码。"),
	code_7002(7002, "您连续输入错误的次数太多，请2小时后再试。", "您连续输入错误的次数太多，请2小时后再试。"),
	code_7003(7003, "输入出错", "输入出错"),
	code_7004(7004, "您连续输入错误的次数太多，请2小时后再试。", "您连续输入错误的次数太多，请2小时后再试。"),
	
	code_8001(8001, "代币不提供充值地址，请直接使用主链的充值地址", "代币不提供充值地址，请直接使用主链的充值地址"),
