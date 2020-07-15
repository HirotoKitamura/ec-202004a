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
		return topping;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * トッピングを全件取得する.
	 * 
	 * @return トッピングのリスト
	 */
	public List<Topping> findAll() {
		String sql = "select id, name, price_m, price_l from toppings;";
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
		String sql2 = "insert into toppings values (:id,:name,:priceM,:priceL)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(topping);
		template.update(sql2, param);
	}

	/**
	 * トッピングを削除する.
	 * 
	 * @param id トッピングID
	 */
	public void deleteTopping(Integer id) {
		String sql = "delete from toppings where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
