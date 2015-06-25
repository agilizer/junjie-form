package com.agilemaster.findoil.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Comment;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.ProductCache;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.OrderRepository;
import com.agilemaster.findoil.repository.ProductRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;
import com.agilemaster.share.service.JdbcPage;

@Service
public class CommentServiceImpl  implements CommentService{
	@Autowired
	JpaShareService jpaShareService;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserService	 userService;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductCacheService productCacheService;

	@Override
	public JdbcPage listByProduct(Long productId, int max, int offset) {
		String hql = "select c FROM Product p join p.comments c WHERE p.id=:productId order by   c.dateCreated desc ";
		String countHql = "select count(c.id) FROM Product p join p.comments c WHERE p.id=:productId";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("productId", productId);
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public boolean checkComment(Product product, User user) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("productId", product.getId());
		parameterMap.put("userId", user.getId());
		@SuppressWarnings("unchecked")
		List<Long> countList = (List<Long>) jpaShareService.queryForHql("select count(o.id) from Order o join o.user user join o.product p "+
				" where user.id=:userId and p.id=:productId and o.canComment=true", parameterMap);
		long count = countList.get(0);
		return count==0?false:true;
	}

	@Override
	public Map<String, Object> product(Long productId, String content,String star) {
		Map<String,Object> result = StaticMethod.genResult();
		if(content!=null&&content.trim()!=null){
			User user = userService.currentUser();
			if(user!=null){
				Product product =productRepository.findOne(productId);
				if(product!=null){
					Comment comment = new Comment();
					comment.setAuthor(user);
					comment.setContent(content);
					comment.setDateCreated(Calendar.getInstance());
					comment.setStar(star);
					jpaShareService.save(comment);
					List<Comment> commentList = product.getComments();
					if(commentList==null){
						commentList = new ArrayList<Comment>();
					}
					commentList.add(comment);
					product.setComments(commentList);
					productCacheService.increaseComment(product.getProductCache());
					jpaShareService.save(product);
					result.put(FindOilConstants.SUCCESS, true);
				}else{
					result.put(FindOilConstants.ERROR_MSG, "评论产品已经删除!");
				}
			}else{
				result.put(FindOilConstants.ERROR_MSG, "请登录后再评论!");
			}
		}else{
			result.put(FindOilConstants.ERROR_MSG, "评论内容不能为空!");
		}
		return result;
	}

}
