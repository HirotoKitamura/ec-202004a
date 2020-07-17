package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.OrderItem;

/**
 * 注文商品情報を操作するリポジトリ.
 * 
 * @author hyoga.ito
 *
 */
@Repository
public class OrderItemRepository {
	/** 値をセットするRowMapper */
//	private final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
//		OrderItem orderItem = new OrderItem();
//		orderItem.setId(rs.getInt("id"));
//		orderItem.setItemId(rs.getInt("item_id"));
//		orderItem.setOrderId(rs.getInt("order_id"));
//		orderItem.setQuantitiy(rs.getInt("quantity"));
//		char[] cars = rs.getString("size").toCharArray();
//		orderItem.setSize(cars[0]);
//
//		// TODO 正しい値をセットしたい
//		Item item = null;
//		orderItem.setItem(item);
//
//		// TODO 正しい値をセットしたい
//		List<OrderTopping> orderToppingList = null;
//		orderItem.setOrderToppingList(orderToppingList);
//
//		return orderItem;
//
//	};

	/** SQL実行用変数 */
	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	private static final ResultSetExtractor<List<OrderItem>> ORDER_ITEM_RSE = (rs) -> {
//		OrderItem orderItem = new OrderItem();
//		List<OrderTopping> orderToppingList = new ArrayList<>();
//		while(rs.next()) {
//			
//		}
//		return null;
//	};
//	
//	/** カート内のアイテムを全件検索
//	 * @param orderId
//	 * @return
//	 */
//	public List<OrderItem> findAllInCart(Integer orderId){
//		String sql = "select i.id i_id,i.item_id,i.order_id,i.quantity,i.size,t.id t_id,t.topping_id,t.order_item_id from order_items i left join order_toppings t on i.id = t.order_item_id where order_id = :orderId order by i.id;";
//		SqlParameterSource param = new MapSqlParameterSource("orderId", orderId);
//		return template.query(sql, param, ORDER_ITEM_RSE);
//	}

	/**
	 * 注文商品情報を挿入する.
	 * 
	 * @param orderItem 注文商品情報
	 */
	public OrderItem insert(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		String sql = "insert into order_items(item_id,order_id,quantity,size) values"
				+ "(:itemId,:orderId,:quantity,:size);";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(sql, param, keyHolder, keyColumnNames);

		orderItem.setId(keyHolder.getKey().intValue());

		return orderItem;

	}

	/**
	 * 注文商品IDから、注文商品情報を削除する.
	 * 
	 * @param id 注文商品ID
	 */
	public void deleteById(Integer id) {
		String sql = "delete from order_items where id=:id";
		SqlParameterSource param = new MapSqlParameterSource("id", id);
		template.update(sql, param);
	}

//	/**
//	 * 注文IDから、注文商品一覧を取得する.
//	 * 
//	 * @param orderId　注文ID
//	 * @return　注文商品一覧
//	 */
//	public List<OrderItem> findByOrderId(Integer orderId) {
//		String sql = "select id,item_id,order_id,quantity,size from order_items "
//				+ "where order_id=:order_id order by id";
//		SqlParameterSource param = new MapSqlParameterSource("order_id",orderId);
//		List<OrderItem> orderItems = template.query(sql, param, ORDER_ITEM_ROW_MAPPER);
//		return orderItems;
//	}

	/**
	 * 指定された注文商品に指定量個数を追加.
	 * 
	 * @param id            注文ID
	 * @param extraQuantity 追加する個数
	 */
	public void addQuantity(Integer id, Integer extraQuantity) {
		String sql = "update order_items set quantity=quantity+:extraQuantity where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource("extraQuantity", extraQuantity).addValue("id", id);
		template.update(sql, param);
	}

}
