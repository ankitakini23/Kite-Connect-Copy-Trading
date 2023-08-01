package com.example.copytrade.services;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.ticker.KiteTicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CopyTradingService {


    @Autowired
    KiteTradeLoginAutomation kiteTradeLoginAutomation;

//    @Value("${api.key.master}")
//    String apiKeyMaster;
//    @Value("${api.key.ankita}")
//    String apiKeyChild1 = "xxx";

//    private static final org.slf4j.Logger log
//            = org.slf4j.LoggerFactory.getLogger(CopyTradingService.class);
    public void testKite() {
        log.info("testKite start");
        KiteConnect kiteSdkSelf = kiteTradeLoginAutomation.login();
        if (kiteSdkSelf != null) {
            log.info("kiteSdkSelf : {}", kiteSdkSelf);
            KiteTicker tickerProviderSelf = new KiteTicker(kiteSdkSelf.getAccessToken(), kiteSdkSelf.getApiKey());
            log.info("tickerProviderSelf = {}", tickerProviderSelf);
        }

//        KiteConnect kiteSdkMaster = new KiteConnect(apiKeyMaster);
//        KiteTicker tickerProviderMaster = new KiteTicker(kiteSdkMaster.getAccessToken(), kiteSdkMaster.getApiKey());
//        KiteTicker tickerProviderChild1 = new KiteTicker(kiteSdkChild1.getAccessToken(), kiteSdkChild1.getApiKey());

//          Set listener to get order updates for Master Account.
//        tickerProviderMaster.setOnOrderUpdateListener(order -> {
//            log.info("order update : {}",order.orderId);
//          Place x order for child accounts

        // todo; check order type and do accordingly

        // todo; if sell, check quantity that was bought(according to multiplier etc) ;
//        });


    }
}
