package org.linlinjava.litemall.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.util.StringConstants;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.service.LitemallOrderGoodsService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 测试短信发送服务
 * <p>
 * 注意LitemallNotifyService采用异步线程操作
 * 因此测试的时候需要睡眠一会儿，保证任务执行
 * <p>
 * 开发者需要确保：
 * 1. 在腾讯云短信平台设置短信签名和短信模板notify.properties已经设置正确
 * 2. 在腾讯云短信平台设置短信签名和短信模板
 * 3. 在当前测试类设置好正确的手机号码
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SmsTest {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private LitemallOrderGoodsService orderGoodsService;

    @Autowired
    private LitemallOrderService orderService;

    @Test
    public void testCaptcha() {
        String phone = "xxxxxxxxxxx";
        String[] params = new String[]{"123456"};

        notifyService.notifySmsTemplate(phone, NotifyType.CAPTCHA, params);
    }

    @Test
    public void testPaySucceed() {
        LitemallOrder order = orderService.findById(33);
        List<String> goods = new ArrayList<>();
        List<LitemallOrderGoods> orderGoods = orderGoodsService.queryByOid(order.getId());
        for (LitemallOrderGoods orderGood: orderGoods) {
            goods.add(orderGood.getGoodsName() + " " + orderGood.getNumber() + "件");
        }
        String[] params = new String[] {
                order.getOrderSn().substring(8, 14),
                order.getConsignee(),
                order.getMobile(),
                order.getAddress(),
                Arrays.toString(goods.toArray())
        };
        notifyService.notifySmsTemplateSync(StringConstants.PHONE_KF, NotifyType.PAY_SUCCEED, params);
    }

    @Test
    public void testShip() {
        String phone = "xxxxxxxxxxx";
        String[] params = new String[]{"123456"};

        notifyService.notifySmsTemplate(phone, NotifyType.SHIP, params);
    }

    @Test
    public void testRefund() {
        LitemallOrder order = orderService.findById(33);
        notifyService.notifyMail("退款申请", order.toString());
        String[] params = new String[]{
                order.getConsignee(),
                order.getOrderSn(),
                order.getActualPrice().toString()
        };
        notifyService.notifySmsTemplate(StringConstants.PHONE_KF, NotifyType.REFUND, params);
    }

    @Configuration
    @Import(Application.class)
    static class ContextConfiguration {
        @Bean
        @Primary
        public Executor executor() {
            return new SyncTaskExecutor();
        }
    }
}
