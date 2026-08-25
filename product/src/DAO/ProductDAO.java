package DAO;

import java.util.ArrayList;
import java.util.List;

import DTO.Product;

// 데이터 접근 객체 - 게시글 데이터를 접근
// JDBConnection 상속
//  - DB접속
//  - JDBC 주요 객체 : Statement, PreparedStatement, ResultSet
public class ProductDAO extends JDBConnection {
    
    /**
     * 데이터 목록
     * - board 테이블의 데이터 전체를 조회 요청
     * - 결과를 컬렉션 리스트에 담아서 반환
     * @return boardList
     */
    public List<Product> list() {
        // 상품 목록을 담을 컬렉션 객체 생성
        List<Product> ProductList = new ArrayList();
        
        // SQL작성
        // 외부자원 접근 시, 예외처리 고려!
        String sql = " SELECT * "
                        + " FROM Product ";

        try { // 데이터베이스의 데이터를 가져오는 try 코드
            // 1.SQL 실행 객체 생성 -> Statement (stmt)
            stmt = con.createStatement();
            // 2.SQL 실행 요청 -> 결과 ResultSet (rs)
            rs = stmt.executeQuery(sql);
            // 3.조회된 결과를 리스트에 추가
            while( rs.next() ){ // next() : 조회 결과의 다음 데이터로 이동.
                // 다음 데이터가 있으면 true, 없으면 false로 반환
                // 즉, 이 while문은 한 행을 읽은 후 계속해서 다음 행을 읽는 코드임

                // 한 행의 데이터를 Product객체로 생성
                Product product = new Product();
                //결과 데이터 가져오기
                // rs.getXXX("컬럼명") : 해당 컬럼의 데이터를 반환
                product.setNo( rs.getInt("no") );
                product.setName( rs.getString("name"));
                product.setExdate( rs.getString("exdate"));
                product.setCreatedAt( rs.getTimestamp("created_at"));
                product.setUpdatedAt( rs.getTimestamp("updated_at"));

                // 상품 목록에 추가 
                ProductList.add(product);
            }
            
            
        } catch (Exception e) { // 데이터베이스를 가져올 때 예외가 발생하면 알려주는 catch코드
            System.err.println("상품 목록 조회 시 예외 발생");
            e.printStackTrace();
            //단계별로 예외 출력을 해줌

        }
        // 4.상품 리스트 반환
        return ProductList;
    }


    /**
     * 데이터 조회
     * java파일 코드 내에서 select객체를 호출할 때,
     * 데이터베이스의 해당 컬럼(예: no, name)과 연결됩니다.
     * @param no - 번호
     * @return product - 게시글
     */
    public Product select(int no){
        // 상품 정보 객체 생성
        Product product = new Product();
        // SQL 작성
        String sql = " SELECT * " + " FROM Product " // Product 테이블 전체 중,
                        + " WHERE no = ? "; // no가 ?인 데이터만 조회
                        // ? : 동적 파라미터
        
        // 데이터 조회 : SQL 실행 객체 생성 -> SQL 실행 요청 -> 조회 결과 -> 반환

        try {
            // SQL 실행 객체 생성 - PreparedStatement (psmt)
            psmt = con.prepareStatement(sql);

            // ? 동적 파라미터 바인딩
            // * psmt.setXXX( 순서번호, 매핑할 값 )
            psmt.setInt(1, no); // 1번째 ? 파라미터에 매핑

            // SQL 실행 요청
            rs = psmt.executeQuery();
            
            // 조회 결과 1건 가져오기
            if( rs.next() ) {
                product.setNo( rs.getInt("no") );
                product.setName( rs.getString("name"));
                product.setExdate( rs.getString("exdate"));
                product.setCreatedAt( rs.getTimestamp("created_at"));
                product.setUpdatedAt( rs.getTimestamp("updated_at"));
            }

        } catch (Exception e) {

            System.err.println("상품 조회 시, 예외 발생");
            e.printStackTrace();
        }

        // 게시글 정보 1건 반환
        return product;

    }
/**
     * 데이터 등록
     * java파일 코드 내에서 insert객체를 호출할 때,
     * 데이터베이스의 마지막 행에 한 행을 삽입합니다.
     * @param product - 상품
     * @return 적용된 데이터의 행의 개수( 1 or 0 )
     */
    // 등록 메소드
    public int insert(Product product){
        int result = 0; // 결과 : 적용된 데이터 개수

        String sql = " INSERT INTO board ( no, name, exdate ) "
                    + " VALUES( BOARD_SEQ.nextval, ?, ? ) ";

        try { // psmt는 ? 파라미터 확장기능을 제공합니다. con은 연결된 드라이버에 sql 실행을 요청합니다.
            psmt = con.prepareStatement(sql);
            psmt.setString(1, product.getName());
            psmt.setString(2, product.getExdate());
            result = psmt.executeUpdate(); 
            // * executeUpdate()
            // SQL(INSERT, UPDATE, DELETE) 실행시 적용된 데이터 개수를 int타입으로 받아온다.
            // ex) 게시글 1개 적용 성공 시, result : 1 실패시 0
        } catch (Exception e) {

            System.err.println("상품 등록 시, 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 데이터 편집
     * java파일 코드 내에서 update객체를 호출할 때,
     * 데이터베이스의 해당 no의 행을 편집합니다.
     * @param product
     * @return 적용된 데이터의 행의 개수( 1 or 0 )
     */
    // 업데이트 메소드
    public int update(Product product){
        int result = 0; // 결과 : 적용된 데이터 개수

        String sql = " UPDATE Product" + " SET name = ? "
                    + ",  exdate = ? "
                    + ",  updated_at = sysdate "
                    + " WHERE no = ? ";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, product.getName());
            psmt.setString(2, product.getExdate());
            psmt.setInt(3, product.getNo());
            result = psmt.executeUpdate(); 
            // * executeUpdate()
            // SQL(INSERT, UPDATE, DELETE) 실행시 적용된 데이터 개수를 int타입으로 받아온다.
            // ex) 게시글 1개 적용 성공 시, result : 1 실패시 0
        } catch (Exception e) {
            System.err.println("상품 편집 시, 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 게시글 삭제 메소드
     * java파일 코드 내에서 delete객체를 호출할 때,
     * 데이터베이스의 해당 no의 행을 삭제합니다.
     * @param no
     * @return
     */
    public int delete(int no) {
        int result = 0;
        String sql = " DELETE FROM Product "
                    + " WHERE no = ? ";
        
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, no);
            result = psmt.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("상품 삭제 시, 예외 발생");
            e.printStackTrace();
        }
        return result;
    }
}
