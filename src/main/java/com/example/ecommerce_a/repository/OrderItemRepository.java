package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Item;
import com.example.ecommerce_a.domain.OrderItem;
import com.example.ecommerce_a.domain.OrderTopping;

@Repository
public class OrderItemRepository {
	private final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantitiy(rs.getInt("quantity"));
		char[] cars = rs.getString("size").toCharArray();
		orderItem.setSize(cars[0]);

		// TODO 正しい値をセットしたい
		Item item = null;
		orderItem.setItem(item);

		// TODO 正しい値をセットしたい
		List<OrderTopping> orderToppingList = null;
		orderItem.setOrderToppingList(orderToppingList);

		return orderItem;

	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	public void insert(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		String sql = "inset into order_items(item_id,order_id,quantity,size) values"
				+ "(:item_id,:order_id,:quantity,:size);";

		template.update(sql, param);
	}

	public void deleteById(Integer id) {
		String sql = "delete from order_items where id=:id";
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		template.update(sql, param);
	}

	public List<OrderItem> findByOrderId(Integer orderId) {
		String sql = "select id,item_id,order_id,quantity,size from order_items "
				+ "where order_id=:order_id order by id";
		SqlParameterSource param = new MapSqlParameterSource("order_id",orderId);
		List<OrderItem> orderItems = template.query(sql, param, ORDER_ITEM_ROW_MAPPER);
		return orderItems;
	}

}
