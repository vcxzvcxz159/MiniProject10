package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;


/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
									"classpath:config/context-aspect.xml",
									"classpath:config/context-mybatis.xml",
									"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		User user = new User();
		user.setUserId("user02");
		user.setUserName("Test");
		user.setPassword("Test");
		System.out.println("user : "+user);
		
		Product product = new Product();
		product.setProdNo(10007);
		product.setPrice(500);
		product.setQuantity(14);
		System.out.println("product : " + product);
		
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("TestRecName");
		purchase.setDivyAddr("����");
		purchase.setQuantity(7);

		//==> console Ȯ��
		System.out.println("pruchase : " + purchase);
		
		//==> API Ȯ��
		Assert.assertEquals(1, purchaseService.addPurchase(purchase));
	}	 
	
	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10000);

		//==> console Ȯ��
		System.out.println(purchase);
		
		//==> API Ȯ��
		Assert.assertEquals(10000, purchase.getTranNo());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals(10007, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals(14, purchase.getQuantity());

	}
	
	@Test
	public void testUpdateUser() throws Exception{
			 
		Purchase purchase = purchaseService.getPurchase(10000);

		Assert.assertNotNull(purchase);
		
		Assert.assertEquals(10000, purchase.getTranNo());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals(10007, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals(5, purchase.getQuantity());

		User user = new User();
		user.setUserId("Test");
		
		purchase.setBuyer(user);
		purchase.setDivyRequest("UpdateRequest");
		purchase.setPaymentOption("1");
		purchase.setReceiverName("UpdateName");
		purchase.setQuantity(5);
			
		purchaseService.updatePurchase(purchase);
			
		purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
			
		//==> console Ȯ��
		//System.out.println(user);
				
		//==> API Ȯ��
		Assert.assertEquals(10000, purchase.getTranNo());
		Assert.assertEquals("user01", purchase.getBuyer().getUserId());
		Assert.assertEquals("UpdateRequest", purchase.getDivyRequest());
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("UpdateName", purchase.getReceiverName() );
		Assert.assertEquals(5, purchase.getQuantity());
	 }
	
	//@Test
		 public void testGetPurchaseList() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = purchaseService.getPurchaseList(search, "user02");
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(2, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
//		 	
//		 	search.setCurrentPage(1);
//		 	search.setPageSize(3);
//		 	search.setSearchCondition("0");
//		 	search.setSearchKeyword("");
//		 	map = productService.getProductList(search);
//		 	
//		 	list = (List<Object>)map.get("list");
//		 	Assert.assertEquals(3, list.size());
//		 	
//		 	//==> console Ȯ��
//		 	//System.out.println(list);
//		 	
//		 	totalCount = (Integer)map.get("totalCount");
//		 	System.out.println(totalCount);
		 }
	
	
	
}