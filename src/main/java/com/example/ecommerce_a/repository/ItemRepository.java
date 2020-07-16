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
		item.setStatus(rs.getInt("status"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 販売されている商品一覧を表示する.
	 * 
	 * @param name  検索名
	 * @param order 並び順(デフォルトではasc=安い順)
	 * @return 条件に合致する商品一覧
	 */
	public List<Item> findByFuzzyName(String name, String order) {
		if (name == null) {
			name = "";
		}
		String sql = "select id, name, description, price_m, price_l, image_path, status "
				+ "from items where name ilike :name and status != 2";
		name = "%" + name + "%";
		if ("iddesc".equals(order)) {// 新着順--id降順desc
			order = "order by id desc;";

		} else if ("idasc".equals(order)) {// 古い順--id昇順asc
			order = "order by id asc;";

		} else if ("pricedesc".equals(order)) {// 価格の高い順
			order = "order by price_m desc;";

		} else if ("priceasc".equals(order)) {// 価格の安い順
			order = "order by price_m asc;";

		} else {// 初期動作
			order = "order by price_m;";
		}

		sql += order;

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("order", order);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 販売停止中の商品一覧を表示する.
	 * 
	 * @param name  検索名
	 * @param order 並び順(デフォルトではasc=安い順)
	 * @return 条件に合致する商品一覧
	 */
	public List<Item> findSuspendedByFuzzyName(String name, String order) {
		if (name == null) {
			name = "";
		}
		String sql = "select id, name, description, price_m, price_l, image_path, status "
				+ "from items where name ilike :name where status = 2";
		name = "%" + name + "%";
		if ("iddesc".equals(order)) {// 新着順--id降順desc
			order = "order by id desc;";

		} else if ("idasc".equals(order)) {// 古い順--id昇順asc
			order = "order by id asc;";

		} else if ("pricedesc".equals(order)) {// 価格の高い順
			order = "order by price_m desc;";

		} else if ("priceasc".equals(order)) {// 価格の安い順
			order = "order by price_m asc;";

		} else {// 初期動作
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
		String sql = "select id, name, description, price_m, price_l, image_path, status from items where id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", itemId);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		System.out.println(item.getId());
		return item;
	}

	/**
	 * 検索に該当するカレーの数を取得する．
	 * 
	 * @param name 検索する名前（空文字: 全件検索）
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

	/**
	 * 新しい商品を挿入する.
	 * 
	 * @param item 商品
	 */
	public void insertItem(Item item) {
		String sql1 = "select max(id)+1 from items";
		Integer newId;
		try {
			newId = template.queryForObject(sql1, new MapSqlParameterSource(), Integer.class);
		} catch (Exception e) {
			newId = 1;
		}
		item.setId(newId);
		String sql2 = "insert into items values (:id,:name,:description,:priceM,:priceL,:imagePath,:status)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql2, param);
	}

	/**
	 * 商品を削除する.
	 * 
	 * @param id 商品ID
	 */
	public void deleteItem(Integer id) {
		String sql = "delete from items where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

	/**
	 * 商品の削除フラグを変更する.
	 * 
	 * @param id      商品ID
	 * @param deleted 削除フラグ
	 */
	public void setDeleteFlag(Integer id, Integer status) {
		String sql = "update items set status=:status where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("status", status);
		template.update(sql, param);
	}
};
