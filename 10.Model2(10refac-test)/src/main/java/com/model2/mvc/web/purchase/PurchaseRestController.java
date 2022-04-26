package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;


//==> 회원관리 RestController
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;	
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public PurchaseRestController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value="json/addProduct", method=RequestMethod.POST)
	public Product addProduct(@RequestBody Product product) throws Exception{
		
		System.out.println("/product/json/addProduct : POST");
		
		System.out.println("::" + product);
	
		int resultRow = productService.addProduct(product);
		
		System.out.println("resultRow : " + resultRow);
		
		return product;
	}
	
	@RequestMapping(value="json/addPurchase", method=RequestMethod.GET)
	public Purchase addPurchase(@RequestBody Product product,
								HttpSession session) throws Exception{
		
		System.out.println("/purchase/json/addPurchase : GET");
		
		product = productService.getProduct(product.getProdNo());
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		
		purchase.setBuyer((User)session.getAttribute("user"));
		
		return purchase;
	}
	
	@RequestMapping(value="json/addPurchase", method=RequestMethod.POST)
	public Purchase addPurchase(@RequestBody Purchase purchase,
								@RequestBody Product product,
								HttpSession session) throws Exception{
		
		System.out.println("/purchase/json/addPurchase : POST");
		
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(product);
		
		purchaseService.addPurchase(purchase);
		
		return purchase;
	}
	
	@RequestMapping(value="json/getPurchase", method=RequestMethod.GET)
	public Purchase getPurchase(@RequestBody Purchase purchase) throws Exception{
		
		System.out.println("/purchase/json/getPurchase : GET");
		
		purchase = purchaseService.getPurchase(purchase.getTranNo());
	
		return purchase;
	}
	
	@RequestMapping(value="json/listPurchase")
	public Map<String, Object> listPurchase(@RequestBody Search search,
											HttpSession session) throws Exception{
		System.out.println("/purchase/json/listPurchase : GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, ((User)session.getAttribute("user")).getUserId());
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		map.put("resultPage", resultPage);
		map.put("search", search);
		
		return map;
	}
	
	@RequestMapping(value="json/listSale")
	public Map<String, Object> listSale(@RequestBody Search search) throws Exception{
		System.out.println("/purchase/json/listSale : GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = purchaseService.getSaleList(search);
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		map.put("resultPage", resultPage);
		map.put("search", search);
		
		return map;
	}
	
	@RequestMapping(value="json/updatePurchase", method=RequestMethod.GET)
	public Purchase updatePurchase(@RequestBody Product product,
								   @RequestBody Purchase purchase,
								   HttpSession session) throws Exception{
		
		System.out.println("/purchase/json/updatePurchase : GET");
		
		product = productService.getProduct(product.getProdNo());
		purchase = purchaseService.getPurchase(purchase.getTranNo());
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer((User)session.getAttribute("user"));
		
		return purchase;
	}
	
	@RequestMapping(value="json/updatePurchase", method=RequestMethod.POST)
	public Purchase updatePurchase(@RequestBody Purchase purchase,
								   @RequestBody Product product,
								   HttpSession session) throws Exception{
		
		System.out.println("/purchase/json/updatePurchase : POST");
		
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(product);
		
		purchase = purchaseService.updatePurchase(purchase);
		
		return purchase;
	}
	
	@RequestMapping(value="json/updateTranCode/{menu}", method=RequestMethod.GET)
	public void updateTranCode(@RequestBody Purchase purchase) throws Exception{
		
		System.out.println("/purchase/json/updateTranCode : GET");
		
		purchaseService.UpdateTranCode(purchase);
	}
}