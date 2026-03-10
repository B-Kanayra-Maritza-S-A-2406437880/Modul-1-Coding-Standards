package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        model.addAttribute("order", new Order());
        return "order/CreateOrder";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, Model model) {
        orderService.createOrder(order);
        model.addAttribute("order", order);
        return "order/CreateOrder";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "order/OrderHistory";
    }

    @PostMapping("/history")
    public String orderHistory(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "order/OrderList";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/PayOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable String orderId,
                           @RequestParam String method,
                           @RequestParam Map<String, String> allParams,
                           Model model) {
        Order order = orderService.findById(orderId);
        Map<String, String> paymentData = new HashMap<>(allParams);
        paymentData.remove("method");

        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("paymentId", payment.getId());
        return "order/PayOrder";
    }
}