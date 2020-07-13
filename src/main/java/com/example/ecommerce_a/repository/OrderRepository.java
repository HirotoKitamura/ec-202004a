package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;
import com.example.ecommerce_a.domain.Topping;
import com.example.ecommerce_a.domain.User;

/**
 * 注文テーブルを操作するリポジトリ.
 * 
 * @author hyoga.ito
 *
 */
@Repository
public class OrderRepository {
	
	
	/** 値セット用RowMapper */
	private final ResultSetExtractor<Order> ORDER_RS_EXT =(rs)->{
		List<OrderItem> orderItems = new ArrayList();
		List<OrderTopping> orderToppings = new ArrayList();
		List<Topping> toppings = new ArrayList();
		Order order = null;
		OrderItem orderItem = null;
		OrderTopping orderTopping = null;
		Item item = null;
		Topping topping = null;
		
		if(rs.next()) {
		order=new Order();
		order.setId(rs.getInt("o_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTell(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		// TODO　正しい値をセットする（サービスでやるかも）
		User user=null;
		order.setUser(user);
		}
		
		int beforeOrderItemId = -1;
		
		while(rs.next()) {
			int nowOrderItemId=rs.getInt("oi_id");
			
			if(beforeOrderItemId != nowOrderItemId) {
				orderItem=new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("item_id"));
				orderItem.setOrderId(rs.getInt("order_id"));
				orderItem.setQuantitiy(rs.getInt("quantity"));
				char[] chars=rs.getString("size").toCharArray();
				orderItem.setSize(chars[0]);
				
				item=new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("image_path"));
				topping = new Topping();
				item.setToppingList(toppings); 
				orderItem.setItem(item);
				
				orderItem.setOrderToppingList(orderToppings);
				
				orderItems.add(orderItem);
				
			}
			
			if(rs.getInt("ot_id") != 0) {
				orderTopping=new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_item_id"));
				
				topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_L"));
				
				orderTopping.setTopping(topping);
				
				toppings.add(topping);
				orderToppings.add(orderTopping);
				
			}
			
			beforeOrderItemId=nowOrderItemId;
			
		}
		
		return order;
		
	};
	
	/** SQL実行用変数 */
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 注文情報を挿入する.
	 * 
	 * @param order 注文内容
	 */
	public void insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		
		String sql="insert into orders(user_id,status,total_price,order_date,"
				+ "destination_name,destination_email,destination_zipcode,"
				+ "destination_address,destination_tel,delivery_time,"
				+ "payment_method)  values(:user_id,:status"
				+ ":total_price,:order_date,:destination_name,:destination_email,"
				+ ":destination_zipcode,:destination_address,:destination_tel,"
				+ ":delivery_time,:payment_method);";

		template.update(sql, param);
	}
	
	/**
	 * ユーザーIDとステータス（0=注文前）から、注文情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return　注文情報
	 */
	public Order findByUserIdAndStatus0(Integer userId) {
		String sql="select o.id as o_id,user_id,status,total_price,order_date," 
					+ "destination_name,destination_email,destination_zipcode,"  
					+ "destination_address,destination_tel,delivery_time,payment_method,"
					+ "oi.id as oi_id,item_id,order_id,quantity,size,"
					+ "i.id as i_id,i.name as i_name,description,i.price_m as i_price_m,"
					+ "i.price_l as i_price_l,image_path,deleted,"
					+ "ot.id as ot_id,topping_id,order_item_id,"
					+ "t.id as t_id,t.name as t_name,"
					+ "t.price_m as t_price_m,t.price_l as t_price_l "
					+ "from orders as o left join order_items as oi on o.id=oi.order_id "
					+ "left join items as i on oi.item_id=i.id "
					+ "left join order_toppings as ot on oi.id=ot.order_topping_id "
					+ "left join toppings as t on t.id=ot.item_id"
					+ "where user_id=:user_id and status=0;";
		SqlParameterSource param = new MapSqlParameterSource("user_id",userId);
		
		Order order=template.query(sql, param, ORDER_RS_EXT);
		
		return order;
		
	}
	
}
