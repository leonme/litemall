package org.linlinjava.litemall.core;

import org.apache.ibatis.reflection.ArrayUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.notify.NotifyType;
import org.linlinjava.litemall.core.util.DateTimeUtil;
import org.linlinjava.litemall.core.util.StringConstants;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.service.LitemallOrderGoodsService;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 测试微信发送服务
 * <p>
 * 注意LitemallNotifyService采用异步线程操作
 * 因此测试的时候需要睡眠一会儿，保证任务执行
 * <p>
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WeixinNotificationTest {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private LitemallOrderService orderService;

    @Autowired
    private LitemallOrderGoodsService orderGoodsService;

    @Test
    public void testCaptcha() {
        LitemallOrder order = new LitemallOrder();
        order.setOrderSn("000000001");
        order.setPayTime(LocalDateTime.now());
        order.setActualPrice(BigDecimal.TEN);
        order.setOrderStatus(OrderUtil.orderStatus(2).get(0));
        List<String> orderGoods = new ArrayList<>();
        orderGoods.add("商品1");
        orderGoods.add("商品2");
        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[]{
                order.getOrderSn(),
                DateTimeUtil.getDateTimeDisplayString(order.getPayTime()),
                order.getActualPrice().toString(),
                OrderUtil.orderStatusText(order),
                orderGoods.toString()
        };
        notifyService.notifyWxTemplate(StringConstants.OPENID_ME, NotifyType.PAY_SUCCEED, parms);

    }

    @Test
    public void testPaySucceed() {
        LitemallOrder order = new LitemallOrder();
        order.setOrderSn("000000001");
        order.setPayTime(LocalDateTime.now());
        order.setActualPrice(BigDecimal.TEN);
        order.setOrderStatus(OrderUtil.orderStatus(2).get(0));
        List<String> orderGoods = new ArrayList<>();
        orderGoods.add("商品1");
        orderGoods.add("商品2");
        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[]{
                order.getOrderSn(),
                DateTimeUtil.getDateTimeDisplayString(order.getPayTime()),
                order.getActualPrice().toString(),
                OrderUtil.orderStatusText(order),
                orderGoods.toString()
        };
        notifyService.notifyWxTemplate(StringConstants.OPENID_KF, NotifyType.PAY_SUCCEED, parms);
    }

    @Test
    public void testShip() {
        LitemallOrder order = new LitemallOrder();
        order.setOrderSn("000000001");
        order.setPayTime(LocalDateTime.now());
        order.setActualPrice(BigDecimal.TEN);
        order.setOrderStatus(OrderUtil.orderStatus(2).get(0));
        List<String> orderGoods = new ArrayList<>();
        orderGoods.add("商品1");
        orderGoods.add("商品2");
        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[]{
                order.getOrderSn(),
                DateTimeUtil.getDateTimeDisplayString(order.getPayTime()),
                order.getActualPrice().toString(),
                OrderUtil.orderStatusText(order),
                orderGoods.toString()
        };
        notifyService.notifyWxTemplate(StringConstants.OPENID_KF, NotifyType.SHIP, parms);
    }

    @Test
    public void testRefund() {
        LitemallOrder order = new LitemallOrder();
        order.setOrderSn("000000001");
        order.setPayTime(LocalDateTime.now());
        order.setActualPrice(BigDecimal.TEN);
        order.setOrderStatus(OrderUtil.orderStatus(2).get(0));
        List<String> orderGoods = new ArrayList<>();
        orderGoods.add("商品1");
        orderGoods.add("商品2");
        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[]{
                order.getOrderSn(),
                DateTimeUtil.getDateTimeDisplayString(order.getPayTime()),
                order.getActualPrice().toString(),
                OrderUtil.orderStatusText(order),
                orderGoods.toString()
        };
        notifyService.notifyWxTemplate(StringConstants.OPENID_KF, NotifyType.REFUND, parms);
    }

    @Test
    public void testDelivery() {
        LitemallOrder order = orderService.findById(33);
        List<LitemallOrderGoods> goods = orderGoodsService.queryByOid(33);
        List<String> orderGoods = new ArrayList<>();
        for (LitemallOrderGoods good: goods) {
            orderGoods.add(good.getGoodsName() + " " + good.getNumber() + "件\n");
        }
        // 请依据自己的模版消息配置更改参数
        String[] parms = new String[]{
                order.getOrderSn(),
                "未知",
                OrderUtil.orderStatusText(order),
                order.getConsignee(),
                orderGoods.toString(),
                order.getMobile(),
                order.getAddress(),
                "无",
                DateTimeUtil.getDateTimeDisplayString(order.getAddTime())
        };
        System.out.println(Arrays.toString(parms));
        notifyService.notifyWxTemplate(StringConstants.OPENID_KF, NotifyType.DELIVERY, parms);
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
