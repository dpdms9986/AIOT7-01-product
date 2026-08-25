package Service;

import java.util.List;

import DTO.Product;
/**
 * - 상품 목록
 * - 상품 조회
 * - 상품 등록
 * - 상품 수정
 * - 상품 삭제
 */
public interface ProductService {

	List<Product> list();
	Product select(int no);
	int insert(Product product);
	int update(Product product);
	int delete(int no);

}
