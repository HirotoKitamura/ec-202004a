package com.example.ecommerce_a.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.ecommerce_a.domain.Topping;

/**
 * トッピング情報を扱うリポジトリクラス.
 * 
 * @author junpei.azuma
 *
 */
@Repository
public class ToppingRepository {

	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		topping.setDeleted(rs.getBoolean("deleted"));
		return topping;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 販売中のトッピングを全件取得する.
	 * 
	 * @return トッピングのリスト
	 */
	public List<Topping> findOnSale() {
		String sql = "select id, name, price_m, price_l, deleted from toppings where deleted=false order by id;";
		return template.query(sql, TOPPING_ROW_MAPPER);
	}

	/**
	 * 販売停止中のトッピングを全件取得する.
	 * 
	 * @return トッピングのリスト
	 */
	public List<Topping> findSuspended() {
		String sql = "select id, name, price_m, price_l, deleted from toppings where deleted=true order by id;";
		return template.query(sql, TOPPING_ROW_MAPPER);
	}

	/**
	 * 新しいトッピングを挿入する.
	 * 
	 * @param item トッピング
	 */
	public void insertTopping(Topping topping) {
		String sql1 = "select max(id)+1 from toppings";
		Integer newId;
		try {
			newId = template.queryForObject(sql1, new MapSqlParameterSource(), Integer.class);
		} catch (Exception e) {
			newId = 1;
		}
		topping.setId(newId);
		String sql2 = "insert into toppings values (:id,:name,:priceM,:priceL,:deleted)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(topping);
		template.update(sql2, param);
	}

	/**
	 * トッピングの削除フラグを変更する.
	 * 
	 * @param id      トッピングID
	 * @param deleted 削除フラグ
	 */
	public void setDeleted(Integer id, Boolean deleted) {
		String sql = "update toppings set deleted=:deleted where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("deleted", deleted);
		template.update(sql, param);
	}
}
