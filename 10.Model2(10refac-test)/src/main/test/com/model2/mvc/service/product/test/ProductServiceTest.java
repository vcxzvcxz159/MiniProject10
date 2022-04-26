package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;


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
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		
		product.setProdName("testProductName");
		product.setProdDetail("testProdDetail");
		product.setPrice(1111);
		product.setFileName("abcd");
		product.setQuantity(10);

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals(1, productService.addProduct(product));
	}	 
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		
		product = productService.getProduct(10010);

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals(1111, product.getPrice());
		Assert.assertEquals("abcd", product.getFileName());
		Assert.assertEquals(10, product.getQuantity());

	}
	
	//@Test
	public void testUpdateUser() throws Exception{
			 
		Product product = productService.getProduct(10009);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProductName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals(1111, product.getPrice());
		Assert.assertEquals("abcd", product.getFileName());
		Assert.assertEquals(10, product.getQuantity());

		product.setProdName("changetest");
		product.setPrice(500);
		product.setProdDetail("changetest");
		product.setFileName("fucku");
		product.setQuantity(20);
			
		productService.updateProduct(product);
			
		product = productService.getProduct(10010);
		Assert.assertNotNull(product);
			
		//==> console Ȯ��
		//System.out.println(user);
				
		//==> API Ȯ��
		Assert.assertEquals("changetest", product.getProdName());
		Assert.assertEquals("changetest", product.getProdDetail());
		Assert.assertEquals(500, product.getPrice());
		Assert.assertEquals("fucku", product.getFileName());
		Assert.assertEquals(20, product.getQuantity());
	 }
	
	@Test
		 public void testGetUserListAll() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("");
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
		 	//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
	
	
	
}