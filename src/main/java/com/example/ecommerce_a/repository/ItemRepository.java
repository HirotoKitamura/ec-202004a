package com.example.ecommerce_a.repository;

import org.springframework.jdbc.core.RowMapper;
import com.example.ecommerce_a.domain.Item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * 商品情報を扱うリポジトリクラス.
 * 
 * @author junpei.azuma
 *
 */
@Repository
public class ItemRepository {

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品一覧を表示する.
	 * 
	 * @param name  検索名
	 * @param order 並び順(デフォルトではasc=安い順)
	 * @return 条件に合致する商品一覧
	 */
	public List<Item> findByFuzzyName(String name, String order) {
		if (name == null) {
			name = "";
		}
		String sql = "select id, name, description, price_m, price_l, image_path, deleted "
				+ "from items where name like :name ";
		name = "%" + name + "%";
		if ("desc".equals(order)) {
			order = "order by price_m desc;";
		} else {
			order = "order by price_m;";
		}
		sql += order;
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("order", order);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 受け取ったidで商品を検索.
	 * 
	 * @param id 商品id
	 * @return 表示する商品
	 */
	public Item load(Integer itemId) {
		System.out.println(itemId);
		String sql = "select id, name, description, price_m, price_l, image_path, deleted from items where id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", itemId);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		System.out.println(item.getId());
		return item;
	}

	/**
	 * 検索に該当するカレーの数を取得する．
	 * 
	 * @param name 検索する名前（空文字:　全件検索）
	 * @return 検索hit数
	 */
	public int itemHitSizeByFuzzyName(String name) {
		if (name == null) {
			name = "";
		}
		String sql = "select count(*) " + "from items where name like :name";

		name = "%" + name + "%";

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		int itemsize = template.queryForObject(sql, param, Integer.class);

		return itemsize;
	}
};
