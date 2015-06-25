package com.agilemaster.findoil.bootstrap

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service

import com.agilemaster.findoil.domain.ProductCatalog;
import com.agilemaster.findoil.repository.ProductCatalogRepository;

@Service
class ProducCatalogService {
	@Autowired
	ProductCatalogRepository productCatalogRepository;
	public void init(){
		if (productCatalogRepository.count() == 0){
			int theNumber = 1;
			// 基础油数据类型初始化
			ProductCatalog productCatalogRoot = new ProductCatalog();
			productCatalogRoot.setName("润滑油");
			productCatalogRoot.setDescription(null);
			productCatalogRoot.setOnline(true);
			productCatalogRoot.setParentCataLog(null);
			productCatalogRoot.setSequence(theNumber);
			productCatalogRoot.setLockProduct(false);
			productCatalogRoot.setOnlinePay(true);
			theNumber = theNumber +5
			productCatalogRoot.setOnline(true);
			productCatalogRepository.save(productCatalogRoot);
			// 基础油二级分类插入 I 类，II 类， III 类， IV 类，V 类。
			def levelTwo = ["汽油机油标号","机油级别"]
			def levelThree= [["5W-40", "10W-40", "5W-30","15W-40","20W-50","0W-40","15W-20"]
					,["SN/CF","SN sl","SL/GF-3","SJ","CF-4","SM","SF","CH-4","CD","CJ-4","CI-4","SN/GF-5","SA","SE" ]]
			def secondCatalog = []
			def threeCatalog = []
			def temp;
			levelTwo.eachWithIndex {it,index->
				theNumber = theNumber + 5*(index+1)
				threeCatalog = []
				temp = new ProductCatalog();
				temp.setName(it);
				temp.setDescription(null);
				temp.setOnline(true);
				temp.setParentCataLog(productCatalogRoot);
				temp.setSequence(theNumber);
				temp.setOnline(true);
				temp = productCatalogRepository.save(temp);
				
				ProductCatalog temp1
				levelThree[index].each {levelThreeName->
					theNumber = theNumber + 5*(index+1)
					temp1 = new ProductCatalog();
					temp1.setName(levelThreeName);
					temp1.setDescription(null);
					temp1.setOnline(true);
					temp1.setParentCataLog(temp);
					temp1.setSequence(theNumber);
					temp1.setOnline(true);
					productCatalogRepository.save(temp1);
					threeCatalog.add(temp1);
				}
				
				temp.setChildrenCatalogs(threeCatalog);
				temp = productCatalogRepository.save(temp);
				secondCatalog.add(temp);
			}
			productCatalogRoot.setChildrenCatalogs(secondCatalog);
			productCatalogRepository.save(productCatalogRoot);
		}
	}

}
