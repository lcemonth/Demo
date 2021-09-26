2021國泰世華面試題目
===

需求項目
---
框架：
* Spring Boot 
* Spring MVC 
* Spring Data Jpa

專案類型：
* Maven

資料庫：
* H2

情境：
* 提供多隻RestFul API，達到以下幾件事：
    1. 新增幣別資料 : 資料內容請參考「幣別資料表」
    2. 更新幣別資料 : 資料內容請參考「幣別資料表」
    3. 刪除幣別資料 : 參數為幣別編號
    4. 查詢員工資料 :「條件」為ID(無指定條件)
    5. 查詢指定API : 呼叫coindesk API，並顯示資料
    6. 查詢指定API : 呼叫coindesk API，並顯示幣別、幣別中文名稱、匯率和更新時間
      > 更新時間格式範例：1990/01/01 00:00:00
---


資料庫設計
---
* 幣別資料表[CURRENCY_TYPE]

|欄位代號        |欄位名稱        |型別 |欄位大小   |必填      |
|--------------|---------------|----------|----------|---------|
|*CURRENCY_ID   |ID             |INTEGER|          |●|
|CURRENCY_CODE |幣別(英文)      |VARCHAR|10        |●|
|CURRENCY_NAME |幣別(中文)      |NVARCHAR|5         |●|


CURRENCY_TYPE.sql
---
```=sql
DROP TABLE IF EXISTS CURRENCY_TYPE;

-- 幣別對應表
CREATE TABLE CURRENCY_TYPE (
	CURRENCY_ID INTEGER PRIMARY KEY auto_increment, -- ID
   	CURRENCY_CODE VARCHAR(10) NOT NULL, -- 幣別(英文)
	CURRENCY_NAME NVARCHAR(5) NOT NULL -- 幣別(中文)
);

INSERT INTO CURRENCY_TYPE(CURRENCY_CODE, CURRENCY_NAME) VALUES ('USD', '美元');
INSERT INTO CURRENCY_TYPE(CURRENCY_CODE, CURRENCY_NAME) VALUES ('GBP', '英鎊');
INSERT INTO CURRENCY_TYPE(CURRENCY_CODE, CURRENCY_NAME) VALUES ('EUR', '歐元');

SELECT * FROM CURRENCY_TYPE;
```

---

API設計
---
* Currency API[CurrencyController]

|Http狀態|URI                |說明
|-------|-------------------|----------|
|POST   |/currencys       |新增幣別資料
|PUT    |/currencys/{currencyID} |更新幣別資料
|DELETE |/currencys/{currencyID} |刪除幣別資料
|Get   |/currencys/{currencyID}|查詢指定幣別資料
|Get   |/currencys/all|查詢全部幣別資料

* Coindesk API[CoindeskController]

|Http狀態|URI                  |說明
|-------|---------------------|----------|
|GET   |/coindesk/v1       |呼叫 coindesk API
|GET |/coindesk/v2 |呼叫 coindesk 資料轉換的 API資訊（幣別，中文名稱，和匯率）


---

URL
---

* URL : http://127.0.0.1:8080/Demo/
* swagger-ui : http://127.0.0.1:8080/Demo/swagger-ui.html#/

---

API 測試
---
 >API 測試工具 : API Tester
 >匯入檔案 :Demo.json
### 1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容。
![](https://i.imgur.com/LuDCul0.png)





### 2. 測試呼叫新增幣別對應表資料 API。
![](https://i.imgur.com/A6CxbYU.png)


 
    驗證
![](https://i.imgur.com/qOLed67.png)



### 3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。
![](https://i.imgur.com/Y9HEEXS.png)

    驗證
![](https://i.imgur.com/C6p5lpj.png)


### 4. 測試呼叫刪除幣別對應表資料 API。
![](https://i.imgur.com/wjFpBGw.png)

    驗證
![](https://i.imgur.com/4t4CZ4j.png)


### 5. 測試呼叫刪除幣別對應表資料 API。
![](https://i.imgur.com/O6Cb8oQ.png)


### 6. 測試呼叫資料轉換的 API，並顯示其內容。
![](https://i.imgur.com/7daKLr8.png)

