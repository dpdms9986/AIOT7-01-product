package DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.Product;

/**
 *   데이터 접근 객체
 *   - 상품 데이터를 접근
 */
public class ProductDAO extends JDBConnection {
	
	/**
	 * 상품 목록
	 * @return
	 */
	public List<Product> list() {
		// 상품 목록을 담을 컬렉션 객체 생성
		List<Product> productList = new ArrayList<Product>();
		
		// SQL 작성
		String sql = " SELECT * "
				   + " FROM product ";
		
		try {
			
			// 1. SQL 실행 객체 생성 - Statement (stmt)
			stmt = con.createStatement();
			
			// 2. SQL 실행 요청 -> 결과 ResultSet (rs)
			rs = stmt.executeQuery(sql);
			
			// 3. 조회된 결과를 리스트(productList)에 추가
			while( rs.next() ) {			// next() : 조회 결과의 다음 데이터로 이동
				Product product = new Product();
				
				// 결과 데이터 가져오기
				// rs.getXXX("컬럼명")  : 해당 컬럼의 데이터를 반환
				product.setNo( rs.getInt("no") );
				product.setName( rs.getString("name") );
				product.setName( rs.getString("exdate") );
				product.setCreatedAt( rs.getTimestamp("created_at") );
				product.setUpdatedAt( rs.getTimestamp("updated_at") );
				
				// 상품 목록 추가
				productList.add(product);
			}
			
		} catch (SQLException e) {
			System.err.println("상품 목록 조회 시, 예외 발생");
			e.printStackTrace();
		}
		// 4. 상품 목록 반환
		return productList;
	}

	/**
	 * 데이터 조회
	 * @param no
	 * @return
	 */
	public Product select(int no) {
		
		// 상품 정보 객체 생성
		Product product = new Product();
		
		// SQL 작성
		String sql = " SELECT * "
				   + " FROM product "
				   + " WHERE no = ? "; 	// no 가 ? 인 데이터만 조회
		
		// 데이터 조회 : SQL 실행 객체 생성 -> SQL 실행 요청 -> 조회 결과 -> 반환
		try {
			// SQL 실행 객체 생성 - PreparedStatement (psmt)
			psmt = con.prepareStatement(sql);
			
			// ? 동적 파라미터 바인딩
			// * psmt.setXXX( 순서번호, 매핑할 값 );
			psmt.setInt( 1, no );		// 1번째 ? 파라미터에 매핑
			
			// SQL 실행 요청
			rs = psmt.executeQuery();
			
			// 조회 결과 1건 가져오기
			if( rs.next() ) {
				// 결과 데이터 가져오기
				// rs.getXXX("컬럼명")  : 해당 컬럼의 데이터를 반환
				product.setNo( rs.getInt("no") );
				product.setName( rs.getString("name") );
				product.setExdate( rs.getString("exdate") );
				product.setCreatedAt( rs.getTimestamp("created_at") );
				product.setUpdatedAt( rs.getTimestamp("updated_at") );
			}
			
		} catch (SQLException e) {
			System.err.println("상품 조회 시, 예외 발생");
			e.printStackTrace();
		}
		// 상품 정보 1건 반환
		return product;
	}

	/**
	 * 데이터 등록
	 * @param product
	 * @return
	 */
	public int insert(Product product) {
		int result = 0;			// 결과 : 적용된 데이터 개수
		
		String sql = " INSERT INTO product ( no, exdate ) "
				   + " VALUES( ?, ? ) ";
		
		try {
			psmt = con.prepareStatement(sql);			    // 쿼리 실행 객체 생성
			psmt.setString( 1, product.getName() );		// 1번 ? 에 title() 매핑
			psmt.setString( 2, product.getExdate() );		    // 2번 ? 에 Name(상품명) 매핑
			result = psmt.executeUpdate();				    // SQL 실행 요청
			// * executeUpdate() 
			// SQL(INSERT, UPDATE, DELETE) 실행 시 적용된 데이터 개수를 int 타입으로 받아온다.
			// ex) 상품 1개 적용 성공 시, result : 1 
			//				    실패 시, result : 0
		} catch (SQLException e) {
			System.err.println("상품 등록 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 데이터 수정
	 * @param product
	 * @return
	 */
	public int update(Product product) {
		int result = 0;			// 결과 : 적용된 데이터 개수
		
		String sql = " UPDATE product "
				   + "    SET Name = ? "
				   + "		 ,Exdate = ? "
				   + "		 ,updated_at = now() "
				   + " WHERE no = ? ";
		
		try {
			psmt = con.prepareStatement(sql);			// 쿼리 실행 객체 생성
			psmt.setString( 1, product.getName() );		// 1번 ? 에 Name(상품명) 매핑
			psmt.setString( 2, product.getExdate() );   // 2번 ? 에 Exdate(유통기한)매핑
			psmt.setInt( 4, product.getNo() );			// 3번 ? 에 no(글번호) 매핑
			result = psmt.executeUpdate();				// SQL 실행 요청
			// * executeUpdate() 
			// SQL(INSERT, UPDATE, DELETE) 실행 시 적용된 데이터 개수를 int 타입으로 받아온다.
			// ex) 상품 1개 적용 성공 시, result : 1 
			//				    실패 시, result : 0
		} catch (SQLException e) {
			System.err.println("상품 수정 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 데이터 삭제
	 * @param no
	 * @return
	 */
	public int delete(int no) {
		int result = 0;			// 결과 : 적용된 데이터 개수
		
		String sql = " DELETE FROM product "
				   + " WHERE no = ? ";
		
		try {
			psmt = con.prepareStatement(sql);			// 쿼리 실행 객체 생성
			psmt.setInt( 1, no );						// 1번 ? 에 no(글번호) 매핑
			result = psmt.executeUpdate();				// SQL 실행 요청
			// * executeUpdate() 
			// SQL(INSERT, UPDATE, DELETE) 실행 시 적용된 데이터 개수를 int 타입으로 받아온다.
			// ex) 상품 1개 적용 성공 시, result : 1 
			//				    실패 시, result : 0
		} catch (SQLException e) {
			System.err.println("상품 삭제 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

}
