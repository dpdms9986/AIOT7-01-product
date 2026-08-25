import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import DTO.Product;
import Service.ProductService;
import Service.ProductServiceImpl;

public class Main {
	
	static Scanner sc = new Scanner(System.in);					// 입력 객체
	static List<Product> ProductList = null;						// 상품 목록
	static ProductService ProductService = new ProductServiceImpl();	// 비즈니스 로직 객체
	
	/**
	 *  메뉴판
	 */
	public static void menu() {
		System.out.println(":::::::::: 게시판 ::::::::::");
		System.out.println("1. 상품 목록");
		System.out.println("2. 상품 조회");
		System.out.println("3. 상품 등록");
		System.out.println("4. 상품 수정");
		System.out.println("5. 상품 삭제");
		System.out.println("0. 프로그램 종료");
		System.out.print(":::::::::: 번호 입력 : ");
	}
	
	/**
	 *  상품 목록
	 */
	public static void list() {
		System.out.println(":::::::::: 상품 목록 ::::::::::");
		// 상품 목록 데이터 요청
		ProductList = ProductService.list();
		printAll(ProductList);
	}

	/**
	 * 글 목록 전체 출력
	 * @param list
	 */
	private static void printAll(List<Product> list) {
		// 글 목록이 존재하는지 확인
		if( list == null || list.isEmpty() ) {
			System.err.println("품목이 없습니다.");
			return;
		}
		// 글 목록 출력
		for (Product Product : list) {
			print(Product);
		}
	}

	/**
	 * 상품 출력
	 * @param Product
	 */
	private static void print(Product Product) {
		if( Product == null ) {
			System.err.println("상품을 조회 할 수 없습니다.");
			return;
		}
		
		int no = Product.getNo();
		String name = Product.getName();
		String exdate = Product.getExdate();
		Date createdAt = Product.getCreatedAt();
		Date updatedAt = Product.getUpdatedAt();
		// 날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String reg = sdf.format(createdAt);
		String upd = sdf.format(updatedAt);
		
		System.out.println("::::::::::::::::::::::::::::::::::::::::");
		System.out.println("★ 상품의번호 : " + no);
		System.out.println("★ 상품명 : " + name);
		System.out.println("★ 유통기한 : " + exdate);
		System.out.println("----------------------------------------");
		System.out.println("★ 등록날짜 : " + reg);
		System.out.println("★ 수정일 : " + upd);
		System.out.println("::::::::::::::::::::::::::::::::::::::::");
		System.out.println();
	}
	
	/**
	 * 상품 조회
	 */
	public static void select() {
		System.out.println(":::::::::: 상품 조회 :::::::::::");
		System.out.print("상품의번호 : ");
		int no = sc.nextInt();
		sc.nextLine();
		// 글번호(no)를 전달하여 상품 정보 데이터 요청
		Product Product = ProductService.select(no);
		// 상품 정보 출력
		print(Product);
	}

	/**
	 * 상품 등록
	 */
	public static void insert() {
		System.out.println(":::::::::: 상품 등록 ::::::::::");
		
		Product Product = input();
		// 상품 등록 요청
		int result = ProductService.insert(Product);
		if( result > 0 ) {
			System.out.println("★ 상품이 등록되었습니다.");
		} else {
			System.err.println("★ 상품 등록에 실패하였습니다.");
		}
	}
	
	/**
	 * 상품 정보 입력
	 * @param 
	 * @return
	 */
	private static Product input() {
		System.out.print("★ 상품명 : ");
		String name = sc.nextLine();
		System.out.print("★ 유통기한 : ");
		String exdate = sc.nextLine();
		Product Product = new Product(name, exdate);
		return Product;
	}
	
	/**
	 * 상품 수정
	 */
	public static void update() {
		System.out.println(":::::::::: 상품 수정 ::::::::::");
		
		System.out.print("상품 번호 : ");
		int no = sc.nextInt();
		sc.nextLine();

		Product Product = input();
		Product.setNo(no);
		
		// 상품 수정 요청
		int result = ProductService.update(Product);
		if( result > 0 ) {
			System.out.println("★ 상품이 수정되었습니다.");
		} else {
			System.err.println("★ 상품 수정에 실패하였습니다.");
		}
	}

	/**
	 * 상품 삭제
	 */
	public static void delete() {
		System.out.println(":::::::::: 상품 삭제 :::::::::::");
		
		System.out.print("상품 번호 : ");
		int no = sc.nextInt();
		sc.nextLine();
		
		// 상품 삭제 요청
		int result = ProductService.delete(no);
		if( result > 0 ) {
			System.out.println("★ 상품을 삭제하였습니다.");
		} else {
			System.err.println("★ 상품 삭제에 실패하였습니다.");
		}
	}
	
	public static void main(String[] args) throws Exception {
		int menuNo = 0;
		
		do {
			// 메뉴판 출력
			menu();
			// 메뉴 번호 입력
			menuNo = sc.nextInt();
			sc.nextLine();
			// 0 -> 프로그램 종료
			if( menuNo == 0 ) break;
			// 메뉴 선택
			switch (menuNo) {
				case 1:	list();			// 상품 목록
						break;
				case 2:	select();		// 상품 조회
						break;
				case 3:	insert();		// 상품 등록
						break;
				case 4:	update();		// 상품 수정
						break;
				case 5:	delete();		// 상품 삭제
						break;
			}
		} while (menuNo != 0);
		
		System.out.println("프로그램을 종료합니다...");
	}

}