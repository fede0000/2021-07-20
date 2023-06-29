package it.polito.tdp.yelp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.yelp.model.Archi;
import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Review;
import it.polito.tdp.yelp.model.User;
import it.polito.tdp.yelp.model.Vertici;

public class YelpDao {

	public List<Business> getAllBusiness(){
		String sql = "SELECT * FROM Business";
		List<Business> result = new ArrayList<Business>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Business business = new Business(res.getString("business_id"), 
						res.getString("full_address"),
						res.getString("active"),
						res.getString("categories"),
						res.getString("city"),
						res.getInt("review_count"),
						res.getString("business_name"),
						res.getString("neighborhoods"),
						res.getDouble("latitude"),
						res.getDouble("longitude"),
						res.getString("state"),
						res.getDouble("stars"));
				result.add(business);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Review> getAllReviews(){
		String sql = "SELECT * FROM Reviews";
		List<Review> result = new ArrayList<Review>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Review review = new Review(res.getString("review_id"), 
						res.getString("business_id"),
						res.getString("user_id"),
						res.getDouble("stars"),
						res.getDate("review_date").toLocalDate(),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("review_text"));
				result.add(review);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getAllUsers(){
		String sql = "SELECT * FROM Users";
		List<User> result = new ArrayList<User>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				User user = new User(res.getString("user_id"),
						res.getInt("votes_funny"),
						res.getInt("votes_useful"),
						res.getInt("votes_cool"),
						res.getString("name"),
						res.getDouble("average_stars"),
						res.getInt("review_count"));
				
				result.add(user);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Vertici> getAllVertices(int nRecen){
		String sql = "select u.`user_id`, COUNT(*) as numRecensioni "
				+ "from Reviews r, Users u "
				+ "where u.`user_id`=r.`user_id` "
				+ "group by u.`user_id` "
				+ "having numrecensioni>=?";
		List<Vertici> result = new ArrayList<Vertici>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nRecen);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				
				result.add( new Vertici(res.getString("user_id"),
						res.getInt("numRecensioni")));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Archi> getAllEdges(int anno, Map<String, Vertici> vertexmap){
		String sql = "select r1.`user_id`, r2.`user_id`, COUNT(*) as numRecensioni\n"
				+ "from Reviews r1 , Reviews r2\n"
				+ "where YEAR(r1.`review_date`)=2008 and YEAR(r2.`review_date`)=2008\n"
				+ "and r1.`user_id` in (select distinct u.`user_id`\n"
				+ "						from Users u, Reviews r\n"
				+ "						where u.`user_id`=r.`user_id`\n"
				+ "						group by r.`user_id`\n"
				+ "						having count(*)>=200)\n"
				+ "and r2.`user_id` in (select distinct u.`user_id`\n"
				+ "						from Users u, Reviews r\n"
				+ "						where u.`user_id`=r.`user_id`\n"
				+ "						group by r.`user_id`\n"
				+ "						having count(*)>=200)\n"
				+ "and r1.`business_id`=r2.`business_id` and r1.`user_id`>r2.`user_id` "
				+ "group by r1.`user_id`, r2.`user_id`";
		List<Archi> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Archi a = new Archi(res.getString("user_id"),res.getString("business_id") ,res.getInt("numRecensioni"), res.getDate("review_date"));
				if(vertexmap.containsKey(a.getUserid())){
				result.add(a);}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
