package Controller;

import Entity.Admin;
import Service.AdminService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/verifyCredentials")
    public String verifyCredentials(@ModelAttribute("admin")Admin admin, Model model){
        if (adminService.verifyCredentials(admin.getEmail(),admin.getPassword())){
            return "";
        }
        model.addAttribute("erro", "Email ou senha inváldos");
        return "Login";
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


}
