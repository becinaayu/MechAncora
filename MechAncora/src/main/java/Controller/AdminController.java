package Controller;

import Entity.Admin;
import Entity.Order;
import Entity.Product;
import Entity.User;
import Service.AdminService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;


@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/verify/credentials")
    public String verifyCredentials(@ModelAttribute("admin") Admin admin, Model model) {
        if (adminService.verifyCredentials(admin.getEmail(), admin.getPassword())) {
            model.addAttribute("admin", new Admin());
            model.addAttribute("user", new User());
            model.addAttribute("product", new Product());
            return "redirect:/admin/home";
        }
        model.addAttribute("error", "Invalid email or password");
        return "LoginPage";
    }

    @GetMapping("/admin/home")
    public String adminHomePage(Model model){
        model.addAttribute("adminList", adminService.getAllAdmin());
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("orderList", orderService.getAllOrder());
        model.addAttribute("productList", productService.getAllProduct());

        return "AdminHomePage";
    }

    @GetMapping("/add/admin")
    public String createAdmin(){
        return "AddAdmin";
    }

    @PostMapping("/add/admin")
    public String createAdmin(Admin admin){
        adminService.createUser(admin);
        return "/admin/home";
    }

    @GetMapping("/update/admin/{id}")
    public String update(@PathVariable ("id") Long id, Model model){
        model.addAttribute("admin", adminService.getAdminById(id));
        return "UpdateAdmin";
    }

    @PostMapping("/update/admin")
    public String updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);

        return "redirect:/admin/home";
    }

    @DeleteMapping("/delete/admin/{id}")
    public String delete(@PathVariable Long id){
        adminService.deleteAdmin(id);

        return "redirect:/admin/home";
    }

    @GetMapping("/user/home")
    public String userHome(@ModelAttribute("userId") Long userId, @ModelAttribute("error") String error, @ModelAttribute("messageSuccess") String messageSuccess, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("ordersList", orderService.findOrdersByUser(user));
        if (!error.isEmpty()) {
            model.addAttribute("error", error);
        }
        if (!messageSuccess.isEmpty()) {
            model.addAttribute("messageSuccess", messageSuccess);
        }

        return "BuyProductPage";
    }

    @PostMapping("/product/search")
    public String productSearch(String name, Long userId, Model model) {
        Product product = productService.findProductByName(name);
        User user = userService.getUserById(userId);
        model.addAttribute("ordersList", orderService.findOrdersByUser(user));

        if (product != null) {
            model.addAttribute("product", product);
        } else {
            model.addAttribute("messageError", "Sorry, product was not found...");
        }

        model.addAttribute("userId", userId);

        return "BuyProductPage";
}

    @PostMapping("/place/order")
        public String placeOrder(Order order, Long userId, RedirectAttributes redirectAttributes) {
            double totalAmount = order.getPrice() * order.getQuantity();
            order.setAmount(totalAmount);
            order.setDate(new Date());

            User user = userService.getUserById(userId);
            order.setUser(user);

            orderService.createOrder(order);

            redirectAttributes.addAttribute("userId", userId);
            redirectAttributes.addAttribute("messageSuccess", "Pedido realizado com sucesso!!");

            return "redirect:/user/home";
        }
}



