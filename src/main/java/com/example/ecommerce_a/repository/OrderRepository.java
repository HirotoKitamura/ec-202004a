package com.example.ecommerce_a.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	private final ResultSetExtractor<Order> ORDER_RS_EXT = (rs) -> {
		List<OrderItem> orderItems = new ArrayList();
		List<OrderTopping> orderToppings = new ArrayList();
		List<Topping> toppings = new ArrayList();
		Order order = null;
		OrderItem orderItem = null;
		OrderTopping orderTopping = null;
		Item item = null;
		Topping topping = null;

		boolean firstRs = true;

		int beforeOrderItemId = -1;

		while (rs.next()) {
			if (firstRs) {
				order = new Order();
				order.setId(rs.getInt("o_id"));
				order.setUserId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				// TODO 正しい値をセットする（サービスでやるかも）
				User user = null;
				order.setUser(user);
				order.setOrderItemList(orderItems);

				firstRs = false;

			}

			int nowOrderItemId = rs.getInt("oi_id");

			if (beforeOrderItemId != nowOrderItemId) {
				orderItem = new OrderItem();
				orderToppings = new ArrayList<>();
				toppings = new ArrayList<>();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("item_id"));
				orderItem.setOrderId(rs.getInt("order_id"));
				orderItem.setQuantity(rs.getInt("quantity"));

				if (rs.getString("size") != null) {
					char[] chars = rs.getString("size").toCharArray();
					orderItem.setSize(chars[0]);
				} else {
					orderItem.setSize(null);
				}
				item = new Item();
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

			if (rs.getInt("ot_id") != 0) {
				orderTopping = new OrderTopping();
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

			beforeOrderItemId = nowOrderItemId;

		}

		return order;

	};

	private final RowMapper<Integer> MIN_ID_ROW_MAPPER = (rs, i) -> {
		Integer minId = rs.getInt("minId");
		return minId;
	};

	/** SQL実行用変数 */
	@Autowired
	private NamedParameterJdbcTemplate template;

	/** SQL実行用変数(パラメータなし） */
	@Autowired
	private JdbcTemplate noParamTemplate;

	/**
	 * 注文情報を挿入する.
	 * 
	 * @param order 注文内容
	 */
	public Order insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		String sql = "insert into orders(user_id,status,total_price,order_date,"
				+ "destination_name,destination_email,destination_zipcode,"
				+ "destination_address,destination_tel,delivery_time," + "payment_method) "
				+ "values(:userId,:status,:totalPrice,:orderDate,:destinationName,"
				+ ":destinationEmail,:destinationZipcode,:destinationAddress,"
				+ ":destinationTel,:deliveryTime,:paymentMethod);";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(sql, param, keyHolder, keyColumnNames);

		order.setId(keyHolder.getKey().intValue());

		return order;
	}

	/**
	 * ユーザーIDとステータスから、注文情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "select o.id as o_id,user_id,status,total_price,order_date,"
				+ "destination_name,destination_email,destination_zipcode,"
				+ "destination_address,destination_tel,delivery_time,payment_method,"
				+ "oi.id as oi_id,item_id,order_id,quantity,size,"
				+ "i.id as i_id,i.name as i_name,description,i.price_m as i_price_m,"
				+ "i.price_l as i_price_l,image_path,deleted," + "ot.id as ot_id,topping_id,order_item_id,"
				+ "t.id as t_id,t.name as t_name," + "t.price_m as t_price_m,t.price_l as t_price_l "
				+ "from orders as o left join order_items as oi on o.id=oi.order_id "
				+ "left join items as i on oi.item_id=i.id "
				+ "left join order_toppings as ot on oi.id=ot.order_item_id "
				+ "left join toppings as t on t.id=ot.topping_id " + "where user_id=:userId and status=:status;";

		SqlParameterSource param = new MapSqlParameterSource("userId", userId).addValue("status", status);

		Order order = template.query(sql, param, ORDER_RS_EXT);

		return order;

	}

	/**
	 * ユーザーIDから該当する注文群を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return 取得された注文群
	 */
	public List<Order> findOrdersByUser(Integer userId) {
		List<Order> result = new ArrayList<>();

		String sql = "select id from orders where user_id=:userId order by id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Integer> orderIdList = template.queryForList(sql, param, Integer.class);
		String sql2 = "select o.id as o_id,user_id,status,total_price,order_date,"
				+ "destination_name,destination_email,destination_zipcode,"
				+ "destination_address,destination_tel,delivery_time,payment_method,"
				+ "oi.id as oi_id,item_id,order_id,quantity,size,"
				+ "i.id as i_id,i.name as i_name,description,i.price_m as i_price_m,"
				+ "i.price_l as i_price_l,image_path,deleted," + "ot.id as ot_id,topping_id,order_item_id,"
				+ "t.id as t_id,t.name as t_name," + "t.price_m as t_price_m,t.price_l as t_price_l "
				+ "from orders as o left join order_items as oi on o.id=oi.order_id "
				+ "left join items as i on oi.item_id=i.id "
				+ "left join order_toppings as ot on oi.id=ot.order_item_id "
				+ "left join toppings as t on t.id=ot.topping_id "
				+ "where o.id = :orderId and status!=0 and status!=9;";
		for (int orderId : orderIdList) {
			SqlParameterSource param2 = new MapSqlParameterSource().addValue("orderId", orderId);
			Order order = template.query(sql2, param2, ORDER_RS_EXT);
			if (order != null) {
				result.add(order);
			}
		}
		return result;
	}

	/**
	 * 注文IDから注文情報を取得.
	 * 
	 * @param orderId 注文ID
	 * @return 注文情報
	 */
	public Order findByOrderId(Integer orderId) {
		String sql = "select o.id as o_id,user_id,status,total_price,order_date,"
				+ "destination_name,destination_email,destination_zipcode,"
				+ "destination_address,destination_tel,delivery_time,payment_method,"
				+ "oi.id as oi_id,item_id,order_id,quantity,size,"
				+ "i.id as i_id,i.name as i_name,description,i.price_m as i_price_m,"
				+ "i.price_l as i_price_l,image_path,deleted," + "ot.id as ot_id,topping_id,order_item_id,"
				+ "t.id as t_id,t.name as t_name," + "t.price_m as t_price_m,t.price_l as t_price_l "
				+ "from orders as o left join order_items as oi on o.id=oi.order_id "
				+ "left join items as i on oi.item_id=i.id "
				+ "left join order_toppings as ot on oi.id=ot.order_item_id "
				+ "left join toppings as t on t.id=ot.topping_id "
				+ "where o.id = :orderId and status!=0 and status!=9;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		return template.query(sql, param, ORDER_RS_EXT);

	}

	/**
	 * 最小のユーザーIDを取得する.
	 * 
	 * @return 最小のユーザーID
	 */
	public Integer findMinUserId() {
		String sql = "select min(user_id) as minId from orders";
		List<Integer> minIds = template.query(sql, MIN_ID_ROW_MAPPER);
		return minIds.get(0);
	}

	/**
	 * 注文時にordersテーブルの内容を更新する
	 * 
	 */
	public void update(Order order) {
		String sql = "update orders set status= :status, total_price = :totalPrice, order_date = :orderDate, "
				+ "destination_name = :destinationName, destination_email= :destinationEmail, "
				+ "destination_zipcode = :destinationZipcode, destination_address = :destinationAddress, "
				+ "destination_tel = :destinationTel, delivery_time = :deliveryTime, payment_method = :paymentMethod "
				+ "where id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("status", order.getStatus()).addValue("totalPrice", order.getCalcTotalPrice()).addValue("orderDate", order.getOrderDate())
				.addValue("destinationName", order.getDestinationName()).addValue("destinationEmail", order.getDestinationEmail()).addValue("destinationZipcode", order.getDestinationZipcode())
				.addValue("destinationAddress", order.getDestinationAddress()).addValue("destinationTel", order.getDestinationTel())
				.addValue("deliveryTime", order.getDeliveryTime()).addValue("paymentMethod", order.getPaymentMethod()).addValue("id", order.getId());
		template.update(sql, param);
	}

	/**
	 * ユーザーIDをログイン前のものからログイン後のものに更新する.
	 * 
	 * @param guestId ログイン前のID
	 * @param userId  ログイン後のID
	 */
	public void updateUserId(Integer guestId, Integer userId) {
		String sql = "update orders set user_id=:userId where user_id=:guestId and status=0;";
		SqlParameterSource param = new MapSqlParameterSource("userId", userId).addValue("guestId", guestId);
		template.update(sql, param);
	}

	/**
	 * ログインしていないユーザーが発行したユーザーIDに関連する注文情報を削除する.
	 *
	 */
	public void deleteNotLoginUsersOrder() {

		String sql = "WITH deleted AS (DELETE FROM orders WHERE user_id < 0 RETURNING id),"
				+ "deleted2 AS (DELETE FROM order_items" + "where order_id IN (SELECT id FROM deleted) RETURNING id)"
				+ "DELETE FROM order_toppings WHERE order_item_id IN (SELECT id FROM deleted2);";

		noParamTemplate.update(sql);
	}

	/**
	 * 注文IDをログイン前の注文番号からログイン後の注文番号に更新する.
	 * 
	 * @param guestId ログイン前のID
	 * @param userId  ログイン後のID
	 */
	public void updateOrderId(Integer guestId, Integer userId) {
		String sql = "update order_items set order_id = (select id from orders where user_id = :userId and status = 0) "
				+ "where order_id = (select id from orders where user_id = :guestId and status = 0)";
		SqlParameterSource param = new MapSqlParameterSource("userId", userId).addValue("guestId", guestId);
		template.update(sql, param);
	}

	/**
	 * 指定されたユーザーIDの注文を削除する.
	 * 
	 * @param guestId ログイン前のID
	 */
	public void deleteOrder(Integer guestId) {
		String sql = "delete from orders where user_id = :guestId and status = 0;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("guestId", guestId);
		template.update(sql, param);
	}

}
