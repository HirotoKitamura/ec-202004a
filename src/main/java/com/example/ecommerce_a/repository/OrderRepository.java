package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Order;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.User;

@Repository
public class OrderRepository {
	
	private final RowMapper<Order> ORDER_ROW_MAPPER =(rs,i)->{
		Order order=new Order();
		order.setId(rs.getInt("id"));
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
		
		// TODO　正しい値をセットする
		User user=null;
		order.setUser(user);
		// TODO　正しい値をセットする
		List<OrderItem> orderItemList=null;
		order.setOrderItemList(orderItemList);
		
		
		return order;
		
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public void insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		
		String sql="insert into(user_id,status,total_price,order_date,"
				+ "destination_name,destination_email,destination_zipcode,"
				+ "destination_address,destination_tel,delivery_time,"
				+ "payment_method)  orders values(:user_id,:status"
				+ ":total_price,:order_date,destination_name,destination_email,"
				+ "destination_zipcode,destination_address,destination_tel,"
				+ "delivery_time,payment_method);";

		template.update(sql, param);
	}
	
	public Order findByUserIdAndStatus0(Integer userId) {
		String sql="id,select user_id,status,total_price,order_date," 
					+ "destination_name,destination_email,destination_zipcode,"  
					+ "destination_address,destination_tel,delivery_time," 
					+ "payment_method from orders where user_id=:user_id and status=0";
		SqlParameterSource param = new MapSqlParameterSource("user_id",userId);
		
		Order order=template.queryForObject(sql, param, ORDER_ROW_MAPPER);
		
		return order;
		
	}
	
}
