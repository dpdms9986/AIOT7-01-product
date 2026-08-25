package Service;

import java.util.List;

import DAO.ProductDAO;
import DTO.Product;
public class ProductServiceImpl implements ProductService {
    
    private ProductDAO productDAO = new ProductDAO();

    @Override
    public List<Product> list() {
		List<Product> productList = productDAO.list(); 
		return productList;
    }

    @Override
    public Product select(int no) {
        Product product = productDAO.select(no);
        return product;
    }

    @Override
    public int insert(Product product) {
		int result = productDAO.insert(product);
		if( result > 0 ) System.out.println("상품정보가 등록되었습니다.");
		else System.err.println("상품정보 등록에 실패했습니다.");
		return result;
    }

    @Override
    public int update(Product product) {
		int result = productDAO.update(product);
		if( result > 0 ) System.out.println("상품정보가 수정되었습니다.");
		else System.err.println("상품정보 수정에 실패했습니다.");
		return result;
    }

    @Override
	public int delete(int no) {
		int result = productDAO.delete(no);
		if( result > 0 ) System.out.println("상품정보가 삭제되었습니다.");
		else System.err.println("상품정보 삭제를 실패하였습니다.");
		return result;
	}
}
