package org.onlineshoppingcart.controller;
 
import java.io.IOException;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.onlineshoppingcart.dao.OrderDAO;
import org.onlineshoppingcart.dao.ProductDAO;
import org.onlineshoppingcart.entity.Product;
import org.onlineshoppingcart.model.CartInfo;
import org.onlineshoppingcart.model.CustomerInfo;
import org.onlineshoppingcart.model.PaginationResult;
import org.onlineshoppingcart.model.ProductInfo;
import org.onlineshoppingcart.util.Utils;
import org.onlineshoppingcart.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
@Controller
@Transactional
@EnableWebMvc
public class MainController {
 
	public static Logger logger = Logger.getLogger(MainController.class);
    @Autowired
    private OrderDAO orderDAO;
 
    @Autowired
    private ProductDAO productDAO;
 
    @Autowired
    private CustomerValidator customerValidator;
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
 
        if (target.getClass() == CartInfo.class) {
 
        }
        else if (target.getClass() == CustomerInfo.class) {
            dataBinder.setValidator(customerValidator);
        }
 
    }
 
    @RequestMapping("/403")
    public String accessDenied() {
    	  logger.error("Access Denied");
        return "/403";
    }
 
    @RequestMapping("/")
    
    public String home() {
        return "index";
    }
 
    @RequestMapping({ "/shop" })
    public String listProductHandler(Model model, //
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;
 
        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
                maxResult, maxNavigationPage, likeName);
 
        model.addAttribute("paginationProducts", result);
        return "shop";
    }
 
    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model, //
            @RequestParam(value = "code", defaultValue = "") String code) {
 
        Product product = null;
        if (code != null && code.length() > 0) {
            product = productDAO.findProduct(code);
        }
        if (product != null) {
 
            CartInfo cartInfo = Utils.getCartInSession(request);
 
            ProductInfo productInfo = new ProductInfo(product);
 
            cartInfo.addProduct(productInfo, 1);
        }
        return "redirect:/cart";
    }
 
    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
            @RequestParam(value = "code", defaultValue = "") String code) {
        Product product = null;
        if (code != null && code.length() > 0) {
            product = productDAO.findProduct(code);
        }
        if (product != null) {
 
            CartInfo cartInfo = Utils.getCartInSession(request);
 
            ProductInfo productInfo = new ProductInfo(product);
 
            cartInfo.removeProduct(productInfo);
 
        }
        return "redirect:/cart";
    }
 
    @RequestMapping(value = { "/cart" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
            Model model, //
            @ModelAttribute("cartForm") CartInfo cartForm) {
 
        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);
 
        return "redirect:/cart";
    }
 
    @RequestMapping(value = { "/cart" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);
 
        model.addAttribute("cartForm", myCart);
        return "cart";
    }
 
    @RequestMapping(value = { "/cartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
 
        CartInfo cartInfo = Utils.getCartInSession(request);
      
        if (cartInfo.isEmpty()) {
             
            return "redirect:/cart";
        }
 
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        if (customerInfo == null) {
            customerInfo = new CustomerInfo();
        }
 
        model.addAttribute("customerForm", customerInfo);
 
        return "cartCustomer";
    }
 
    @RequestMapping(value = { "/cartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, //
            Model model, //
            @ModelAttribute("customerForm") @Validated CustomerInfo customerForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
  
        if (result.hasErrors()) {
            customerForm.setValid(false);
            return "cartCustomer";
        }
 
        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
 
        cartInfo.setCustomerInfo(customerForm);
 
        return "redirect:/cartConfirmation";
    }
 
    @RequestMapping(value = { "/cartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
 
        if (cartInfo.isEmpty()) {
            return "redirect:/cart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/cartCustomer";
        }
 
        return "cartConfirmation";
    }
 
    @RequestMapping(value = { "/cartConfirmation" }, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
 
        if (cartInfo.isEmpty()) {
        	return "redirect:/cart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/cartCustomer";
        }
        try {
        	System.out.println("before save");
            orderDAO.saveOrder(cartInfo);
            System.out.println("after save");
            
        } catch (Exception e) {
            return "cartConfirmation";
        }
        Utils.removeCartInSession(request);
         
        Utils.storeLastOrderedCartInSession(request, cartInfo);
 
        return "redirect:/orderConfirmation";
    }
 
    @RequestMapping(value = { "/orderConfirmation" }, method = RequestMethod.GET)
    public String orderConfirmation(HttpServletRequest request, Model model) {
 
        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);
 
        if (lastOrderedCart == null) {
            return "redirect:/cart";
        }
 
        return "orderConfirmation";
    }
 
    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productDAO.findProduct(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
     
}