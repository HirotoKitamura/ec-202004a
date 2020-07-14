package com.example.ecommerce_a.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.OrderTopping;

/**
 * 注文トッピング情報を操作するリポジトリ.
 * 
 * @author hyoga.ito
 *
 */
@Repository
public class OrderToppingRepository {
//	/** 値をセットするRowMapper */
//	private final RowMapper<OrderTopping> OREDER_TOPPING_ROW_MAPPER = (rs, i) -> {
//		OrderTopping orderTopping = new OrderTopping();
//		Topping topping= new Topping();
//		orderTopping.setId(rs.getInt("ot_id"));
//		orderTopping.setToppingId(rs.getInt("topping_id"));
//		orderTopping.setOrderItemId(rs.getInt("order_item_id"));
//		topping.setId(rs.getInt("t_id"));
//		topping.setName(rs.getString("name"));
//		topping.setPriceM(rs.getInt("price_m"));
//		topping.setPriceL(rs.getInt("price_L"));
//
//		orderTopping.setTopping(topping);
//
//		return orderTopping;
//
//	};

	/** SQL実行用変数 */
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 注文トッピングを挿入するリポジトリ.
	 * 
	 * @param orderTopping 注文トッピング
	 */
	public void insert(OrderTopping orderTopping) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		String sql = "insert into order_toppings(topping_id,order_item_id) " 
					+ "values(:toppingId,:orderItemId);";

		template.update(sql, param);

	}
	
	/**
	 * 注文IDから、注文トッピングを削除する.
	 * 
	 * @param orderItemid 注文ID
	 */
	public void deleteByOrderItemId(Integer orderItemid) {
		String sql="delete from order_toppings where order_item_id=:order_item_id;";
		SqlParameterSource param = new MapSqlParameterSource("order_item_id",orderItemid);
		template.update(sql, param);
		
	}
	
//	/**
//	 * 注文商品IDから、注文トッピング情報一覧を取得する.
//	 * 
//	 * @param orderItemid 注文商品ID
//	 * @return　注文トッピング情報一覧
//	 */
//	public List<OrderTopping> findByOrderItemId(Integer orderItemId){
//		String sql="select ot.id as ot_id,topping_id,order_item_id,t.id as t_id,name,price_m,price_l "
//				+ " from order_toppings as ot"
//				+ "left join toppings as t on ot.topping_id=t.id"
//				+ "where order_item_id=:order_item_id order by ot.id;";
//		SqlParameterSource param = new MapSqlParameterSource("order_item_id",orderItemId);
//		List<OrderTopping> orderToppings=template.query(sql,param , OREDER_TOPPING_ROW_MAPPER);
//		return orderToppings;
//	}

}
